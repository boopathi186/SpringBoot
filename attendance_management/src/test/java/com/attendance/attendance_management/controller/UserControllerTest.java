package com.attendance.attendance_management.controller;

import com.attendance.attendance_management.dto.LeaveDto;
import com.attendance.attendance_management.dto.UserDto;
import com.attendance.attendance_management.services.UserService;
import lombok.experimental.ExtensionMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;
    UserDto userDto;


    @BeforeEach
    void setUp() {
        userDto = new UserDto(1L,"john","teacher","cse","true");
    }

    @Test
    void getUser() {
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto);
        when(userService.getUser()).thenReturn(userDtoList);
        assertEquals(userDtoList,userController.getUser());

    }

    @Test
    void getUserById() {
        when(userService.getUserById(1L)).thenReturn(userDto);
        assertEquals(userDto,userController.getUserById("1"));
    }

    @Test
    void getUserByRoll() {
        List<UserDto>userDtoList=new ArrayList<>();
        userDtoList.add(userDto);
        when(userService.getUserByRoll("teacher")).thenReturn(userDtoList);
        assertEquals(userDtoList,userController.getUserByRoll("teacher"));
    }

    @Test
    void getUserByDepartment() {
        List<UserDto>userDtoList=new ArrayList<>();
        userDtoList.add(userDto);
        when(userService.getUserByDepartment("cse")).thenReturn(userDtoList);
        assertEquals(userDtoList,userController.getUserByDepartment("cse"));
    }

    @Test
    void getDelete() {
        when(userService.getDelete(1L)).thenReturn("Deleted");
        assertEquals("Deleted",userController.getDelete("1"));

    }

}