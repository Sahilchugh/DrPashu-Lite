package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedResponse {

    @SerializedName("feed")
    @Expose
    private List<Feed> feed = null;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("upperlimit")
    @Expose
    private Integer upperlimit;
    @SerializedName("lowerlimit")
    @Expose
    private Integer lowerlimit;
    @SerializedName("ymin")
    @Expose
    private Integer ymin;
    @SerializedName("ymax")
    @Expose
    private Integer ymax;
    @SerializedName("add_default_value")
    @Expose
    private Integer add_default_value;
    @SerializedName("buy_default_value")
    @Expose
    private Integer buy_default_value;

    public Integer getAdd_default_value() {
        return add_default_value;
    }

    public void setAdd_default_value(Integer add_default_value) {
        this.add_default_value = add_default_value;
    }

    public Integer getBuy_default_value() {
        return buy_default_value;
    }

    public void setBuy_default_value(Integer buy_default_value) {
        this.buy_default_value = buy_default_value;
    }

    public List<Feed> getFeed() {
        return feed;
    }

    public void setEggs(List<Feed> eggs) {
        this.feed = feed;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getUpperlimit() {
        return upperlimit;
    }

    public void setUpperlimit(Integer upperlimit) {
        this.upperlimit = upperlimit;
    }

    public Integer getLowerlimit() {
        return lowerlimit;
    }

    public void setLowerlimit(Integer lowerlimit) {
        this.lowerlimit = lowerlimit;
    }

    public Integer getYmin() {
        return ymin;
    }

    public void setYmin(Integer ymin) {
        this.ymin = ymin;
    }

    public Integer getYmax() {
        return ymax;
    }

    public void setYmax(Integer ymax) {
        this.ymax = ymax;
    }

    public class Feed {

        @SerializedName("number")
        @Expose
        private Integer number;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("feed")
        @Expose
        private String feed;

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
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
}