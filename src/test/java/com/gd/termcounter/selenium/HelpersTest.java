package com.gd.termcounter.selenium;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelpersTest {
    @Test
    void testGeneratesListOfUrls() {
        List<String> pageUrls = Helpers.getPageUrls(1, 5);

        List<String> expected = new ArrayList<>();
        expected.add("https://uk.indeed.com/jobs?q=software+engineer");
        expected.add("https://uk.indeed.com/jobs?q=software+engineer&start=10");
        expected.add("https://uk.indeed.com/jobs?q=software+engineer&start=20");
        expected.add("https://uk.indeed.com/jobs?q=software+engineer&start=30");
        expected.add("https://uk.indeed.com/jobs?q=software+engineer&start=40");

        assertEquals(pageUrls, expected);
    }

    @Test
    void testHandlesSettingStartPages() {
        List<String> pageUrls = Helpers.getPageUrls(3, 5);

        List<String> expected = new ArrayList<>();
        expected.add("https://uk.indeed.com/jobs?q=software+engineer&start=20");
        expected.add("https://uk.indeed.com/jobs?q=software+engineer&start=30");
        expected.add("https://uk.indeed.com/jobs?q=software+engineer&start=40");
        expected.add("https://uk.indeed.com/jobs?q=software+engineer&start=50");
        expected.add("https://uk.indeed.com/jobs?q=software+engineer&start=60");

        assertEquals(pageUrls, expected);
    }
}
