package com.attendance.attendance_management.services;

import com.attendance.attendance_management.dto.LeaveDto;
import com.attendance.attendance_management.mapper.LeaveMapper;
import com.attendance.attendance_management.repository.LeaveRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtensionMethod(MockitoExtension.class)
class LeaveServiceTest {

    @Mock
    private LeaveRepository leaveRepository;

    @Mock
    private LeaveMapper leaveMapper;

    @InjectMocks
    private LeaveService leaveService;
    LeaveInfo leaveInfo;
    LeaveDto leaveDto;

    @BeforeEach
    void setUp() {

        UserInfo userInfo = new UserInfo(2L, "mano",
                "teacher", "cse", true);
        MockitoAnnotations.openMocks(this);
        leaveInfo = new LeaveInfo(1L, userInfo, "23-12-23");
        leaveDto = new LeaveDto(1L, userInfo, "23-12-23");
    }

    @Test
    void getLeaveData() {
        leaveService.getLeaveData();
        verify(leaveRepository).findAll();
    }

    @Test
    void getRecordById() {
        when(leaveRepository.findById(1L)).thenReturn(Optional.ofNullable(leaveInfo));
        when(leaveMapper.setDto(leaveInfo)).thenReturn(leaveDto);
        assertEquals(leaveDto, leaveService.getRecordById(1));
        verify(leaveRepository, times(1)).findById(1L);
    }

    @Test
    void getRecordByDate() {

        List<LeaveInfo> leaveInfoList = new ArrayList<>();
        leaveInfoList.add(leaveInfo);

        List<LeaveDto> leaveDtoList = new ArrayList<>();
        leaveDtoList.add(leaveDto);

        when(leaveRepository.findByLeaveDate("23-12-23")).thenReturn(leaveInfoList);
        when(leaveMapper.setDto(leaveInfo)).thenReturn(leaveDto);
        assertEquals(leaveDtoList, leaveService.getRecordByDate("23-12-23"));
        verify(leaveRepository, times(1)).findByLeaveDate("23-12-23");
    }

    @Test
    void addLeaveForm() {
        leaveService.addLeaveForm(leaveInfo);
        verify(leaveRepository).save(leaveInfo);
    }

    @Test
    void getDelete() {
        leaveService.getDelete(1L);
        verify(leaveRepository).deleteById(1L);
    }

}