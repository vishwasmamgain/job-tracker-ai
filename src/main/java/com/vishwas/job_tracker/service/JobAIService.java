package com.vishwas.job_tracker.service;

import com.vishwas.job_tracker.entity.JobApplication;
import com.vishwas.job_tracker.repository.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JobAIService {

    private final ChatClient chatClient;
    private final JobApplicationRepository repository;

    public String smartSearch (String userQuery){
        List<JobApplication> jobs = repository.findAll();

        String jobsContext = jobs.stream().map(job -> String.format(
                "ID: %d, Company: %s, Role: %s, Status: %s, Notes: %s",
                job.getId(),
                job.getCompanyName(),
                job.getRole(),
                job.getStatus(),
                job.getNotes()))
                .collect(Collectors.joining("\n"));

        String prompt = String.format("""
                You are a job search assistant.
                User has these job applications:
                %s
                
                User query: %s
                
                Return only relevant job IDs and why they match.
                """, jobsContext, userQuery);

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();

    }

}
