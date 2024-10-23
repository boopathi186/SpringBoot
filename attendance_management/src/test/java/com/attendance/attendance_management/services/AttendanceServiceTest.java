package com.attendance.attendance_management.services;

import com.attendance.attendance_management.dto.AttendanceDto;
import com.attendance.attendance_management.dto.LeaveDto;
import com.attendance.attendance_management.mapper.AttendanceMapper;
import com.attendance.attendance_management.repository.AttendanceRepository;
import com.attendance.attendance_management.table.AttendanceInfo;
import com.attendance.attendance_management.table.LeaveInfo;
import com.attendance.attendance_management.table.UserInfo;
import lombok.experimental.ExtensionMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtensionMethod(MockitoExtension.class)
class AttendanceServiceTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private AttendanceMapper attendanceMapper;

    @InjectMocks
    AttendanceService attendanceService;

    AttendanceInfo attendanceInfo;
    AttendanceDto attendanceDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        UserInfo userInfo = new UserInfo(2L, "mano",
                "teacher", "cse", true);
        attendanceDto = new AttendanceDto(1L, "23-12-23", "9:00 Am", userInfo, "5:00 Pm", "present");
        attendanceInfo = new AttendanceInfo(1L, userInfo, "23-12-23", "9:00 Am", "5:00 Pm", "present");
    }

    @Test
    void getAttendanceRecord() {
        List<AttendanceInfo> attendanceInfoList = new ArrayList<>();
        attendanceInfoList.add(attendanceInfo);

        List<AttendanceDto> attendanceDtoList = new ArrayList<>();
        attendanceDtoList.add(attendanceDto);

        when(attendanceRepository.findAll()).thenReturn(attendanceInfoList);
        when(attendanceMapper.getDtoList()).thenReturn(attendanceDtoList);
        assertEquals(attendanceDtoList, attendanceService.getAttendanceRecord());
        verify(attendanceRepository, times(1)).findAll();
        verify(attendanceMapper, times(1)).getDtoList();
    }


    @Test
    void postAttendanceRecord() {

        when(attendanceMapper.setEntity(attendanceDto)).thenReturn(attendanceInfo);
        attendanceService.postAttendanceRecord(attendanceDto);
        verify(attendanceRepository, times(1)).save(attendanceInfo);
    }

}