package com.attendance.attendance_management.mapper;

import com.attendance.attendance_management.dto.LeaveDto;
import com.attendance.attendance_management.table.LeaveInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeaveMapper {

    public LeaveDto setDto(LeaveInfo leaveInfo) {
        LeaveDto leaveDto = new LeaveDto();
        leaveDto.setLeaveId(leaveInfo.getLeaveId());
        leaveDto.setLeaveDate(leaveInfo.getLeaveDate());
        leaveDto.setUser(leaveInfo.getUser());
        return leaveDto;
    }
}
