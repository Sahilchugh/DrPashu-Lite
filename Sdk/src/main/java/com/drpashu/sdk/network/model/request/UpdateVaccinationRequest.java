package com.drpashu.sdk.network.model.request;

public class UpdateVaccinationRequest {
    private int vaccination, lot_id;
    private String lot_name;
    private String date;
    private String amount;
    private String info;

    public void setInfo(String info) {
        this.info = info;
    }

    public void setLot_id(int lot_id) {
        this.lot_id = lot_id;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setVaccination(int vaccination) {
        this.vaccination = vaccination;
    }

    public void setLot_name(String lot_name) {
        this.lot_name = lot_name;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
