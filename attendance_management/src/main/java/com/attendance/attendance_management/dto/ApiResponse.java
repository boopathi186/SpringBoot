package com.attendance.attendance_management.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String status;
    private String message;
    private Object validationErrors;
    private long requestedTime;
    private String executionTime;
    private T response;

}
