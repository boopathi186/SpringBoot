package com.attendance.attendance_management.services;

import com.attendance.attendance_management.dto.UserDto;
import com.attendance.attendance_management.mapper.UserMapper;
import com.attendance.attendance_management.repository.UserRepository;
import com.attendance.attendance_management.table.UserInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;


    @Test
    void TestGetUser() {
        userService.getUserByRoll("Teacher");
        verify(userRepository).findByRoll("Teacher");
    }

    @Test
    void TestGetUserById() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(20L);
        userInfo.setName("Michael Johnson");

        UserDto userDto = new UserDto();
        userDto.setUserId(20L);
        userDto.setName("Michael Johnson");
        when(userRepository.findById(20L)).thenReturn(Optional.of(userInfo));
        when(userMapper.setDto(userInfo)).thenReturn(userDto);
        UserDto result = userService.getUserById(20L);
        System.out.println(result.getName());
        assertEquals("Michael Johnson", result.getName());
        assertEquals(20L, result.getUserId());
        verify(userRepository, times(1)).findById(20L);
        verify(userMapper, times(1)).setDto(userInfo);
    }

//    @Test
//    void TestIfNotGetUserById()
//    {
//        when(userRepository.findById(3L)).thenReturn(Optional.empty());
//        UserDto result= userService.getUserById(3L);
//        assertNull(result);
//        verify(userRepository, times(1)).findById(3L);
//    }
}