package com.attendance.attendance_management.mapper;

import com.attendance.attendance_management.dto.AttendanceDto;
import com.attendance.attendance_management.repository.AttendanceRepository;
import com.attendance.attendance_management.repository.UserRepository;
import com.attendance.attendance_management.table.AttendanceInfo;
import com.attendance.attendance_management.table.UserInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class AttendanceMapper {
    private final AttendanceRepository attendanceRepo;
    private final UserRepository userRepository;
    private List<AttendanceDto> dtoList = new ArrayList<>();

    public void setDto() {
        dtoList.clear();
        for (AttendanceInfo att : attendanceRepo.findAll()) {
            if (att.getStatus() != null) {
                AttendanceDto attendanceDto = new AttendanceDto();
                attendanceDto.setAttendanceId(att.getAttendanceId());
                attendanceDto.setRecordIn(att.getRecordIn());
                attendanceDto.setRecordOut(att.getRecordOut());
                attendanceDto.setStatus(att.getStatus());
                attendanceDto.setDate(att.getDate());
                attendanceDto.setUser(att.getUser());
                dtoList.add(attendanceDto);
            }
        }
    }

    public AttendanceInfo setEntity(AttendanceDto attendanceDto) {
        AttendanceInfo attendanceDetails = new AttendanceInfo();
        attendanceDetails.setAttendanceId(attendanceDto.getAttendanceId());
        attendanceDetails.setDate(attendanceDto.getDate());
        attendanceDetails.setStatus(attendanceDto.getStatus());
        attendanceDetails.setRecordIn(attendanceDto.getRecordIn());
        attendanceDetails.setRecordOut(attendanceDto.getRecordOut());
        UserInfo userInfo = userRepository.findById(attendanceDto.getUser().getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        attendanceDetails.setUser(userInfo);
        attendanceRepo.save(attendanceDetails);
        return attendanceDetails;
    }

}

