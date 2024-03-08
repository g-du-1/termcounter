import { JobData } from "./types.ts";

export const saveJob = (jobData: JobData) => {
  // @ts-ignore
  GM_xmlhttpRequest({
    method: "POST",
    url: "http://localhost:8080/api/v1/jobs/save",
    data: JSON.stringify(jobData),
    headers: {
      "Content-Type": "application/json",
    },
    onload: (_: Response) => {},
  });
};
