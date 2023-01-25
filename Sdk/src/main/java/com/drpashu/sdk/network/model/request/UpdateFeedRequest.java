package com.drpashu.sdk.network.model.request;

public class UpdateFeedRequest {
    private int lot_id;
    private int feed;
    private String date;

    public void setLot_id(int lot_id) {
        this.lot_id = lot_id;
    }

    public void setFeed(int feed) {
        this.feed = feed;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
