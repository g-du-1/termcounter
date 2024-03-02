package com.gd.termcounter.job;

import jakarta.persistence.*;

@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String descriptionTxt;

    public String getDescriptionTxt() {
        return descriptionTxt;
    }

    public void setDescriptionTxt(String descriptionTxt) {
        this.descriptionTxt = descriptionTxt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}