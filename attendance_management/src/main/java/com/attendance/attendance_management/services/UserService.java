package com.attendance.attendance_management.services;

import com.attendance.attendance_management.dto.LeaveDto;
import com.attendance.attendance_management.dto.UserDto;
import com.attendance.attendance_management.mapper.UserMapper;
import com.attendance.attendance_management.repository.UserRepository;
import com.attendance.attendance_management.table.LeaveInfo;
import com.attendance.attendance_management.table.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
//        UserInfo userInfo = userRepository.findById(id).stream().findFirst().orElse(null);
//        UserDto userDto = new UserDto();
//        return  userDto;
        Optional<UserInfo> userInfo= this.userRepository.findById(id);
        return userInfo.map(userMapper::setDto).orElse(null);
    }


    public List<UserDto> getUserByRoll(final String roll) {
//        final List<UserDto> userDtoList = getUser();
//        return userDtoList.stream()
//                .filter(user -> user.getRoll().equals(roll))
//                .collect(Collectors.toList());
        List<UserInfo> userInfos = userRepository.findByRoll(roll);
        List<UserDto> userDtoList = new ArrayList<>();
        for (UserInfo userInfo : userInfos) {
            userDtoList.add(userMapper.setDto(userInfo));
        }

        return userDtoList;
    }

    public List<UserDto> getUserByDepartment(final String department) {
        List<UserInfo> userInfos = userRepository.findByDepartment(department);
        List<UserDto> userDtoList = new ArrayList<>();
        for (UserInfo userInfo : userInfos) {
            userDtoList.add(userMapper.setDto(userInfo));
        }
        return userDtoList;
    }

    public void getDelete(final long id) {

        this.userRepository.deleteById(id);
    }
}
