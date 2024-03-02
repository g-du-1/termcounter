package com.gd.termcounter.job;

public class JobUtils {
    public static Job createJob() {
        Job job = new Job();
        job.setKey("abdcef");
        job.setTitle("Software Engineer");
        job.setDescriptionTxt("Job Description");
        job.setEmployer("Company");
        job.setLocation("London");
        job.setUrl("https://example.com");
        job.setSalaryMin(80000.00);
        job.setSalaryMax(90000.00);

        return job;
    }
}
