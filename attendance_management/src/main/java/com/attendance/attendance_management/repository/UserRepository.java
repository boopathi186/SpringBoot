package com.attendance.attendance_management.repository;

import com.attendance.attendance_management.table.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {


    List<UserInfo> findByDepartment(String department);

    List<UserInfo> findByRoll(String roll);

    @Modifying
    @Query("UPDATE UserInfo u SET u.isActive = false WHERE u.userId = :userId")
    void softDelete(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE UserInfo u SET u.isMarked = false WHERE u.userId = :userId")
    void markAttendance(@Param("userId") Long userId);


    List<UserInfo> findByIsMarked(boolean b);
}
