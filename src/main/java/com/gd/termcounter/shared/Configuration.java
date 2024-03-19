package com.gd.termcounter.shared;

public class Configuration {
    public static final String COUNT_TERMS_URL = "http://127.0.0.1:8080/api/v1/terms/count";
    public static final String SAVE_JOB_URL = "http://127.0.0.1:8080/api/v1/jobs/save";

    private Configuration() {
        throw new IllegalStateException("Utility class");
    }
}
