package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductionResponse {

    @SerializedName("production")
    @Expose
    private List<Production> production = null;
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
    @SerializedName("produce")
    @Expose
    private String produce;
    @SerializedName("add_default_value")
    @Expose
    private Integer add_default_value;
    @SerializedName("sell_default_value")
    @Expose
    private Integer sell_default_value;

    public Integer getAdd_default_value() {
        return add_default_value;
    }

    public void setAdd_default_value(Integer add_default_value) {
        this.add_default_value = add_default_value;
    }

    public Integer getSell_default_value() {
        return sell_default_value;
    }

    public void setSell_default_value(Integer sell_default_value) {
        this.sell_default_value = sell_default_value;
    }

    public String getProduce() {
        return produce;
    }

    public void setProduce(String produce) {
        this.produce = produce;
    }

    public List<Production> getEggs() {
        return production;
    }

    public void setEggs(List<Production> production) {
        this.production = production;
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

    public class Production {

        @SerializedName("number")
        @Expose
        private Integer number;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("production")
        @Expose
        private String production;

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

        public String getEggs() {
            return production;
        }

        public void setEggs(String eggs) {
            this.production = eggs;
        }

    }
}