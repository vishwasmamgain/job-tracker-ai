package com.vishwas.job_tracker.repository;

import com.vishwas.job_tracker.entity.JobApplication;
import com.vishwas.job_tracker.entity.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByStatus(JobStatus status);
}
