package com.gd.termcounter.job;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class JobServiceTest {

    @Test
    public void saveNewJob() {
        JobRepository jobRepository = mock(JobRepository.class);
        JobService jobService = new JobService(jobRepository);

        Job job = JobUtils.createJob();

        when(jobRepository.existsByKey(job.getKey())).thenReturn(false);
        when(jobRepository.save(job)).thenReturn(job);

        Job savedJob = jobService.saveJob(job);

        assertEquals(savedJob, job);
        verify(jobRepository).existsByKey(job.getKey());
        verify(jobRepository).save(job);
    }

    @Test
    public void updateExistingJob() {
        JobRepository jobRepository = mock(JobRepository.class);
        JobService jobService = new JobService(jobRepository);

        Job existingJob = JobUtils.createJob();
        Job updatedJob = JobUtils.createJob();

        when(jobRepository.existsByKey(existingJob.getKey())).thenReturn(true);
        when(jobRepository.findByKey(existingJob.getKey())).thenReturn(existingJob);
        when(jobRepository.save(existingJob)).thenReturn(updatedJob);

        Job savedJob = jobService.saveJob(updatedJob);

        assertEquals(savedJob, updatedJob);
        verify(jobRepository).existsByKey(existingJob.getKey());
        verify(jobRepository).findByKey(existingJob.getKey());
        verify(jobRepository).save(existingJob);
    }
}
