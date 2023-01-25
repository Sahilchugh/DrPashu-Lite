package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateVaccinationResponse {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("lot_name")
    @Expose
    private String lotName;
    @SerializedName("vaccination")
    @Expose
    private String vaccination;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    public String getVaccination() {
        return vaccination;
    }

    public void setVaccination(String vaccination) {
        this.vaccination = vaccination;
    }
}
