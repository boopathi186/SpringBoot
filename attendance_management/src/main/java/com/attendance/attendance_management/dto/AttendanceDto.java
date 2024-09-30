package com.attendance.attendance_management.dto;

import com.attendance.attendance_management.table.UserInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceDto {
    private Long attendanceId;
    private String date;
    private String RecordIn;
    private UserInfo user;
    private String RecordOut;
    private String Status;

}

