package com.gd.termcounter.term;

import org.springframework.stereotype.Service;

@Service
public class TermService {

    private final TermRepository termRepository;

    public TermService(TermRepository termRepository) {
        this.termRepository = termRepository;
    }

    public Term saveTerm(Term term) {
        return termRepository.save(term);
    }
}
