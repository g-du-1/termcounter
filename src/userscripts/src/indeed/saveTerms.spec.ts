/**
 * @vitest-environment jsdom
 */

import { describe, expect, it, vi } from "vitest";
import { saveTerms } from "./saveTerms.ts";

(global as any).GM_xmlhttpRequest = vi.fn();

describe("saveTerms", () => {
  it("sends save terms request with the correct data", async () => {
    saveTerms("a job description");

    expect((global as any).GM_xmlhttpRequest).toHaveBeenCalledWith({
      method: "POST",
      url: "http://localhost:8080/api/v1/terms/save",
      data: "a job description",
      headers: {
        "Content-Type": "application/json",
      },
      onload: expect.any(Function),
    });
  });
});
