/**
 * @vitest-environment jsdom
 */

import { describe, expect, it, vi } from "vitest";
import { JobData } from "./types.ts";
import { saveJob } from "./saveJob.ts";

(global as any).GM_xmlhttpRequest = vi.fn();

describe("saveJob", () => {
  it("sends save job request with the correct data", async () => {
    const jobData: JobData = {
      key: "key1",
      title: "title",
      employer: "employer",
      descriptionTxt: "description text",
      url: "https://example.com",
      location: "London",
      salaryMin: 50000,
      salaryMax: 100000,
    };

    saveJob(jobData);

    expect((global as any).GM_xmlhttpRequest).toHaveBeenCalledWith({
      method: "POST",
      url: "http://localhost:8080/api/v1/jobs/save",
      data: JSON.stringify(jobData),
      headers: {
        "Content-Type": "application/json",
      },
      onload: expect.any(Function),
    });
  });
});
