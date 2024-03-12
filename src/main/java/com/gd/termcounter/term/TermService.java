package com.gd.termcounter.term;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TermService {

    private final TermRepository termRepository;

    public TermService(TermRepository termRepository) {
        this.termRepository = termRepository;
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

    public List<Term> countTerms(String jobDescription) {
        Set<String> excludedWords = new HashSet<>() {{
            add("and");
            add("a");
            add("the");
        }};

        String[] words = jobDescription.split(" ");

        for (String word : words) {
            String lowerCaseWord = word.toLowerCase();

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

        return termRepository.findAll();
    }
}
