package com.gd.termcounter.job;

public class JobUtils {
    public static Job createJob() {
        Job job = new Job();
        job.setTitle("Software Engineer");
        job.setDescriptionTxt("Job Description");

        return job;
    }
}
