import { buildPageUrls } from "./buildPageUrls.ts";
import { getPageHtml } from "./getPageHtml.ts";
import { getJobHtmlData } from "./getJobHtmlData.ts";
import { getJobData } from "./getJobData.ts";
import { saveJob } from "./saveJob.ts";

export const main = async () => {
  const pageUrls = buildPageUrls(2);

  for (const element of pageUrls) {
    const pageHtml = await getPageHtml(element);
    const jobElemHtmlData = getJobHtmlData(pageHtml);

    for (const element of jobElemHtmlData) {
      const jobData = await getJobData(element.jobKey);
      saveJob(jobData);
    }
  }
};
