/**
 * @vitest-environment jsdom
 */

import { describe, expect, it, vi } from "vitest";

import { getPageHtml } from "./getPageHtml.ts";

describe("getPageHtml", () => {
  it("gets a pages html", async () => {
    const mockHtml = "<!DOCTYPE html><html><head></head><body><h1>Hello, World!</h1></body></html>";

    global.fetch = vi.fn().mockResolvedValue({
      text: vi.fn().mockResolvedValue(mockHtml),
    });

    const result = await getPageHtml("https://example.com");

    expect(result).toBe(mockHtml);
  });
});
