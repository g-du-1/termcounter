import { wait } from "./wait";
import { describe, it, vi, expect } from 'vitest';

describe("wait", () => {
  vi.useFakeTimers();

  it("waits for the specified time", async () => {
    const ms = 1000;

    const start = Date.now();
    const waitPromise = wait(ms);
    vi.advanceTimersByTime(ms);

    await waitPromise;

    const end = Date.now();
    const elapsedTime = end - start;

    expect(elapsedTime).toBeGreaterThanOrEqual(ms);
  });

  it("resolves after waiting", async () => {
    const ms = 500;
    const start = Date.now();
    const waitPromise = wait(ms);

    vi.advanceTimersByTime(ms);

    await expect(waitPromise).resolves.toBeUndefined();

    const end = Date.now();
    const elapsedTime = end - start;

    expect(elapsedTime).toBeGreaterThanOrEqual(ms);
  });
});
