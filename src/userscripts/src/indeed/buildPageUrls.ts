export const buildPageUrls = (nOfPages: number) => {
  const result = ["https://uk.indeed.com/jobs?q=software+engineer"];

  for (let i = 1; i < nOfPages; i++) {
    result.push(`https://uk.indeed.com/jobs?q=software+engineer&start=${i * 10}`);
  }

  return result;
};
