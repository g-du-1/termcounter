import { JobData, JobResponse } from "./types.ts";

export const getJobData = async (jobKey: string): Promise<JobData> => {
  const resp = await fetch(
    `https://uk.indeed.com/viewjob?jk=${jobKey}&viewtype=embedded&xkcb=SoBi67M3E9Jz922yoh0PbzkdCdPP&continueUrl=%2Fjobs%3Fq%3Dsoftware%2Bengineer&xfps=0479a2ba-ec16-44dd-9c8f-ce5cc16ed6c6&spa=1&hidecmpheader=0`,
    {
      credentials: "include",
      headers: {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:123.0) Gecko/20100101 Firefox/123.0",
        Accept: "*/*",
        "Accept-Language": "en-GB,en;q=0.5",
        "Alt-Used": "uk.indeed.com",
        "Sec-Fetch-Dest": "empty",
        "Sec-Fetch-Mode": "cors",
        "Sec-Fetch-Site": "same-origin",
        Pragma: "no-cache",
        "Cache-Control": "no-cache",
      },
      referrer: "https://uk.indeed.com/jobs?q=software+engineer&vjk=ef1d2ffbee0d06d3",
      method: "GET",
      mode: "cors",
    },
  );

  const jobResponse: JobResponse = await resp.json();

  const jobData = jobResponse?.body?.hostQueryExecutionResult?.data?.jobData?.results?.[0]?.job;
  const salaryData = jobResponse?.body?.salaryInfoModel;

  return {
    key: jobData?.key,
    title: jobData?.title,
    employer: jobData?.sourceEmployerName,
    descriptionTxt: jobData?.description.text,
    url: jobData?.url,
    location: jobData?.location?.city,
    salaryMin: salaryData?.salaryMin,
    salaryMax: salaryData?.salaryMax,
  };
};
