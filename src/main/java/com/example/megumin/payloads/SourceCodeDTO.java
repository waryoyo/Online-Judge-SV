package com.example.megumin.payloads;

import com.example.megumin.models.ProgrammingLanguage;

public class SourceCodeDTO {
    private String content;
    private ProgrammingLanguage language;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ProgrammingLanguage getLanguage() {
        return language;
    }

    public void setLanguage(ProgrammingLanguage language) {
        this.language = language;
    }
}