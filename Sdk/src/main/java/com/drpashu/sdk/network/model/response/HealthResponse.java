package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthResponse {
    @SerializedName("All")
    @Expose
    private String all;
    @SerializedName("Head")
    @Expose
    private String head;
    @SerializedName("Tail")
    @Expose
    private String tail;
    @SerializedName("Body")
    @Expose
    private String wing;
    @SerializedName("Leg")
    @Expose
    private String leg;

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    public String getWing() {
        return wing;
    }

    public void setWing(String wing) {
        this.wing = wing;
    }

    public String getLeg() {
        return leg;
    }

    public void setLeg(String leg) {
        this.leg = leg;
    }
}
