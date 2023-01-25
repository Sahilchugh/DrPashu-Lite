package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CallDetailResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("vet_id")
        @Expose
        private Integer vetId;
        @SerializedName("farmer_id")
        @Expose
        private Integer farmerId;
        @SerializedName("Prescription_image_first")
        @Expose
        private String prescriptionImageFirst;
        @SerializedName("Prescription_image_second")
        @Expose
        private String prescriptionImageSecond;
        @SerializedName("details")
        @Expose
        private String details;
        @SerializedName("duration_of_call_of_farmer")
        @Expose
        private String durationOfCallOfFarmer;
        @SerializedName("duration_of_call_of_vet")
        @Expose
        private String durationOfCallOfVet;
        @SerializedName("lot_id_id")
        @Expose
        private String lotIdId;
        @SerializedName("Animal")
        @Expose
        private String animal;
        @SerializedName("health_val")
        @Expose
        private Boolean healthVal;
        @SerializedName("analysis_id")
        @Expose
        private Integer analysisId;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("modified_at")
        @Expose
        private String modifiedAt;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("symptoms_list")
        @Expose
        private List<String> symptomsList = null;
        @SerializedName("analysis_image")
        @Expose
        private String analysisImage;
        @SerializedName("postmortem_image")
        @Expose
        private String postmortemImage;
        @SerializedName("lot_exists")
        @Expose
        private Boolean lotExists;
        @SerializedName("Breed")
        @Expose
        private String Breed;
        @SerializedName("quantity")
        @Expose
        private String quantity;
        @SerializedName("DOB")
        @Expose
        private String DOB;
        @SerializedName("call_status_res")
        @Expose
        private String callStatusRes;
        @SerializedName("profile_picture")
        @Expose
        private String profilePicture;
        @SerializedName("call_duration")
        @Expose
        private String callDuration;
        @SerializedName("call_initiated")
        @Expose
        private String callInitiated;

        public String getCallInitiated() {
            return callInitiated;
        }

        public void setCallInitiated(String callInitiated) {
            this.callInitiated = callInitiated;
        }

        public String getPostmortemImage() {
            return postmortemImage;
        }

        public void setPostmortemImage(String postmortemImage) {
            this.postmortemImage = postmortemImage;
        }

        public String getCallStatusRes() {
            return callStatusRes;
        }

        public void setCallStatusRes(String callStatusRes) {
            this.callStatusRes = callStatusRes;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getVetId() {
            return vetId;
        }

        public void setVetId(Integer vetId) {
            this.vetId = vetId;
        }

        public Integer getFarmerId() {
            return farmerId;
        }

        public void setFarmerId(Integer farmerId) {
            this.farmerId = farmerId;
        }

        public String getPrescriptionImageFirst() {
            return prescriptionImageFirst;
        }

        public void setPrescriptionImageFirst(String prescriptionImageFirst) {
            this.prescriptionImageFirst = prescriptionImageFirst;
        }

        public String getPrescriptionImageSecond() {
            return prescriptionImageSecond;
        }

        public void setPrescriptionImageSecond(String prescriptionImageSecond) {
            this.prescriptionImageSecond = prescriptionImageSecond;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getDurationOfCallOfFarmer() {
            return durationOfCallOfFarmer;
        }

        public void setDurationOfCallOfFarmer(String durationOfCallOfFarmer) {
            this.durationOfCallOfFarmer = durationOfCallOfFarmer;
        }

        public String getDurationOfCallOfVet() {
            return durationOfCallOfVet;
        }

        public void setDurationOfCallOfVet(String durationOfCallOfVet) {
            this.durationOfCallOfVet = durationOfCallOfVet;
        }

        public String getLotIdId() {
            return lotIdId;
        }

        public void setLotIdId(String lotIdId) {
            this.lotIdId = lotIdId;
        }

        public Boolean getHealthVal() {
            return healthVal;
        }

        public void setHealthVal(Boolean healthVal) {
            this.healthVal = healthVal;
        }

        public Integer getAnalysisId() {
            return analysisId;
        }

        public void setAnalysisId(Integer analysisId) {
            this.analysisId = analysisId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getModifiedAt() {
            return modifiedAt;
        }

        public void setModifiedAt(String modifiedAt) {
            this.modifiedAt = modifiedAt;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public List<String> getSymptomsList() {
            return symptomsList;
        }

        public void setSymptomsList(List<String> symptomsList) {
            this.symptomsList = symptomsList;
        }

        public String getAnalysisImage() {
            return analysisImage;
        }

        public void setAnalysisImage(String analysisImage) {
            this.analysisImage = analysisImage;
        }

        public Boolean getLotExists() {
            return lotExists;
        }

        public void setLotExists(Boolean lotExists) {
            this.lotExists = lotExists;
        }

        public String getAnimal() {
            return animal;
        }

        public void setAnimal(String animal) {
            this.animal = animal;
        }

        public String getBreed() {
            return Breed;
        }

        public void setBreed(String breed) {
            Breed = breed;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getDOB() {
            return DOB;
        }

        public void setDOB(String DOB) {
            this.DOB = DOB;
        }

        public String getProfilePicture() {
            return profilePicture;
        }

        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }

        public String getCallDuration() {
            return callDuration;
        }

        public void setCallDuration(String callDuration) {
            this.callDuration = callDuration;
        }
    }
}