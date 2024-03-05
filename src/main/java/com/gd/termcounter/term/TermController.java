package com.gd.termcounter.term;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/terms")
public class TermController {
    private final TermService termService;
    private final ObjectMapper objectMapper;

    public TermController(TermService termService, ObjectMapper objectMapper) {
        this.termService = termService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<Term> saveTerm(@RequestBody TermDTO termDTO) throws JsonProcessingException {
        String dtoString = objectMapper.writeValueAsString(termDTO);
        Term term = objectMapper.readValue(dtoString, Term.class);

        Term savedTerm = termService.saveTerm(term);

        return ResponseEntity.ok(savedTerm);
    }
}