package com.attendance.attendance_management.services;

import com.attendance.attendance_management.dto.AttendanceDto;
import com.attendance.attendance_management.mapper.AttendanceMapper;
import com.attendance.attendance_management.repository.AttendanceRepository;
import com.attendance.attendance_management.repository.UserRepository;
import com.attendance.attendance_management.table.AttendanceInfo;
import com.attendance.attendance_management.table.LeaveInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final LeaveService leaveService;
    private final UserRepository userRepository;

    public List<AttendanceDto> getAttendanceRecord() {
        this.attendanceMapper.setDto();
        return this.attendanceMapper.getDtoList();
    }


    public void postAttendanceRecord(final AttendanceDto attendanceDto) {
        AttendanceInfo attendanceInfo = this.attendanceMapper.setEntity(attendanceDto);
        this.attendanceRepository.save(attendanceInfo);

        if ((("absent").equalsIgnoreCase(attendanceDto.getStatus()) || ("leave").equalsIgnoreCase(attendanceDto.getStatus()))
                && "-".equals(attendanceDto.getRecordIn()) && "-".equals(attendanceDto.getRecordOut())) {
            LeaveInfo leaveInfo = new LeaveInfo();
            leaveInfo.setLeaveDate(attendanceDto.getDate());
            leaveInfo.setUser(this.userRepository.findById(attendanceDto.getUser().getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
            this.leaveService.addLeaveForm(leaveInfo);
        }
    }
}
