package com.gd.termcounter.term;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/terms")
public class TermController {
    private final TermService termService;

    public TermController(TermService termService) {
        this.termService = termService;
    }

    @PostMapping("/save")
    public ResponseEntity<Term> saveTerm(@Valid @RequestBody TermDTO termDTO) {
        Term term = TermMapper.INSTANCE.termDtoToTerm(termDTO);

        Term savedTerm = termService.saveTerm(term);

        return ResponseEntity.ok(savedTerm);
    }

    @PostMapping("/count")
    public ResponseEntity<List<Term>> countTerms(@RequestBody CountTermsDTO countTermsDTO) {
        String jobDescription = countTermsDTO.getDescription();
        String jobKey = countTermsDTO.getJobKey();

        List<Term> savedTerms = termService.countTerms(jobDescription, jobKey);

        return ResponseEntity.ok(savedTerms);
    }
}