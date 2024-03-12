package com.gd.termcounter.term;

import org.springframework.stereotype.Service;

import java.util.List;

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
        String[] words = jobDescription.split(" ");

        for (String word : words) {
            Term term = new Term();
            term.setName(word);

            Term existingTerm = termRepository.findByName(word);

            if (existingTerm != null) {
                term.setCount(existingTerm.getCount() + 1);
            }

            saveTerm(term);
        }

        return termRepository.findAll();
    }
}
