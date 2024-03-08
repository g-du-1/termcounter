package com.gd.termcounter.term;

import jakarta.validation.constraints.NotBlank;

public class TermDTO {
    @NotBlank(message = "Field 'name' cannot be blank")
    private String name;
    private int count = 1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
