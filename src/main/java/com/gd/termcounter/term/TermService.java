package com.gd.termcounter.term;

import com.gd.termcounter.job.Job;
import com.gd.termcounter.job.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TermService {

    private final TermRepository termRepository;
    private final JobRepository jobRepository;

    public TermService(TermRepository termRepository, JobRepository jobRepository) {
        this.termRepository = termRepository;
        this.jobRepository = jobRepository;
    }

    public Term saveTerm(Term term) {
        String termName = term.getName();

        if (termRepository.existsByName(termName)) {
            Term existingTerm = termRepository.findByName(termName);
            existingTerm.setCount(term.getCount());
            return termRepository.save(existingTerm);
        } else {
            return termRepository.save(term);
        }
    }

    public List<Term> countTerms(String jobDescription, String jobKey) {
        Job existingJob = jobRepository.findByKey(jobKey);

        if (existingJob == null) {
            Set<String> excludedWords = Exclusions.excludedWords;

            String[] words = jobDescription.split("\\s+");

            for (String word : words) {
                String lowerCaseWord = word.replaceAll("[():,.]", "").toLowerCase();

                if (!excludedWords.contains(lowerCaseWord)) {
                    Term term = new Term();
                    term.setName(lowerCaseWord);

                    Term existingTerm = termRepository.findByName(lowerCaseWord);

                    if (existingTerm != null) {
                        term.setCount(existingTerm.getCount() + 1);
                    }

                    saveTerm(term);
                }
            }
        }

        return termRepository.findAll();
    }
}
