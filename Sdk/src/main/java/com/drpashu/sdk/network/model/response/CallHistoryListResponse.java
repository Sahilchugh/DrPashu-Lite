package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CallHistoryListResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

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

    public static class Data {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("call_status_res")
        @Expose
        private String callStatusRes;
        @SerializedName("profile_picture")
        @Expose
        private String profilePicture;
        @SerializedName("call_duration")
        @Expose
        private String callDuration;
        @SerializedName("prescription_uploaded")
        @Expose
        private Boolean prescriptionUploaded;
        @SerializedName("call_initiated")
        @Expose
        private String callInitiated;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCallStatusRes() {
            return callStatusRes;
        }


        public String getProfilePicture() {
            return profilePicture;
        }

        public String getCallDuration() {
            return callDuration;
        }

        public Boolean getPrescriptionUploaded() {
            return prescriptionUploaded;
        }

        public void setPrescriptionUploaded(Boolean prescriptionUploaded) {
            this.prescriptionUploaded = prescriptionUploaded;
        }

        public String getCallInitiated() {
            return callInitiated;
        }

        public void setCallInitiated(String callInitiated) {
            this.callInitiated = callInitiated;
        }
    }
}