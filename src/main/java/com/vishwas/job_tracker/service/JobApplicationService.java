package com.vishwas.job_tracker.service;

import com.vishwas.job_tracker.dto.JobApplicationDTO;
import com.vishwas.job_tracker.entity.JobApplication;
import com.vishwas.job_tracker.entity.JobStatus;
import com.vishwas.job_tracker.exception.ResourceNotFoundException;
import com.vishwas.job_tracker.repository.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository repository;

    @CacheEvict(value = "jobs" , allEntries = true)
    public JobApplication save(JobApplicationDTO job) {
        return repository.save(toEntity(job));
    }

    @Cacheable(value = "jobs")
    public List<JobApplicationDTO> getAll() {
        System.out.println("DB se aa raha hai — cache miss");
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(key = "#id" , value = "job")
    public JobApplicationDTO getById(Long id) {
        return toDTO(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found")));
    }

    public List<JobApplication> getByStatus(JobStatus status) {
        return repository.findByStatus(status);
    }

    @CacheEvict (key = "#id" , value = "job")
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private JobApplicationDTO toDTO(JobApplication job) {
        return JobApplicationDTO.builder()
                .id(job.getId())
                .companyName(job.getCompanyName())
                .role(job.getRole())
                .status(job.getStatus())
                .appliedDate(job.getAppliedDate())
                .notes(job.getNotes())
                .build();
    }

    private JobApplication toEntity(JobApplicationDTO dto) {
        return JobApplication.builder()
                .companyName(dto.getCompanyName())
                .role(dto.getRole())
                .status(dto.getStatus())
                .appliedDate(dto.getAppliedDate())
                .notes(dto.getNotes())
                .build();
    }

    public JobApplicationDTO update (Long id, JobApplicationDTO dto)
    {
        JobApplication existing = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Job not found "+id));

        existing.setCompanyName(dto.getCompanyName());
        existing.setRole(dto.getRole());
        existing.setStatus(dto.getStatus());
        existing.setNotes(dto.getNotes());
        existing.setAppliedDate(dto.getAppliedDate());

        return toDTO(repository.save(existing));
    }

    public Page<JobApplicationDTO> getPaginated(Pageable pageable) {

        return repository.findAll(pageable)
                .map(this::toDTO);
    }

}