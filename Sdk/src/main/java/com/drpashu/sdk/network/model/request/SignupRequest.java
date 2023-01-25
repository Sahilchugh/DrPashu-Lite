package com.drpashu.sdk.network.model.request;

public class SignupRequest {
    private String first_name;
    private String last_name;
    private String location;
    private String phone;
    private String email;
    private String state;
    private String country;
    private String district;
    private String pincode;
    private String gender;
    private String farming_type;
    private String user_role;
    private String farmer_animals;

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFarming_type(String farming_type) {
        this.farming_type = farming_type;
    }

    public void setFarmerAnimals(String farmerAnimals) {
        this.farmer_animals = farmerAnimals;
    }
}
