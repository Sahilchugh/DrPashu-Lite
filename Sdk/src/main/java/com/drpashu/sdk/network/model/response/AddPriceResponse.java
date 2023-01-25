package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddPriceResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("lot_id")
    @Expose
    private String lotId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("catagory")
    @Expose
    private String catagory;
    @SerializedName("account")
    @Expose
    private String account;

    public String getStatus() {
        return status;
    }

    public String getUserId() {
        return userId;
    }

    public String getLotId() {
        return lotId;
    }

    public String getDate() {
        return date;
    }

    public String getInfo() {
        return info;
    }

    public String getAmount() {
        return amount;
    }

    public String getCatagory() {
        return catagory;
    }

    public String getAccount() {
        return account;
    }
}
