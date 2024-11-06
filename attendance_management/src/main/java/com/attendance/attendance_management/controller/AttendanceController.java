package com.attendance.attendance_management.controller;

import com.attendance.attendance_management.dto.AttendanceDto;
import com.attendance.attendance_management.dto.LeaveDto;
import com.attendance.attendance_management.services.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/attendance")
@RequiredArgsConstructor
class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping
    public List<AttendanceDto> getAttendanceRecord() {
        return this.attendanceService.getAttendanceRecord();
    }

    @GetMapping("/planned-leave")
    public List<AttendanceDto> getPlannedLeave() {
        return attendanceService.getPlannedLeave();
    }

    @GetMapping("/sick")
    public List<AttendanceDto> getSickLeave() {
        return attendanceService.getSickLeave();
    }


    @GetMapping("/absent")
    public List<AttendanceDto> getAbsentRecords() {
        return attendanceService.getRecordsByAbsent();
    }

    @GetMapping("/present")
    public List<AttendanceDto> getPresentRecords() {
        return attendanceService.getRecordsByPresent();
    }


    @PostMapping("/addattendance")
    public String addAttendanceRecord(@RequestBody AttendanceDto attendanceDto) {
        this.attendanceService.postAttendanceRecord(attendanceDto);
        return "Attendance record added successfully";
    }


}

