package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VetListResponse {
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
        @SerializedName("categories_list")
        @Expose
        private CategoriesList categoriesList;
        @SerializedName("vet_list")
        @Expose
        private List<Vet> vetList = null;
        @SerializedName("free_call_status")
        @Expose
        private String freeCallStatus;

        public CategoriesList getCategoriesList() {
            return categoriesList;
        }

        public void setCategoriesList(CategoriesList categoriesList) {
            this.categoriesList = categoriesList;
        }

        public List<Vet> getVetList() {
            return vetList;
        }

        public void setVetList(List<Vet> vetList) {
            this.vetList = vetList;
        }

        public String getFreeCallStatus() {
            return freeCallStatus;
        }

        public void setFreeCallStatus(String freeCallStatus) {
            this.freeCallStatus = freeCallStatus;
        }

        public class CategoriesList {
            @SerializedName("OPD_mrp")
            @Expose
            private Integer oPDMrp;
            @SerializedName("OPD_offer_price")
            @Expose
            private Integer oPDOfferPrice;
            @SerializedName("specialist_mrp")
            @Expose
            private Integer specialistMrp;
            @SerializedName("specialist_offer_price")
            @Expose
            private Integer specialistOfferPrice;
            @SerializedName("company_mrp")
            @Expose
            private Integer companyMrp;
            @SerializedName("company_offer_price")
            @Expose
            private Integer companyOfferPrice;
            @SerializedName("government_mrp")
            @Expose
            private Integer governmentMrp;
            @SerializedName("government_offer_price")
            @Expose
            private Integer governmentOfferPrice;
            @SerializedName("family_mrp")
            @Expose
            private Integer familyMrp;
            @SerializedName("family_offer_price")
            @Expose
            private Integer familyOfferPrice;
            @SerializedName("support_mrp")
            @Expose
            private Integer supportMrp;
            @SerializedName("support_offer_price")
            @Expose
            private Integer supportOfferPrice;

            public Integer getOPDMrp() {
                return oPDMrp;
            }

            public void setOPDMrp(Integer oPDMrp) {
                this.oPDMrp = oPDMrp;
            }

            public Integer getOPDOfferPrice() {
                return oPDOfferPrice;
            }

            public void setOPDOfferPrice(Integer oPDOfferPrice) {
                this.oPDOfferPrice = oPDOfferPrice;
            }

            public Integer getSpecialistMrp() {
                return specialistMrp;
            }

            public void setSpecialistMrp(Integer specialistMrp) {
                this.specialistMrp = specialistMrp;
            }

            public Integer getSpecialistOfferPrice() {
                return specialistOfferPrice;
            }

            public void setSpecialistOfferPrice(Integer specialistOfferPrice) {
                this.specialistOfferPrice = specialistOfferPrice;
            }

            public Integer getCompanyMrp() {
                return companyMrp;
            }

            public void setCompanyMrp(Integer companyMrp) {
                this.companyMrp = companyMrp;
            }

            public Integer getCompanyOfferPrice() {
                return companyOfferPrice;
            }

            public void setCompanyOfferPrice(Integer companyOfferPrice) {
                this.companyOfferPrice = companyOfferPrice;
            }

            public Integer getGovernmentMrp() {
                return governmentMrp;
            }

            public void setGovernmentMrp(Integer governmentMrp) {
                this.governmentMrp = governmentMrp;
            }

            public Integer getGovernmentOfferPrice() {
                return governmentOfferPrice;
            }

            public void setGovernmentOfferPrice(Integer governmentOfferPrice) {
                this.governmentOfferPrice = governmentOfferPrice;
            }

            public Integer getFamilyMrp() {
                return familyMrp;
            }

            public void setFamilyMrp(Integer familyMrp) {
                this.familyMrp = familyMrp;
            }

            public Integer getFamilyOfferPrice() {
                return familyOfferPrice;
            }

            public void setFamilyOfferPrice(Integer familyOfferPrice) {
                this.familyOfferPrice = familyOfferPrice;
            }

            public Integer getSupportMrp() {
                return supportMrp;
            }

            public void setSupportMrp(Integer supportMrp) {
                this.supportMrp = supportMrp;
            }

            public Integer getSupportOfferPrice() {
                return supportOfferPrice;
            }

            public void setSupportOfferPrice(Integer supportOfferPrice) {
                this.supportOfferPrice = supportOfferPrice;
            }
        }

        public class Vet {
            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("first_name")
            @Expose
            private String firstName;
            @SerializedName("last_name")
            @Expose
            private String lastName;
            @SerializedName("vet_languages")
            @Expose
            private List<String> vetLanguages = null;
            @SerializedName("vet_animals")
            @Expose
            private List<String> vetAnimals = null;
            @SerializedName("vet_status")
            @Expose
            private String vetStatus;
            @SerializedName("vet_degree")
            @Expose
            private String vetDegree;
            @SerializedName("vet_specialization_degree")
            @Expose
            private String vetSpecializationDegree;
            @SerializedName("vet_type")
            @Expose
            private String vetType;
            @SerializedName("vet_profile_image")
            @Expose
            private String vetProfileImage;
            @SerializedName("company_name")
            @Expose
            private String companyName;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
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

            public List<String> getVetLanguages() {
                return vetLanguages;
            }

            public void setVetLanguages(List<String> vetLanguages) {
                this.vetLanguages = vetLanguages;
            }

            public List<String> getVetAnimals() {
                return vetAnimals;
            }

            public void setVetAnimals(List<String> vetAnimals) {
                this.vetAnimals = vetAnimals;
            }

            public String getVetStatus() {
                return vetStatus;
            }

            public void setVetStatus(String vetStatus) {
                this.vetStatus = vetStatus;
            }

            public String getVetDegree() {
                return vetDegree;
            }

            public void setVetDegree(String vetDegree) {
                this.vetDegree = vetDegree;
            }

            public String getVetSpecializationDegree() {
                return vetSpecializationDegree;
            }

            public void setVetSpecializationDegree(String vetSpecializationDegree) {
                this.vetSpecializationDegree = vetSpecializationDegree;
            }

            public String getVetType() {
                return vetType;
            }

            public void setVetType(String vetType) {
                this.vetType = vetType;
            }

            public String getVetProfileImage() {
                return vetProfileImage;
            }

            public void setVetProfileImage(String vetProfileImage) {
                this.vetProfileImage = vetProfileImage;
            }

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }
        }
    }
}