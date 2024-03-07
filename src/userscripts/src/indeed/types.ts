export type JobData = {
  key: string;
  title: string;
  employer: string;
  descriptionTxt: string;
  url: string;
  location: string;
  salaryMin: number;
  salaryMax: number;
};

export type JobDataResult = {
  job: {
    key: string;
    title: string;
    sourceEmployerName: string;
    description: {
      text: string;
    };
    url: string;
    location: {
      city: string;
    };
  };
};

export type JobResponse = {
  body: {
    hostQueryExecutionResult: {
      data: {
        jobData: {
          results: JobDataResult[];
        };
      };
    };
    salaryInfoModel: {
      salaryMin: number;
      salaryMax: number;
    };
  };
};
