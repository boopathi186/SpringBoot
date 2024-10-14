package com.attendance.attendance_management.services;

import com.attendance.attendance_management.repository.UserAuthRepository;
import com.attendance.attendance_management.table.UserAuth;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserAuthRepository userAuthRepository;
    private final JwtService jwtService;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final AuthenticationManager authenticationManager;

    public String registerUser(UserAuth user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userAuthRepository.save(user);
        return "Success";
    }


    public List<UserAuth> getRegisterUser() {

        return userAuthRepository.findAll();
    }

    public String verifyLogin(UserAuth userAuth) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuth.getUsername(), userAuth.getPassword()));
        if (authentication.isAuthenticated()) {
            if (userAuthRepository.findByUserName(userAuth.getUsername()).getIsActive())
                return jwtService.getToken(userAuth.getUsername());
            else {
                return "No active";
            }
        }
        return "No authenticated";
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
