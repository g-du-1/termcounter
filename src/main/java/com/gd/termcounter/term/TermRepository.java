package com.gd.termcounter.term;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Long> {
    boolean existsByName(String termName);

    Term findByName(String termName);
}