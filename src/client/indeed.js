(async () => {
  // Utilities

  const delay = ms => {
    console.log(`%c Waiting ${ms} ms...`, 'color: darkgrey; font-weight: bold;');

    return new Promise(res => setTimeout(res, ms));
  };

  const getHtmlDocumentFromUrl = async url => {
    console.log(`%c Fetching HTML Text: ${url}`, 'color: darkblue; font-weight: bold;');

    const resp = await fetch(url, {
      "credentials": "include", "headers": {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:123.0) Gecko/20100101 Firefox/123.0",
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8",
        "Accept-Language": "en-GB,en;q=0.5",
        "Alt-Used": "uk.indeed.com",
        "Upgrade-Insecure-Requests": "1",
        "Sec-Fetch-Dest": "document",
        "Sec-Fetch-Mode": "navigate",
        "Sec-Fetch-Site": "none",
        "Sec-Fetch-User": "?1",
        "Pragma": "no-cache",
        "Cache-Control": "no-cache"
      }, "method": "GET", "mode": "cors"
    });

    const text = await resp?.text();
    const parser = new DOMParser();

    return parser?.parseFromString(text, 'text/html');
  };

  const fetchJobData = async (jobKey) => {
    const resp = await fetch(`https://uk.indeed.com/viewjob?jk=${jobKey}&viewtype=embedded&xkcb=SoBi67M3E9Jz922yoh0PbzkdCdPP&continueUrl=%2Fjobs%3Fq%3Dsoftware%2Bengineer&xfps=0479a2ba-ec16-44dd-9c8f-ce5cc16ed6c6&spa=1&hidecmpheader=0`, {
      "credentials": "include",
      "headers": {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:123.0) Gecko/20100101 Firefox/123.0",
        "Accept": "*/*",
        "Accept-Language": "en-GB,en;q=0.5",
        "Alt-Used": "uk.indeed.com",
        "Sec-Fetch-Dest": "empty",
        "Sec-Fetch-Mode": "cors",
        "Sec-Fetch-Site": "same-origin",
        "Pragma": "no-cache",
        "Cache-Control": "no-cache"
      },
      "referrer": "https://uk.indeed.com/jobs?q=software+engineer&vjk=ef1d2ffbee0d06d3",
      "method": "GET",
      "mode": "cors"
    });

    return await resp.json();
  };

  const getJobData = async (jobKey) => {
    const jobResponse = await fetchJobData(jobKey);

    const jobData = jobResponse?.body?.hostQueryExecutionResult?.data?.jobData?.results?.[0]?.job;
    const salaryData = jobResponse?.body?.salaryInfoModel;

    return {
      key: jobData?.key,
      title: jobData?.title,
      employer: jobData?.sourceEmployerName,
      descriptionTxt: jobData?.description.text,
      published: jobData?.datePublished,
      url: jobData?.url,
      location: jobData?.location?.city,
      salaryMin: salaryData?.salaryMin,
      salaryMax: salaryData?.salaryMax
    };
  };

  const localStorageKey = 'gdSaved';

  const saveToLocalStorage = (jobData) => {
    const savedArray = JSON.parse(localStorage.getItem(localStorageKey));

    savedArray.push(jobData);

    localStorage.setItem(localStorageKey, JSON.stringify(savedArray));
  };

  const main = async () => {
    localStorage.setItem(localStorageKey, '[]');

    const PAGES_TO_VISIT = 2;

    for (let i = 0; i < PAGES_TO_VISIT; i++) {
      const url = `https://uk.indeed.com/jobs?q=Software+Engineer&start=${i === 0 ? "0" : (i * 10).toString()}`;

      const htmlDocument = await getHtmlDocumentFromUrl(url);

      const jobElems = htmlDocument?.querySelectorAll('[data-jk]');

      for (let i = 0; i < jobElems?.length; i++) {
        const currJobElem = jobElems?.[i];

        const jobKey = currJobElem?.getAttribute("data-jk");
        const jobData = await getJobData(jobKey);
        jobData.indeedUrl = currJobElem?.getAttribute("href");

        console.log(`${i + 1}`, jobData);
        saveToLocalStorage(jobData);

        await delay(Math.random() * 9000 + 1000);
      }

      await delay(Math.random() * 30000 + 1000);
    }
  };

  await main();

  console.log(`%c DONE!`, 'background: darkgreen; color: white; font-weight: bold;');
})();