package com.gd.termcounter.job;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/save")
    public ResponseEntity<Job> saveJob(@RequestBody JobDTO jobDTO) {
        Job job = JobMapper.INSTANCE.jobDtoToJob(jobDTO);

        Job savedJob = jobService.saveJob(job);

        return ResponseEntity.ok(savedJob);
    }
}