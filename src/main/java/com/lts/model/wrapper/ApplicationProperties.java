package com.lts.model.wrapper;

import com.lts.model.entities.Language;

import java.util.List;

public class ApplicationProperties {

    private List<Language> languages;

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

}
