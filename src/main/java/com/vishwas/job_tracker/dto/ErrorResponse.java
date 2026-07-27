package com.vishwas.job_tracker.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

// Error Response DTO
@Data
@Builder
public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
