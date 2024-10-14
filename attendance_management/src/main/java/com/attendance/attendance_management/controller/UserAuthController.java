package com.attendance.attendance_management.controller;

import com.attendance.attendance_management.services.UserAuthService;
import com.attendance.attendance_management.table.UserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserAuthController {
    private final UserAuthService authService;

    @GetMapping("/register")
    public List<UserAuth> getRegisterUser() {
        return authService.getRegisterUser();
    }

    @PostMapping("/addregister")
    public String registerUser(@RequestBody UserAuth user) {
        return authService.registerUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserAuth userAuth) {
        return authService.verifyLogin(userAuth);
    }


    @DeleteMapping("/register/id/{id}")
    public String getDelete(@PathVariable String id) {
        return authService.getDelete(Long.parseLong(id));

    }
}
