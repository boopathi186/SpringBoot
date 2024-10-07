package com.attendance.attendance_management.repository;

import com.attendance.attendance_management.table.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth,Long> {

    UserAuth findByUserName(String userNAme);
}
