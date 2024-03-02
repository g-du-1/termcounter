package com.gd.termcounter.job;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.gd.termcounter.job.JobAssertions.assertJobEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JobRepositoryTest {

    @Autowired
    private JobRepository jobRepository;

    @Test
    public void testSaveJob() {
        Job job = new Job();
        job.setTitle("Software Engineer");

        Job savedJob = jobRepository.save(job);

        assertJobEquals(job, savedJob);
    }
}