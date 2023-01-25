package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateFeedResponse {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("lot_id")
    @Expose
    private String lotId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("feed")
    @Expose
    private String feed;

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

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }
}
