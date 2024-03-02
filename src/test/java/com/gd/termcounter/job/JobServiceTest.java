package com.gd.termcounter.job;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JobServiceTest {
    private JobRepository jobRepository;

    @BeforeEach
    public void setUp() {
        jobRepository = mock(JobRepository.class);
    }

    @Test
    public void saveJob() {
        JobService jobService = new JobService(jobRepository);

        Job job = new Job();
        job.setTitle("Software Engineer");

        when(jobRepository.save(job)).thenReturn(job);

        Job savedJob = jobService.saveJob(job);

        assertEquals("Software Engineer", savedJob.getTitle());
    }
}