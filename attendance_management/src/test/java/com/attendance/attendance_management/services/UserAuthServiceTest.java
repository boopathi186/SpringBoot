package com.attendance.attendance_management.services;

import com.attendance.attendance_management.repository.UserAuthRepository;
import com.attendance.attendance_management.table.UserAuth;
import lombok.experimental.ExtensionMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtensionMethod(MockitoExtension.class)
class UserAuthServiceTest {

    @Mock
    private UserAuthRepository userAuthRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserAuthService userAuthService;
    private UserAuth userAuth;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userAuth = new UserAuth(2L, "mano",
                "m@123", true);
    }


    @Test
    void TestGetRegisterUser() {
        userAuthService.getRegisterUser();
        verify(userAuthRepository).findAll();
    }

    @Test
    void TestGetRegisterById() {
        long id = 1L;
        when(userAuthRepository.findById(id)).thenReturn(of(userAuth));
        String user = Objects.requireNonNull(userAuthService.getRegisterById(id).getBody()).getResponse().getUsername();
        assertEquals("mano", user);
        assertEquals("m@123", Objects.requireNonNull(userAuthService.getRegisterById(id).getBody()).getResponse().getPassword());
    }

    @Test
    void TestVerifyLogin() {
        when(userAuthRepository.findByUserName(userAuth.getUsername()))
                .thenReturn(new UserAuth(2L, "mano", "$2a$12$5.bQ.EnTuA..QWU0UgPGAeBhRQCtu4/5bEFwHrM0cs4JFpzcEirzq", true));

        when(encoder.matches("m@123", "$2a$12$5.bQ.EnTuA..QWU0UgPGAeBhRQCtu4/5bEFwHrM0cs4JFpzcEirzq")).thenReturn(true);
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtService.getToken("mano")).thenReturn("$2a$12$5.bQ.EnTuA..QWU0UgPGAeBhRQCtu4/5bEFwHrM0cs4JFpzcEirzq");
        String token = userAuthService.verifyLogin(userAuth);
        assertEquals("$2a$12$5.bQ.EnTuA..QWU0UgPGAeBhRQCtu4/5bEFwHrM0cs4JFpzcEirzq", token);
        verify(userAuthRepository, times(1)).findByUserName(userAuth.getUsername());
    }

//    @Test
//    void TestUserNotFound() {
//        when(userAuthRepository.findByUserName(userAuth.getUsername())).thenReturn(null);
//        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
//            userAuthService.verifyLogin(userAuth);
//        });
//        assertEquals("User not found", exception.getMessage());
//        verify(userAuthRepository, times(1)).findByUserName(userAuth.getUsername());
//    }
//
//    @Test
//    void TestUserIsNotActive()
//    {
//        when(userAuthRepository.findByUserName(userAuth.getUsername()))
//                .thenReturn(new UserAuth(20L, "manoj", "m@123", false));
//        InvalidException exception = assertThrows(InvalidException.class, () -> {
//            userAuthService.verifyLogin(userAuth);
//        });
//        assertEquals("User account is inactive.", exception.getMessage());
//        verify(userAuthRepository, times(1)).findByUserName(userAuth.getUsername());
//    }
//
//    @Test
//    void TestAuthenticationFailed()
//    {
//        when(userAuthRepository.findByUserName(userAuth.getUsername()))
//                .thenReturn(new UserAuth(2L, "mano", "$2a$12$5.bQ.EnTuA..QWU0UgPGAeBhRQCtu4/5bEFwHrM0cs4JFpzcEirzq", true));
//
//
//        when(encoder.matches("m@123", "$2a$12$5.bQ.EnTuA..QWU0UgPGAeBhRQCtu4/5bEFwHrM0cs4JFpzcEirzq")).thenReturn(true);
//        Authentication authentication = mock(Authentication.class);
//        when(authentication.isAuthenticated()).thenReturn(false);
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
//        InvalidException exception = assertThrows(InvalidException.class, () -> {
//           userAuthService.verifyLogin(userAuth);
//        });
//        assertEquals("internal error", exception.getMessage());
//        verify(userAuthRepository, times(1)).findByUserName(userAuth.getUsername());
//    }

    @Test
    void TestDeleteById() {
        when(userAuthRepository.findById(2L)).thenReturn(Optional.of(userAuth));
        String result = userAuthService.getDelete(userAuth.getUserId());
        assertEquals("Deleted", result);
        verify(userAuthRepository, times(1)).findById(2L);
    }

    @Test
    void TestRegisterUser() {

        when(userAuthRepository.save(userAuth)).thenReturn(userAuth);
        String result = userAuthService.registerUser(userAuth);
        assertEquals("Success", result);
        verify(userAuthRepository, times(1)).save(userAuth);
    }

}