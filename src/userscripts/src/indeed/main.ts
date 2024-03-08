import { buildPageUrls } from "./buildPageUrls.ts";
import { getPageHtml } from "./getPageHtml.ts";
import { getJobHtmlData } from "./getJobHtmlData.ts";
import { getJobData } from "./getJobData.ts";
import { saveJob } from "./saveJob.ts";

export const main = async () => {
  const pageUrls = buildPageUrls(2);

  for (let i = 0; i < pageUrls.length; i++) {
    const pageHtml = await getPageHtml(pageUrls[i]);
    const jobElemHtmlData = getJobHtmlData(pageHtml);

    for (let j = 0; j < jobElemHtmlData.length; j++) {
      const jobData = await getJobData(jobElemHtmlData[j].jobKey);
      saveJob(jobData);
      // saveTerms(jobData.descriptionTxt);
    }
  }
};
