package com.attendance.attendance_management.table;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Entity
@Getter
@Setter
@Table(name = "user_info")
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "name")
    private String name;
    @Column(name = "roll")
    private String roll;
    @Column(name = "department")
    private String department;
    @Column(name = "is_active")
    private Boolean isActive = true;
    @Column(name = "is_marked")
    private Boolean isMarked = true;
}
