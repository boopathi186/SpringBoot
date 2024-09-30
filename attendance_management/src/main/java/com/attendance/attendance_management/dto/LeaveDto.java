package com.attendance.attendance_management.dto;

import com.attendance.attendance_management.table.UserInfo;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LeaveDto {
    private Long leaveId;
    private UserInfo user;
    private String leaveDate;

}
