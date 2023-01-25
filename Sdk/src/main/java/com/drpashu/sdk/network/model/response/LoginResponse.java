package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("Response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response {

        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("language")
        @Expose
        private Integer language;
        @SerializedName("user_bio")
        @Expose
        private Integer userBio;
        @SerializedName("user_role")
        @Expose
        private Integer userRole;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("onboarding_status")
        @Expose
        private Boolean onboardingStatus;
        @SerializedName("vet_support")
        @Expose
        private Boolean vetSupport;
        @SerializedName("user_support")
        @Expose
        private Boolean userSupport;
        @SerializedName("onboarding_support")
        @Expose
        private Boolean onboardingSupport;

        public Integer getUserRole() {
            return userRole;
        }

        public Boolean getStatus() {
            return status;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public Integer getLanguage() {
            return language;
        }

        public Integer getUserBio() {
            return userBio;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public Boolean getOnboardingStatus() {
            return onboardingStatus;
        }

        public Boolean getVetSupport() {
            return vetSupport;
        }

        public Boolean getUserSupport() {
            return userSupport;
        }

        public Boolean getOnboardingSupport() {
            return onboardingSupport;
        }
    }
}