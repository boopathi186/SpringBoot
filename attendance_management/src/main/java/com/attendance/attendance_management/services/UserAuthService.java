package com.attendance.attendance_management.services;

import com.attendance.attendance_management.dto.ApiResponse;
import com.attendance.attendance_management.exceptionhandler.customexceptions.InvalidException;
import com.attendance.attendance_management.exceptionhandler.customexceptions.UserNotFoundException;
import com.attendance.attendance_management.repository.UserAuthRepository;
import com.attendance.attendance_management.table.UserAuth;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserAuthRepository userAuthRepository;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;

    public String registerUser(UserAuth user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userAuthRepository.save(user);
        return "Success";
    }

    public ResponseEntity<ApiResponse<List<UserAuth>>> getRegisterUser() {

        List<UserAuth> userAuth = userAuthRepository.findAll();
        ApiResponse<List<UserAuth>> response =
                new ApiResponse<>(HttpStatus.OK.value(), "success", "User found", null, System.currentTimeMillis(), "0 ms", userAuth);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    public ResponseEntity<ApiResponse<UserAuth>> getRegisterById(long id) {
        UserAuth userAuth = userAuthRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        ApiResponse<UserAuth> response =
                new ApiResponse<>(HttpStatus.OK.value(), "success", "User found", null, System.currentTimeMillis(), "0 ms", userAuth);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
//    public String verifyLogin(UserAuth userAuth) {
//        boolean isActive = userAuthRepository.findByUserName(userAuth.getUsername()).getIsActive();
//        if (isActive) {
//            Authentication authentication =
//                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuth.getUsername(), userAuth.getPassword()));
//            if (authentication.isAuthenticated()) {
//                return jwtService.getToken(userAuth.getUsername());
//            }
//        }
//        return
//    }


    public String verifyLogin(UserAuth userAuth) {

        UserAuth user = userAuthRepository.findByUserName(userAuth.getUsername());
        try {
            if (user == null) {
                throw new UserNotFoundException("User not found with username: " + userAuth.getUsername());
            }
            if (!user.getIsActive()) {
                throw new InvalidException("User account is inactive.");
            }

            if (!encoder.matches(userAuth.getPassword(), user.getPassword())) {
                throw new InputMismatchException("Invalid username or password.");
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userAuth.getUsername(), userAuth.getPassword())
            );
            if (authentication.isAuthenticated()) {
                return jwtService.getToken(userAuth.getUsername());
            }
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (InvalidException e) {
            throw new InvalidException(e.getMessage());
        } catch (InputMismatchException e) {
            throw new InputMismatchException(e.getMessage());
        }
        throw new InvalidException("internal error");
    }

    @Transactional
    public String getDelete(final long id) {
        Optional<UserAuth> userInfoOpt = userAuthRepository.findById(id);
        if (userInfoOpt.isPresent()) {
            UserAuth userAuth = userInfoOpt.get();
            if (!userAuth.getIsActive()) {
                return "No match found";
            } else {
                userAuthRepository.softDelete(id);
                return "Deleted";
            }
        } else {
            return "No match found";
        }
    }
}
