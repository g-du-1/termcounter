/**
 * @vitest-environment jsdom
 */

import { describe, expect, it } from "vitest";
import { getJobHtmlData } from "./getJobHtmlData.ts";

describe("getJobHtmlData", () => {
  it("builds an array of job data objects from html", () => {
    const result = getJobHtmlData(
      "<!DOCTYPE html><html><head></head><body><a href='https://uk.indeed.com/url-one' data-jk='key1'>Job1</a><a href='https://uk.indeed.com/url-two' data-jk='key2'>Job2</a></body></html>",
    );

    const expected = [
      { jobKey: "key1", internalUrl: "https://uk.indeed.com/url-one" },
      { jobKey: "key2", internalUrl: "https://uk.indeed.com/url-two" },
    ];

    expect(result).toStrictEqual(expected);
  });
});
