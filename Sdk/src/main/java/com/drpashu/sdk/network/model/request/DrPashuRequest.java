package com.drpashu.sdk.network.model.request;

import android.os.Parcel;
import android.os.Parcelable;

public class DrPashuRequest implements Parcelable {
    private String api_key;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String device_id;
    private String gender;
    private String location;
    private String state;
    private String country;
    private String district;
    private String pincode;

    public DrPashuRequest(Parcel in) {
        api_key = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        phone_number = in.readString();
        device_id = in.readString();
        gender = in.readString();
        location = in.readString();
        state = in.readString();
        country = in.readString();
        district = in.readString();
        pincode = in.readString();
    }

    public static final Creator<DrPashuRequest> CREATOR = new Creator<DrPashuRequest>() {
        @Override
        public DrPashuRequest createFromParcel(Parcel in) {
            return new DrPashuRequest(in);
        }

        @Override
        public DrPashuRequest[] newArray(int size) {
            return new DrPashuRequest[size];
        }
    };

    public DrPashuRequest() {

    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(api_key);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(phone_number);
        dest.writeString(device_id);
        dest.writeString(gender);
        dest.writeString(location);
        dest.writeString(state);
        dest.writeString(country);
        dest.writeString(district);
        dest.writeString(pincode);
    }
}