package com.vishwas.job_tracker.dto;

import com.vishwas.job_tracker.entity.JobStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationDTO implements Serializable {
    private Long id;

    @NotBlank(message = "Company name required")
    private String companyName;

    @NotBlank(message = "Role required")
    private String role;

    private JobStatus status;
    private LocalDate appliedDate;
    private String notes;
}