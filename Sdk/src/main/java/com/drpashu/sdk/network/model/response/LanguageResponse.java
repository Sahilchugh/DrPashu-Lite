package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguageResponse {

    @SerializedName("language")
    @Expose
    private Integer language;

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }
}
