package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;
    @SerializedName("user_id")
    @Expose
    private String userId;

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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public class Data {

        @SerializedName("lot_id")
        @Expose
        private String lotId;
        @SerializedName("lot_name")
        @Expose
        private String lotName;

        public String getMonth_sign() {
            return month_sign;
        }

        public String getTotal_sign() {
            return total_sign;
        }

        @SerializedName("production")
        @Expose
        private String eggs;
        @SerializedName("feed")
        @Expose
        private String feed;
        @SerializedName("vaccination_overdue")
        @Expose
        private String vaccinationOverdue;
        @SerializedName("profit_month")
        @Expose
        private String profit_month;
        @SerializedName("profit_total")
        @Expose
        private String profit_total;
        @SerializedName("month_sign")
        @Expose
        private String month_sign;
        @SerializedName("total_sign")
        @Expose
        private String total_sign;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("last_name")
        @Expose
        private String last_name;
        @SerializedName("first_name")
        @Expose
        private String first_name;
        @SerializedName("produce")
        @Expose
        private String produce;
        @SerializedName("animal_type")
        @Expose
        private String animal_type;

        public String getStatus() {
            return status;
        }

        public String getLast_name() {
            return last_name;
        }

        public String getFirst_name() {
            return first_name;
        }

        public String getLotId() {
            return lotId;
        }

        public String getLotName() {
            return lotName;
        }

        public String getEggs() {
            return eggs;
        }

        public String getFeed() {
            return feed;
        }

        public String getVaccinationOverdue() {
            return vaccinationOverdue;
        }

        public String getProfit_month() {
            return profit_month;
        }

        public String getProfit_total() {
            return profit_total;
        }

        public String getProduce() {
            return produce;
        }

        public String getAnimal_type() {
            return animal_type;
        }
    }
}