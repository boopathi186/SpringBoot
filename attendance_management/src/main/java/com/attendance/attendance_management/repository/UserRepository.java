package com.attendance.attendance_management.repository;

import com.attendance.attendance_management.table.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {



    List<UserInfo> findByDepartment(String department);

    List<UserInfo> findByRoll(String roll);
}
