export const saveTerms = (jobDesc: string) => {
  // @ts-ignore
  GM_xmlhttpRequest({
    method: "POST",
    url: "http://localhost:8080/api/v1/terms/save",
    data: jobDesc,
    headers: {
      "Content-Type": "application/json",
    },
    onload: (_: Response) => {},
  });
};
