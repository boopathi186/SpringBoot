package com.attendance.attendance_management.mapper;

import com.attendance.attendance_management.dto.UserDto;
import com.attendance.attendance_management.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class UserMapper {
    private final UserRepository userRepository;
    private List<UserDto> userDto = new ArrayList<>();

}
