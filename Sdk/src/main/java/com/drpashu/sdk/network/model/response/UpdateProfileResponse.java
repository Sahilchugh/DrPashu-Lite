package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProfileResponse {

    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("user-id")
    @Expose
    private String userId;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("registration_no")
    @Expose
    private Object registrationNo;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("language")
    @Expose
    private Integer language;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("farming_type")
    @Expose
    private Integer farmingType;
    @SerializedName("mother_name")
    @Expose
    private String motherName;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("qualification_id")
    @Expose
    private String qualificationId;
    @SerializedName("landline")
    @Expose
    private String landline;
    @SerializedName("identity_number")
    @Expose
    private String identityNumber;
    @SerializedName("family_member")
    @Expose
    private String familyMember;
    @SerializedName("bank_account")
    @Expose
    private String bankAccount;
    @SerializedName("ifsc")
    @Expose
    private String ifsc;
    @SerializedName("loan")
    @Expose
    private String loan;
    @SerializedName("subsidy_information")
    @Expose
    private String subsidyInformation;
    @SerializedName("jobcard_holder")
    @Expose
    private String jobcardHolder;
    @SerializedName("kissan_card_holder")
    @Expose
    private String kissanCardHolder;
    @SerializedName("coorporative")
    @Expose
    private String coorporative;
    @SerializedName("pan_card")
    @Expose
    private String pan_card;
    @SerializedName("adhaar_number")
    @Expose
    private String adhaar_number;
    @SerializedName("farmer_number")
    @Expose
    private String farmer_number;

    public String getAdhaar_number() {
        return adhaar_number;
    }

    public String getFarmer_number() {
        return farmer_number;
    }

    public String getCoorporative() {
        return coorporative;
    }

    public String getPan_card() {
        return pan_card;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Object getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(Object registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
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

    public Integer getFarmingType() {
        return farmingType;
    }

    public void setFarmingType(Integer farmingType) {
        this.farmingType = farmingType;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getQualificationId() {
        return qualificationId;
    }

    public void setQualificationId(String qualificationId) {
        this.qualificationId = qualificationId;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(String familyMember) {
        this.familyMember = familyMember;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
    }

    public String getSubsidyInformation() {
        return subsidyInformation;
    }

    public void setSubsidyInformation(String subsidyInformation) {
        this.subsidyInformation = subsidyInformation;
    }

    public String getJobcardHolder() {
        return jobcardHolder;
    }

    public void setJobcardHolder(String jobcardHolder) {
        this.jobcardHolder = jobcardHolder;
    }

    public String getKissanCardHolder() {
        return kissanCardHolder;
    }

    public void setKissanCardHolder(String kissanCardHolder) {
        this.kissanCardHolder = kissanCardHolder;
    }
}
