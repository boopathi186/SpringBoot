package com.attendance.attendance_management.controller;

import com.attendance.attendance_management.dto.UserDto;
import com.attendance.attendance_management.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {


    private final UserService userService;


    @GetMapping
    public List<UserDto> getUser() {
        return this.userService.getUser();
    }

    @GetMapping("/csrf")
    public CsrfToken getcsrf(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @GetMapping("/id/{id}")
    public UserDto getUserById(@PathVariable String id) {
        return this.userService.getUserById(Long.parseLong(id));
    }


    @GetMapping("/roll/{roll}")
    public List<UserDto> getUserByRoll(@PathVariable String roll) {
        return this.userService.getUserByRoll(roll);
    }

    @GetMapping("/department/{department}")
    public List<UserDto> getUserByDepartment(@PathVariable String department) {
        return this.userService.getUserByDepartment(department);
    }


    @DeleteMapping("/id/{id}")
    public String getDelete(@PathVariable String id) {
        return this.userService.getDelete(Long.parseLong(id));
    }
}
