package com.gd.termcounter.job;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Transactional
    public Job saveJob(Job job) {
        String jobKey = job.getKey();

        if (jobRepository.existsByKey(jobKey)) {
            Job existingJob = jobRepository.findByKey(jobKey);
            BeanUtils.copyProperties(job, existingJob);
            return jobRepository.save(existingJob);
        } else {
            return jobRepository.save(job);
        }
    }
}
