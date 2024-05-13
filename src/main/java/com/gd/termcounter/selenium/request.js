const fetchJobData = async (jobKey) => {
  const resp = await fetch(
    `https://uk.indeed.com/viewjob?jk=${jobKey}&viewtype=embedded&xkcb=SoBi67M3E9Jz922yoh0PbzkdCdPP&continueUrl=%2Fjobs%3Fq%3Dsoftware%2Bengineer&xfps=0479a2ba-ec16-44dd-9c8f-ce5cc16ed6c6&spa=1&hidecmpheader=0`,
    {
      headers: {
        "sec-ch-ua":
          '"Chromium";v="124", "Google Chrome";v="124", "Not-A.Brand";v="99"',
        "sec-ch-ua-mobile": "?0",
        "sec-ch-ua-platform": '"Windows"',
      },
      referrer:
        "https://uk.indeed.com/jobs?q=software+engineer&vjk=38ed5a9865793038",
      referrerPolicy: "origin-when-cross-origin",
      body: null,
      method: "GET",
      mode: "cors",
      credentials: "omit",
    }
  );

  return await resp.json();
};

const getJobData = async (jobKey) => {
  const jobResponse = await fetchJobData(jobKey);

  const jobData =
    jobResponse?.body?.hostQueryExecutionResult?.data?.jobData?.results?.[0]
      ?.job;
  const salaryData = jobResponse?.body?.salaryInfoModel;

  return {
    key: jobData?.key,
    title: jobData?.title,
    employer: jobData?.sourceEmployerName,
    descriptionTxt: jobData?.description.text,
    // published: jobData?.datePublished,
    url: jobData?.url,
    location: jobData?.location?.city,
    salaryMin: salaryData?.salaryMin,
    salaryMax: salaryData?.salaryMax,
  };
};

// noinspection JSAnnotator
return await getJobData("{{jobKey}}");
