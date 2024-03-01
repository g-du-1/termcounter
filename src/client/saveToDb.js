import fetch from "node-fetch";
import {savedJobs} from "./saved/savedJobs.js";

(async () => {
  const saveJob = async (jobData) => {
    try {
      const response = await fetch('http://127.0.0.1:8080/api/v1/jobs/save', {
        method: 'POST', headers: {
          'Content-Type': 'application/json'
        }, body: JSON.stringify(jobData)
      });

      const data = await response.json();

      console.log('Job saved successfully:', data);
    } catch (error) {
      console.error('There was a problem saving the job:', error);
    }
  };

  for (let i = 0; i < savedJobs.length; i++) {
    await saveJob(savedJobs[i]);
  }
})();