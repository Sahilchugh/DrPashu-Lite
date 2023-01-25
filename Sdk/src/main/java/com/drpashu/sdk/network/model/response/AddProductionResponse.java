package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddProductionResponse {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("lot_id")
    @Expose
    private String lotId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("quantity")
    @Expose
    private String quantity;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
