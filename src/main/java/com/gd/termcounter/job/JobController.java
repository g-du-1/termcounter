package com.gd.termcounter.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {
    private final JobService jobService;
    private final ObjectMapper objectMapper;

    public JobController(JobService jobService, ObjectMapper objectMapper) {
        this.jobService = jobService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<Job> saveJob(@RequestBody JobDTO jobDTO) throws JsonProcessingException {
        String dtoString = objectMapper.writeValueAsString(jobDTO);
        Job job = objectMapper.readValue(dtoString, Job.class);

        Job savedJob = jobService.saveJob(job);

        return ResponseEntity.ok(savedJob);
    }
}