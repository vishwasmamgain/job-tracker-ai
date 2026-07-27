package com.vishwas.job_tracker.controller;

import com.vishwas.job_tracker.dto.JobApplicationDTO;
import com.vishwas.job_tracker.entity.JobApplication;
import com.vishwas.job_tracker.entity.JobStatus;
import com.vishwas.job_tracker.service.JobAIService;
import com.vishwas.job_tracker.service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService service;
    private final JobAIService aiService;

    @PostMapping
    public ResponseEntity<JobApplication> create(@RequestBody @Valid JobApplicationDTO job) {
        return ResponseEntity.ok(service.save(job));
    }

    @GetMapping
    public ResponseEntity<List<JobApplicationDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<JobApplication>> getByStatus(@RequestParam JobStatus status) {
        return ResponseEntity.ok(service.getByStatus(status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplicationDTO> updateJob (@PathVariable Long id,
                                                        @RequestBody @Valid JobApplicationDTO dto) {
        return ResponseEntity.ok( service.update(id, dto));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<JobApplicationDTO>> getPaginated (
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            @RequestParam (defaultValue = "appliedDate") String sortBy){

        Pageable pageable = PageRequest.of(page,size,Sort.by(sortBy).descending());

        return ResponseEntity.ok(service.getPaginated(pageable));
    }

    @GetMapping("/ai/search")
    public ResponseEntity<String> aiSearch (@RequestParam String query){
        return ResponseEntity.ok(aiService.smartSearch(query));

    }



}
