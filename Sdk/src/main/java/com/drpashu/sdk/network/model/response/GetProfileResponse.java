package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProfileResponse {
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
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("first_name")
        @Expose
        private String first_name;
        @SerializedName("last_name")
        @Expose
        private String last_name;
        @SerializedName("Phone")
        @Expose
        private String Phone;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("district")
        @Expose
        private String district;
        @SerializedName("pincode")
        @Expose
        private String pincode;
        @SerializedName("farming_type")
        @Expose
        private String farming_type;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("bank_account")
        @Expose
        private String bankAccount;
        @SerializedName("ifsc")
        @Expose
        private String ifsc;
        @SerializedName("pan_card")
        @Expose
        private String panCard;
        @SerializedName("vet_languages")
        @Expose
        private List<String> vetLanguages = null;
        @SerializedName("vet_animals")
        @Expose
        private List<String> vetAnimals = null;
        @SerializedName("farmer_animals")
        @Expose
        private List<String> farmerAnimals = null;
        @SerializedName("vet_degree_details")
        @Expose
        private String vetDegreeDetails;
        @SerializedName("vet_specialization_degree_details")
        @Expose
        private String vetSpecializationDegreeDetails;
        @SerializedName("vet_degree")
        @Expose
        private String vetDegree;
        @SerializedName("vet_specialization_degree")
        @Expose
        private String vetSpecializationDegree;
        @SerializedName("profile_picture")
        @Expose
        private String profilePicture;
        @SerializedName("vet_medical_registration_number")
        @Expose
        private String vetMedicalRegistrationNumber;
        @SerializedName("vet_medical_registration_cert")
        @Expose
        private String vetMedicalRegistrationCert;
        @SerializedName("vet_goverment_id")
        @Expose
        private String vetGovermentId;
        @SerializedName("vet_goverment_id_image_first")
        @Expose
        private String vetGovermentIdImageFirst;
        @SerializedName("vet_goverment_id_image_second")
        @Expose
        private String vetGovermentIdImageSecond;

        public String getBankAccount() {
            return bankAccount;
        }

        public String getIfsc() {
            return ifsc;
        }

        public String getPanCard() {
            return panCard;
        }

        public String getUserId() {
            return userId;
        }

        public String getFirst_name() {
            return first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public String getPhone() {
            return Phone;
        }

        public String getEmail() {
            return email;
        }

        public String getLocation() {
            return location;
        }

        public String getState() {
            return state;
        }

        public String getCountry() {
            return country;
        }

        public String getDistrict() {
            return district;
        }

        public String getPincode() {
            return pincode;
        }

        public String getFarming_type() {
            return farming_type;
        }

        public String getGender() {
            return gender;
        }

        public List<String> getVetLanguages() {
            return vetLanguages;
        }

        public List<String> getVetAnimals() {
            return vetAnimals;
        }

        public List<String> getFarmerAnimals() {
            return farmerAnimals;
        }

        public String getVetDegreeDetails() {
            return vetDegreeDetails;
        }

        public String getVetSpecializationDegreeDetails() {
            return vetSpecializationDegreeDetails;
        }

        public String getVetDegree() {
            return vetDegree;
        }

        public String getVetSpecializationDegree() {
            return vetSpecializationDegree;
        }

        public String getProfilePicture() {
            return profilePicture;
        }

        public String getVetMedicalRegistrationNumber() {
            return vetMedicalRegistrationNumber;
        }

        public String getVetMedicalRegistrationCert() {
            return vetMedicalRegistrationCert;
        }

        public String getVetGovermentId() {
            return vetGovermentId;
        }

        public String getVetGovermentIdImageFirst() {
            return vetGovermentIdImageFirst;
        }

        public String getVetGovermentIdImageSecond() {
            return vetGovermentIdImageSecond;
        }
    }
}