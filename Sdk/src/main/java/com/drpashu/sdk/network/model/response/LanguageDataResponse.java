package com.drpashu.sdk.network.model.response;

public class LanguageDataResponse {

    private String language, languageIndex;
    private boolean languageCheck;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageIndex() {
        return languageIndex;
    }

    public void setLanguageIndex(String languageIndex) {
        this.languageIndex = languageIndex;
    }

    public boolean isLanguageCheck() {
        return languageCheck;
    }

    public void setLanguageCheck(boolean languageCheck) {
        this.languageCheck = languageCheck;
    }
}
