package com.attendance.attendance_management.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long userId;
    private String name;
    private String roll;
    private String department;
}
