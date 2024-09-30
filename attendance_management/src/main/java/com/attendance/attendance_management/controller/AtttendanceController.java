package com.attendance.attendance_management.controller;

import com.attendance.attendance_management.dto.AttendanceDto;
import com.attendance.attendance_management.services.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping
    public List<AttendanceDto> getAttendanceRecord() {
        return attendanceService.getAttendanceRecord();
    }

    @PostMapping("/addattendance")
    public String addAttendanceRecord(@RequestBody AttendanceDto attendanceDto) {
        attendanceService.postAttendanceRecord(attendanceDto);
        return "Attendance record added successfully";
    }

}
