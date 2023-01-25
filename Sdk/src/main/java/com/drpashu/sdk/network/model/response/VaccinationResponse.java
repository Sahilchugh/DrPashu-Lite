package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VaccinationResponse {

    @SerializedName("vaccine_id")
    @Expose
    private Integer vaccineId;
    @SerializedName("animal_name")
    @Expose
    private String animalName;
    @SerializedName("name_of_lot")
    @Expose
    private String nameOfLot;
    @SerializedName("vaccination_schedule")
    @Expose
    private String vaccinationSchedule;
    @SerializedName("vaccine")
    @Expose
    private String vaccine;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("vaccine_date")
    @Expose
    private String vaccineDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("information")
    @Expose
    private String information;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("lot_id")
    @Expose
    private int lot_id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;

    public String getAnimal_type() {
        return animal_type;
    }

    @SerializedName("animal_type")
    @Expose
    private String animal_type;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getVaccineId() {
        return vaccineId;
    }

    public String getAnimalName() {
        return animalName;
    }

    public String getNameOfLot() {
        return nameOfLot;
    }

    public String getVaccinationSchedule() {
        return vaccinationSchedule;
    }

    public String getVaccine() {
        return vaccine;
    }

    public String getType() {
        return type;
    }

    public String getVaccineDate() {
        return vaccineDate;
    }

    public String getStatus() {
        return status;
    }

    public String getInformation() {
        return information;
    }

    public String getLink() {
        return link;
    }

    public int getLot_id() {
        return lot_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getOwner() {
        return owner;
    }
}
