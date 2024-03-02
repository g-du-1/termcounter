package com.gd.termcounter.job;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JobAssertions {
    public static void assertJobEquals(Job expected, Job actual) {
        assertEquals(expected.getKey(), actual.getKey());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getDescriptionTxt(), actual.getDescriptionTxt());
        assertEquals(expected.getEmployer(), actual.getEmployer());
        assertEquals(expected.getLocation(), actual.getLocation());
        assertEquals(expected.getUrl(), actual.getUrl());
        assertEquals(expected.getSalaryMin(), actual.getSalaryMin());
        assertEquals(expected.getSalaryMax(), actual.getSalaryMax());
    }
}
