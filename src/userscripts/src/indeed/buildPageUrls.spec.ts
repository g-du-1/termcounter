import { describe, expect, it } from "vitest";
import { buildPageUrls } from "./buildPageUrls.ts";

describe("buildPageUrls", () => {
  it("builds an array of urls", () => {
    const result = buildPageUrls(5);

    const expected = [
      "https://uk.indeed.com/jobs?q=software+engineer",
      "https://uk.indeed.com/jobs?q=software+engineer&start=10",
      "https://uk.indeed.com/jobs?q=software+engineer&start=20",
      "https://uk.indeed.com/jobs?q=software+engineer&start=30",
      "https://uk.indeed.com/jobs?q=software+engineer&start=40",
    ];

    expect(result).toStrictEqual(expected);
  });
});
