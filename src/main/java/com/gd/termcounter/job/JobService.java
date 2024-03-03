package com.gd.termcounter.job;

import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job saveJob(Job job) {
        if (!jobRepository.existsByKey(job.getKey())) {
            return jobRepository.save(job);
        }

        return job;
    }
}
