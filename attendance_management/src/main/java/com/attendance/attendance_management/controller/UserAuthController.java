package com.attendance.attendance_management.controller;

import com.attendance.attendance_management.services.UserAuthService;
import com.attendance.attendance_management.table.UserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserAuthController {
    private final UserAuthService authService;

    @GetMapping("/register")
    public List<UserAuth> getRegisterUser()
    {
          return  authService.getRegisterUser();
    }
    @PostMapping("/addregister")
    public String  registerUser(@RequestBody UserAuth user)
    {
        return authService.registerUser(user);
    }
}
