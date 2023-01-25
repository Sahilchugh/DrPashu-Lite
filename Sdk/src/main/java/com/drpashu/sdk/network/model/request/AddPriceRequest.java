package com.drpashu.sdk.network.model.request;

public class AddPriceRequest {

    private String lot_id, info;
    private String amount, date;

    public void setLot_id(String lot_id) {
        this.lot_id = lot_id;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }
}