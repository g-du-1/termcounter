export async function getPageHtml(url: string) {
  const resp = await fetch(url, {
    credentials: "include",
    headers: {
      "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:123.0) Gecko/20100101 Firefox/123.0",
      Accept: "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8",
      "Accept-Language": "en-GB,en;q=0.5",
      "Alt-Used": "uk.indeed.com",
      "Upgrade-Insecure-Requests": "1",
      "Sec-Fetch-Dest": "document",
      "Sec-Fetch-Mode": "navigate",
      "Sec-Fetch-Site": "none",
      "Sec-Fetch-User": "?1",
      Pragma: "no-cache",
      "Cache-Control": "no-cache",
    },
    method: "GET",
    mode: "cors",
  });

  return await resp?.text();
}
