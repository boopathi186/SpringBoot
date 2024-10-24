package com.attendance.attendance_management.services;

import com.attendance.attendance_management.dto.UserDto;
import com.attendance.attendance_management.mapper.UserMapper;
import com.attendance.attendance_management.repository.UserAuthRepository;
import com.attendance.attendance_management.repository.UserRepository;
import com.attendance.attendance_management.table.UserInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserInfo userInfo;
    private final UserAuthRepository userAuthRepository;

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
        Optional<UserInfo> userInfo = this.userRepository.findById(id);
        return userInfo.map(this.userMapper::setDto).orElse(null);
    }


    public List<UserDto> getUserByRoll(final String roll) {
//        final List<UserDto> userDtoList = getUser();
//        return userDtoList.stream()
//                .filter(user -> user.getRoll().equals(roll))
//                .collect(Collectors.toList());
        List<UserInfo> userInfos = this.userRepository.findByRoll(roll);
        List<UserDto> userDtoList = new ArrayList<>();
        for (UserInfo userInfo : userInfos) {
            userDtoList.add(this.userMapper.setDto(userInfo));
        }

        return userDtoList;
    }

    public List<UserDto> getUserByDepartment(final String department) {
        List<UserInfo> userInfos = this.userRepository.findByDepartment(department);
        List<UserDto> userDtoList = new ArrayList<>();
        for (UserInfo userInfo : userInfos) {
            userDtoList.add(this.userMapper.setDto(userInfo));
        }
        return userDtoList;
    }

    @Transactional
    public String getDelete(final long id) {
        Optional<UserInfo> userInfoOpt = this.userRepository.findById(id);
        if (userInfoOpt.isPresent()) {
            UserInfo userInfo = userInfoOpt.get();
            if (!userInfo.getIsActive()) {
                return "No match found";
            } else {
                this.userRepository.softDelete(id);
                return "Deleted";
            }
        } else {
            return "No match found";
        }
    }
}

