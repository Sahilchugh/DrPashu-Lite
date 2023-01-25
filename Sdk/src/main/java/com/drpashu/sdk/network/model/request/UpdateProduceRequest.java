package com.drpashu.sdk.network.model.request;

public class UpdateProduceRequest {
    private int lot_id, quantity;
    private String date;

    public void setLot_id(int lot_id) {
        this.lot_id = lot_id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
