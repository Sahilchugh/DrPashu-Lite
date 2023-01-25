package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        @SerializedName("vet_status")
        @Expose
        private String vetStatus;
        @SerializedName("user_bio")
        @Expose
        private Integer userBio;

        public String getVetStatus() {
            return vetStatus;
        }

        public void setVetStatus(String vetStatus) {
            this.vetStatus = vetStatus;
        }

        public Integer getUserBio() {
            return userBio;
        }

        public void setUserBio(Integer userBio) {
            this.userBio = userBio;
        }
    }
}