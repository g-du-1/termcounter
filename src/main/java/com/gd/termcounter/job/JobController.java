package com.gd.termcounter.job;

import org.springframework.http.HttpStatus;
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

    private Job mapJob(JobDTO jobDTO) {
        Job job = new Job();

        job.setTitle(jobDTO.getTitle());
        job.setEmployer(jobDTO.getEmployer());
        job.setKey(jobDTO.getKey());
        job.setLocation(jobDTO.getLocation());
        job.setSalaryMin(Integer.parseInt(jobDTO.getSalaryMin()));
        job.setSalaryMax(Integer.parseInt(jobDTO.getSalaryMax()));
        job.setUrl(jobDTO.getUrl());
        job.setDescriptionTxt(jobDTO.getDescriptionTxt());

        return job;
    }

    @PostMapping("/save")
    public ResponseEntity<Job> saveJob(@RequestBody JobDTO jobDTO) {
        Job mappedJob = mapJob(jobDTO);

        Job savedJob = jobService.saveJob(mappedJob);

        return new ResponseEntity<>(savedJob, HttpStatus.CREATED);
    }
}
