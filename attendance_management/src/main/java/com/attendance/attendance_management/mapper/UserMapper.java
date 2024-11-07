package com.attendance.attendance_management.mapper;
import com.attendance.attendance_management.dto.UserDto;
import com.attendance.attendance_management.repository.UserRepository;
import com.attendance.attendance_management.table.UserInfo;
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

    public UserDto setDto(final UserInfo userInfo) {

        if (userInfo == null) {
            throw new NullPointerException("userInfo is null");
        } else {

            UserDto userDto1 = new UserDto();
            userDto1.setUserId(userInfo.getUserId());
            userDto1.setRoll(userInfo.getRoll());
            userDto1.setName(userInfo.getName());
            userDto1.setDepartment(userInfo.getDepartment());
            userDto1.setIsActive(userInfo.getIsActive());
            userDto1.setIsMarked(userInfo.getIsMarked());
            return userDto1;

        }

    }
}
