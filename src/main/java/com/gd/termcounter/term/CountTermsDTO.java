package com.gd.termcounter.term;

public class CountTermsDTO {
    private String description;
    private String jobKey;

    public CountTermsDTO(String description, String jobKey) {
        this.description = description;
        this.jobKey = jobKey;
    }

    public CountTermsDTO() {
    }

    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
