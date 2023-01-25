package com.drpashu.sdk.network.model.request;

public class SignupOptionalRequest {
    private String mother_name;
    private String age;
    private String landline;
    private String identity_number;
    private String family_member;
    private String bank_account;
    private String ifsc;
    private String subsidy_information;
    private String adhaar_number;
    private String farmer_number;
    private String cooperative;
    private String pan_card;
    private String qualification_id;
    private String jobcard_holder;
    private String kissan_card_holder;

    public void setCooperative(String cooperative) {
        this.cooperative = cooperative;
    }

    public void setPan_card(String pan_card) {
        this.pan_card = pan_card;
    }

    public void setAdhaar_number(String adhaar_number) {
        this.adhaar_number = adhaar_number;
    }

    public void setFarmer_number(String farmer_number) {
        this.farmer_number = farmer_number;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setQualification_id(String qualification_id) {
        this.qualification_id = qualification_id;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public void setIdentity_number(String identity_number) {
        this.identity_number = identity_number;
    }

    public void setFamily_member(String family_member) {
        this.family_member = family_member;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public void setSubsidy_information(String subsidy_information) {
        this.subsidy_information = subsidy_information;
    }

    public void setJobcard_holder(String jobcard_holder) {
        this.jobcard_holder = jobcard_holder;
    }

    public void setKissan_card_holder(String kissan_card_holder) {
        this.kissan_card_holder = kissan_card_holder;
    }
}
