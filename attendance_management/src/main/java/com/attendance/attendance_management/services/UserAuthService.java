package com.attendance.attendance_management.services;

import com.attendance.attendance_management.repository.UserAuthRepository;
import com.attendance.attendance_management.table.UserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserAuthRepository userAuthRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final JwtService service;
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

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userAuth.getUsername(), userAuth.getPassword()));
        if (authentication.isAuthenticated()) {
            return service.getToken(userAuth.getUsername());
        }
        return "failed";
    }
}
