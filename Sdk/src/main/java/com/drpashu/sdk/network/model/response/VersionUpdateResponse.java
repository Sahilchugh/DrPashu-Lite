package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionUpdateResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("version_code")
    @Expose
    private String versionCode;
    @SerializedName("release_note")
    @Expose
    private String releaseNote;
    @SerializedName("mandatory")
    @Expose
    private String mandatory;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("version_number")
    @Expose
    private String version_number;

    public Boolean getStatus() {
        return status;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public String getReleaseNote() {
        return releaseNote;
    }

    public String getMandatory() {
        return mandatory;
    }

    public String getVersion_number() {
        return version_number;
    }

    public String getActive() {
        return active;
    }
}
