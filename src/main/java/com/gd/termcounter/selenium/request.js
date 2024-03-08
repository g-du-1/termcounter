const fetchJobData = async (jobKey) => {
  const resp = await fetch(
    `https://uk.indeed.com/viewjob?jk=${jobKey}&viewtype=embedded&xkcb=SoBi67M3E9Jz922yoh0PbzkdCdPP&continueUrl=%2Fjobs%3Fq%3Dsoftware%2Bengineer&xfps=0479a2ba-ec16-44dd-9c8f-ce5cc16ed6c6&spa=1&hidecmpheader=0`,
    {
      credentials: "include",
      headers: {
        accept: "*/*",
        "accept-language": "en-US,en;q=0.9",
        "sec-ch-ua":
          '"Chromium";v="122", "Not(A:Brand";v="24", "Google Chrome";v="122"',
        "sec-ch-ua-mobile": "?0",
        "sec-ch-ua-platform": '"Windows"',
        "sec-fetch-dest": "empty",
        "sec-fetch-mode": "cors",
        "sec-fetch-site": "same-origin",
        cookie:
          'CTK=1hofcq9dmkjhe80m; INDEED_CSRF_TOKEN=H722MFbMbibbMCWsQ8Stwos6IQyT0T6A; _cfuvid=iNGcuJ4PVoFCjd7d6tGGXM2OKMOzbYkr37S_mzY_Sv0-1709913745090-0.0.1.1-604800000; _gcl_au=1.1.318077506.1709913746; LC="co=GB"; CSRF=zmfD4vHMUGDfzDz11nc3yc9E7vifDV1e; OptanonAlertBoxClosed=2024-03-08T16:02:49.379Z; _ga=GA1.2.1528167877.1709913769; _gid=GA1.2.725659993.1709913769; __cf_bm=yJeSBC1U.liz17RDDINDSC8v42ZI62SDyBAwMZnLRU0-1709932404-1.0.1.1-k6FpKQk2Lz2mo08RM6G6fg822xqdNLa6Gav0XlQ7Zn3vR2UcOJPtXIz5OxtxTfhqZF5yU_1kc54cYjutsdyI1g; LV="LA=1709932415:LV=1709913744:CV=1709932415:TS=1709913744"; SHARED_INDEED_CSRF_TOKEN=H722MFbMbibbMCWsQ8Stwos6IQyT0T6A; indeed_rcc="LV:CTK:RQ"; hpnode=1; SURF=eAw9bIewkjclEqiZihSn8II4CRXgTzdh; RQ="q=software+engineer&l=&ts=1709932623767&pts=1709913754791"; OptanonConsent=isGpcEnabled=0&datestamp=Fri+Mar+08+2024+21%3A17%3A04+GMT%2B0000+(Greenwich+Mean+Time)&version=202210.1.0&isIABGlobal=false&hosts=&consentId=e3fd63f4-0a8f-4892-8df8-21aacc58df16&interactionCount=2&landingPath=NotLandingPage&groups=C0001%3A1%2CC0002%3A1%2CC0003%3A1%2CC0004%3A1%2CC0007%3A1&AwaitingReconsent=false&geolocation=%3B; JSESSIONID=DA65D3A259E2136C220495000DE80D2F; _gat=1; RSJC=eab057c1a33aaf6e',
        Referer: "https://uk.indeed.com/?from=gnav-jobsearch--indeedmobile",
        "Referrer-Policy": "strict-origin-when-cross-origin",
      },
      referrer:
        "https://uk.indeed.com/jobs?q=software+engineer&vjk=ef1d2ffbee0d06d3",
      method: "GET",
      mode: "cors",
    },
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
