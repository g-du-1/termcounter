/**
 * @vitest-environment jsdom
 */

import { describe, expect, it } from "vitest";
import { getJobKeys } from "./getJobKeys.ts";

describe("buildJobKeys", () => {
  it("builds an array of job keys", () => {
    const result = getJobKeys(
      "<!DOCTYPE html><html><head></head><body><div data-jk='key1'>Job1</div><div data-jk='key2'>Job2</div></body></html>",
    );

    const expected = ["key1", "key2"];

    expect(result).toStrictEqual(expected);
  });
});
