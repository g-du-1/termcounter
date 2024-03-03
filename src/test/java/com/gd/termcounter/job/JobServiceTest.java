package com.gd.termcounter.job;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.gd.termcounter.job.JobAssertions.assertJobEquals;
import static org.mockito.Mockito.*;

public class JobServiceTest {
    private JobRepository jobRepository;

    @BeforeEach
    public void setUp() {
        jobRepository = mock(JobRepository.class);
    }

    @Test
    public void saveJob() {
        JobService jobService = new JobService(jobRepository);

        Job job = JobUtils.createJob();

        when(jobRepository.save(job)).thenReturn(job);

        Job savedJob = jobService.saveJob(job);

        assertJobEquals(job, savedJob);
    }

    @Test
    public void duplicates_NotSaved() {
        JobService jobService = new JobService(jobRepository);

        Job existingJob = JobUtils.createJob();
        when(jobRepository.existsByKey(existingJob.getKey())).thenReturn(true);
        when(jobRepository.save(existingJob)).thenReturn(existingJob);

        Job savedJob = jobService.saveJob(existingJob);

        verify(jobRepository, never()).save(existingJob);
        assertJobEquals(existingJob, savedJob);
    }
}
