package com.attendance.attendance_management.services;

import com.attendance.attendance_management.dto.LeaveDto;
import com.attendance.attendance_management.mapper.LeaveMapper;
import com.attendance.attendance_management.repository.LeaveRepository;
import com.attendance.attendance_management.repository.UserRepository;
import com.attendance.attendance_management.table.LeaveInfo;
import com.attendance.attendance_management.table.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final LeaveMapper leaveMapper;
    private final UserRepository userRepository;

    public List<LeaveDto> getLeaveData() {
        final List<LeaveDto> leaveDtoList = new ArrayList<>();
        final List<LeaveInfo> leaveInfoList = this.leaveRepository.findAll();
        leaveInfoList.forEach(leaveInfo -> {
            final LeaveDto leaveDto = this.leaveMapper.setDto(leaveInfo);
            leaveDtoList.add(leaveDto);
        });
        return leaveDtoList;
    }

    //    public LeaveDto getRecordById(final int id) {
//        final List<LeaveDto> leaveDtoList = getLeaveData();
//       return leaveDtoList.stream()
//                .filter(user -> user.getLeaveId()==(id)).findFirst().orElse(null);
//
//    }
    public LeaveDto getRecordById(final int id) {
        Optional<LeaveInfo> leaveInfoOptional = leaveRepository.findById((long) id);
        if (!leaveInfoOptional.isPresent()) {
            return null;
        }
        return leaveMapper.setDto(leaveInfoOptional.get());
    }


    //    public List<LeaveDto> getRecordByDate(final String date) {
//        final List<LeaveDto> leaveDtoList = getLeaveData();
//        return leaveDtoList.stream()
//                .filter(user -> user.getLeaveDate().equals(date)).toList();
//    }
    public List<LeaveDto> getRecordByDate(final String date) {

        List<LeaveInfo> leaveInfoList = leaveRepository.findByLeaveDate(date);
        List<LeaveDto> leaveDtoList = new ArrayList<>();
        for (LeaveInfo leaveInfo : leaveInfoList) {
            leaveDtoList.add(leaveMapper.setDto(leaveInfo));
        }

        return leaveDtoList;
    }

    public void addLeaveForm(final LeaveInfo leaveInfo) {
        this.leaveRepository.save(leaveInfo);
    }

    public void getDelete(final Long id) {
        this.leaveRepository.deleteById(id);
    }


    public void addAbsentForm(final LeaveInfo leaveInfo) {
        this.leaveRepository.save(leaveInfo);
    }
}
