package com.drpashu.sdk.network.model.request;

public class AddFarmRequest {

    private String animal_id;
    private String animal_quantity;
    private String is_loan;
    private String animal_dob;
    private String lot_name;
    private String emi_start_date;
    private String EMI;
    private String duration_months;
    private String animal_type;

    public void setIs_loan(String is_loan) {
        this.is_loan = is_loan;
    }

    public void setEMI(String EMI) {
        this.EMI = EMI;
    }

    public void setDuration_months(String duration_months) {
        this.duration_months = duration_months;
    }

    public void setEmi_start_date(String emi_start_date) {
        this.emi_start_date = emi_start_date;
    }

    public void setAnimal_id(String animal_id) {
        this.animal_id = animal_id;
    }

    public void setAnimal_quantity(String animal_quantity) {
        this.animal_quantity = animal_quantity;
    }

    public void setAnimal_dob(String animal_dob) {
        this.animal_dob = animal_dob;
    }

    public void setAnimal_type(String animal_type) {
        this.animal_type = animal_type;
    }

    public void setLot_name(String lot_name) {
        this.lot_name = lot_name;
    }
}