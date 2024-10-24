package com.attendance.attendance_management.controller;

import com.attendance.attendance_management.dto.LeaveDto;
import com.attendance.attendance_management.services.LeaveService;
import com.attendance.attendance_management.table.LeaveInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leaverecord")
public class LeaveController {

    private final LeaveService leaveService;

    @GetMapping
    public List<LeaveDto> getLeaveData() {
        return this.leaveService.getLeaveData();
    }

    @GetMapping("/id/{id}")
    public LeaveDto getRecordById(@PathVariable String id) {
        return this.leaveService.getRecordById(Integer.parseInt(id));
    }

    @GetMapping("/{date}")
    public List<LeaveDto> getRecordByDate(@PathVariable String date) {
        return this.leaveService.getRecordByDate(date);
    }

    public void addLeaveForm(LeaveInfo data) {
        this.leaveService.addLeaveForm(data);
    }

    @DeleteMapping("/id/{id}")
    public String getDelete(@PathVariable Long id) {
        this.leaveService.getDelete(id);
        return "Deleted";
    }
}
