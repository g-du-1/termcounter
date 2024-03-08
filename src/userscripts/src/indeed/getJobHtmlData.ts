import { JobHtmlElemData } from "./types.ts";

export const getJobHtmlData = (page: string) => {
  const result: JobHtmlElemData[] = [];

  const parser = new DOMParser();
  const dom = parser?.parseFromString(page, "text/html");

  const jobElems = dom?.querySelectorAll("[data-jk]");

  for (let i = 0; i < jobElems?.length; i++) {
    const currJobElem = jobElems?.[i];

    const jobKey = currJobElem?.getAttribute("data-jk");
    const internalUrl = currJobElem?.getAttribute("href");

    result.push({ jobKey, internalUrl });
  }

  return result;
};
