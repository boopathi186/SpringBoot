package com.attendance.attendance_management.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String name;
    private String roll;
    private String department;
    private  String isActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(userId, userDto.userId) &&
                Objects.equals(name, userDto.name) &&
                Objects.equals(roll, userDto.roll) &&
                Objects.equals(department, userDto.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, roll, department);
    }


}
