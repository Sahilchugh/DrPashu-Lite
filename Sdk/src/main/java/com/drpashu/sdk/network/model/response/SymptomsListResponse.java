package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SymptomsListResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("part_of_chicken")
    @Expose
    private String partOfChicken;
    @SerializedName("symptoms")
    @Expose
    private String symptoms;
    @SerializedName("diagnosis")
    @Expose
    private String diagnosis;
    @SerializedName("part")
    @Expose
    private String part;
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPartOfChicken() {
        return partOfChicken;
    }

    public void setPartOfChicken(String partOfChicken) {
        this.partOfChicken = partOfChicken;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }
}