/**
 * @vitest-environment jsdom
 */

import { describe, expect, it, vi } from "vitest";
import { jobResponse } from "./fixtures/jobResponse.ts";
import { getJobData } from "./getJobData.ts";

describe("getJobData", () => {
  it("gets and formats job data", async () => {
    global.fetch = vi.fn().mockResolvedValue({
      json: vi.fn().mockResolvedValue(jobResponse),
    });

    const result = await getJobData("key1");

    const expected = {
      key: "key1",
      title: "title",
      employer: "employer",
      descriptionTxt: "description text",
      url: "https://example.com",
      location: "London",
      salaryMin: 50000,
      salaryMax: 100000,
    };

    expect(result).toStrictEqual(expected);
  });
});
