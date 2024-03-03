package com.gd.termcounter.job;

import jakarta.transaction.Transactional;
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
            existingJob.setTitle(job.getTitle());
            existingJob.setDescriptionTxt(job.getDescriptionTxt());
            // TODO Rest of the fields

            return jobRepository.save(existingJob);
        } else {
            return jobRepository.save(job);
        }
    }
}
