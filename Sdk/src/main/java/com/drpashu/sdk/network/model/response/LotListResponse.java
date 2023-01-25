package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LotListResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("lot_name")
    @Expose
    private String lotName;
    @SerializedName("animal_quantity")
    @Expose
    private String animal_quantity;
    @SerializedName("animal_dob")
    @Expose
    private String animal_dob;
    @SerializedName("is_loan")
    @Expose
    private String isLoan;
    @SerializedName("duration_month")
    @Expose
    private String durationMonths;
    @SerializedName("EMI")
    @Expose
    private String emi;
    @SerializedName("emi_started")
    @Expose
    private int emi_started;
    @SerializedName("emi_start_date")
    @Expose
    private String emi_start_date;
    @SerializedName("breed_name")
    @Expose
    private String breedName;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("animal_name")
    @Expose
    private String animal_name;
    @SerializedName("user-id")
    @Expose
    private String user_id_vle;
    @SerializedName("animal_url")
    @Expose
    private String animal_url;
    @SerializedName("animal_remaining")
    @Expose
    private String animalRemaining;
    @SerializedName("animal_dead")
    @Expose
    private String animalDead;

    public String getAnimal_url() {
        return animal_url;
    }

    public String getAnimal_name() {
        return animal_name;
    }

    public String getUser_id_vle() {
        return user_id_vle;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getOwner() {
        return owner;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getEmi_started() {
        return emi_started;
    }

    public String getEmi_start_date() {
        return emi_start_date;
    }

    public Integer getId() {
        return id;
    }

    public String getAnimal_quantity() {
        return animal_quantity;
    }

    public String getAnimal_dob() {
        return animal_dob;
    }

    public String getLotName() {
        return lotName;
    }

    public String getIsLoan() {
        return isLoan;
    }

    public String getDurationMonths() {
        return durationMonths;
    }

    public String getEmi() {
        return emi;
    }

    public String getBreedName() {
        return breedName;
    }

    public String getAnimalRemaining() {
        return animalRemaining;
    }

    public String getAnimalDead() {
        return animalDead;
    }
}
