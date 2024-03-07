export const getJobKeys = (page: string) => {
  const result: string[] = [];

  const parser = new DOMParser();
  const dom = parser?.parseFromString(page, "text/html");

  const jobElems = dom?.querySelectorAll("[data-jk]");

  for (let i = 0; i < jobElems?.length; i++) {
    const currJobElem = jobElems?.[i];

    const jobKey = currJobElem?.getAttribute("data-jk");
    jobKey && result.push(jobKey);
  }

  return result;
};
