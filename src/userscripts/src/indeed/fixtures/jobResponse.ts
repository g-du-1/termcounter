import { JobResponse } from "../types.ts";

export const jobResponse: JobResponse = {
  body: {
    hostQueryExecutionResult: {
      data: {
        jobData: {
          results: [
            {
              job: {
                key: "key1",
                title: "title",
                sourceEmployerName: "employer",
                description: {
                  text: "description text",
                },
                url: "https://example.com",
                location: {
                  city: "London",
                },
              },
            },
          ],
        },
      },
    },
    salaryInfoModel: {
      salaryMin: 50000,
      salaryMax: 100000,
    },
  },
};
