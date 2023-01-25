package com.drpashu.sdk.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultsResponse implements Parcelable {
    @SerializedName("Disease")
    @Expose
    private List<String> disease = null;
    @SerializedName("Prob")
    @Expose
    private List<Double> prob = null;
    @SerializedName("brief")
    @Expose
    private List<String> brief = null;
    @SerializedName("information")
    @Expose
    private List<String> information = null;
    @SerializedName("link")
    @Expose
    private List<String> link = null;
    @SerializedName("disease_image")
    @Expose
    private List<String> disease_image = null;
    @SerializedName("google_vision")
    @Expose
    private List<String> google_vision = null;
    @SerializedName("treatment")
    @Expose
    private List<String> treatment = null;
    @SerializedName("symptoms")
    @Expose
    private List<String> symptoms = null;
    @SerializedName("animal_verification")
    @Expose
    private Boolean chicken_verification;
    @SerializedName("animal_verification_message")
    @Expose
    private String animal_verification_message;

    protected ResultsResponse(Parcel in) {
        disease = in.createStringArrayList();
        brief = in.createStringArrayList();
        information = in.createStringArrayList();
        link = in.createStringArrayList();
        disease_image = in.createStringArrayList();
        google_vision = in.createStringArrayList();
        treatment = in.createStringArrayList();
        symptoms = in.createStringArrayList();
        byte tmpChicken_verification = in.readByte();
        chicken_verification = tmpChicken_verification == 0 ? null : tmpChicken_verification == 1;
        animal_verification_message = in.readString();
    }

    public static final Creator<ResultsResponse> CREATOR = new Creator<ResultsResponse>() {
        @Override
        public ResultsResponse createFromParcel(Parcel in) {
            return new ResultsResponse(in);
        }

        @Override
        public ResultsResponse[] newArray(int size) {
            return new ResultsResponse[size];
        }
    };

    public List<String> getTreatment() {
        return treatment;
    }

    public String getAnimal_verification_message() {
        return animal_verification_message;
    }

    public List<String> getSymptoms() {
        return symptoms;
    }

    public Boolean getChicken_verification() {
        return chicken_verification;
    }

    public List<String> getGoogle_vision() {
        return google_vision;
    }

    public List<String> getDisease() {
        return disease;
    }

    public List<Double> getProb() {
        return prob;
    }

    public List<String> getBrief() {
        return brief;
    }

    public List<String> getInformation() {
        return information;
    }

    public List<String> getDisease_image() {
        return disease_image;
    }

    public List<String> getLink() {
        return link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(disease);
        parcel.writeStringList(brief);
        parcel.writeStringList(information);
        parcel.writeStringList(link);
        parcel.writeStringList(disease_image);
        parcel.writeStringList(google_vision);
        parcel.writeStringList(treatment);
        parcel.writeStringList(symptoms);
        parcel.writeByte((byte) (chicken_verification == null ? 0 : chicken_verification ? 1 : 2));
        parcel.writeString(animal_verification_message);
    }
}
