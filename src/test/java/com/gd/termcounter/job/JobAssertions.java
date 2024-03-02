package com.gd.termcounter.job;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JobAssertions {
    public static void assertJobEquals(Job expected, Job actual) {
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getDescriptionTxt(), actual.getDescriptionTxt());
    }
}
