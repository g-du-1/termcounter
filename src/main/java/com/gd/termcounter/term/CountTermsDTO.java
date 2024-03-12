package com.gd.termcounter.term;

public class CountTermsDTO {
    private String description;

    public CountTermsDTO(String description) {
        this.description = description;
    }

    public CountTermsDTO() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
