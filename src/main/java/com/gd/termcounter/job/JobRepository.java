package com.gd.termcounter.job;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
    boolean existsByKey(String key);

    Job findByKey(String key);
}