package com.attendance.attendance_management.repository;

import com.attendance.attendance_management.table.AttendanceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceInfo, Long> {

}
