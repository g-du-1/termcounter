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
        job.setUrl(jobDTO.getUrl());
        job.setDescriptionTxt(jobDTO.getDescriptionTxt());

        if (jobDTO.getSalaryMin() != null) {
            job.setSalaryMin(parseDoubleOrNull(jobDTO.getSalaryMin()));
        }

        if (jobDTO.getSalaryMax() != null) {
            job.setSalaryMax(parseDoubleOrNull(jobDTO.getSalaryMax()));
        }

        return job;
    }

    private Double parseDoubleOrNull(String value) {
        if (value != null && !value.isEmpty()) {
            return Double.parseDouble(value);
        } else {
            return null;
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Job> saveJob(@RequestBody JobDTO jobDTO) {
        Job mappedJob = mapJob(jobDTO);

        Job savedJob = jobService.saveJob(mappedJob);

        return new ResponseEntity<>(savedJob, HttpStatus.CREATED);
    }
}
