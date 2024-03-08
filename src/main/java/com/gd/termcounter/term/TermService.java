package com.gd.termcounter.term;

import org.springframework.stereotype.Service;

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
}
