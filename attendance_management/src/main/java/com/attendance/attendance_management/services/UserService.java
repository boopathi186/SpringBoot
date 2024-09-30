package com.attendance.attendance_management.services;

import com.attendance.attendance_management.dto.UserDto;
import com.attendance.attendance_management.mapper.UserMapper;
import com.attendance.attendance_management.repository.UserRepository;
import com.attendance.attendance_management.table.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDto> getUser() {
        final List<UserDto> userDtoList = new ArrayList<>();
        final List<UserInfo> userDetails = this.userRepository.findAll();

        userDetails.forEach(user -> {
            final UserDto userDto = new UserDto();
            userDto.setDepartment(user.getDepartment());
            userDto.setRoll(user.getRoll());
            userDto.setName(user.getName());
            userDto.setUserId(user.getUserId());
            userDtoList.add(userDto);
        });
        return userDtoList;
    }


    public UserDto getUserById(final long id) {
        final List<UserDto> userDtoList = getUser();
        return userDtoList.stream()
                .filter(user -> user.getUserId().equals(id))
                .findFirst()
                .orElse(null);
    }


    public List<UserDto> getUserByRoll(final String roll) {
        final List<UserDto> userDtoList = getUser();
        return userDtoList.stream()
                .filter(user -> user.getRoll().equals(roll))
                .collect(Collectors.toList());
    }

    public List<UserDto> getUserByDepartment(final String department) {
        final List<UserDto> userDtoList = getUser();
        return userDtoList.stream()
                .filter(user -> user.getDepartment().equals(department))
                .collect(Collectors.toList());
    }

    public void getDelete(final long id) {

        this.userRepository.deleteById(id);
    }
}
