package com.attendance.attendance_management.controller;

import com.attendance.attendance_management.dto.UserDto;
import com.attendance.attendance_management.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<UserDto> getUser() {
        return this.userService.getUser();
    }

    @RequestMapping("/id/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        if (id == null || id.trim().isEmpty() || id.equalsIgnoreCase("null")) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            long userId = Long.parseLong(id);
            UserDto user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @GetMapping("/roll/{roll}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<UserDto> getUserByRoll(@PathVariable String roll) {
        return this.userService.getUserByRoll(roll);
    }

    @GetMapping("/department/{department}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<UserDto> getUserByDepartment(@PathVariable String department) {
        return this.userService.getUserByDepartment(department);
    }

    @PostMapping("/adduser")
    @PreAuthorize("hasRole('ADMIN')")
    public String addUser(@RequestBody UserDto userDto)
    {
        this.userService.addUser(userDto);
        return "User Added Successfully";
    }


    @GetMapping("/unmarked")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<UserDto> getUnMarkedAttendance(UserDto userDto) {
        return this.userService.getUnMarkedAttendance(userDto);
    }

    @DeleteMapping("/id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String getDelete(@PathVariable String id) {
        return this.userService.getDelete(Long.parseLong(id));
    }

}
