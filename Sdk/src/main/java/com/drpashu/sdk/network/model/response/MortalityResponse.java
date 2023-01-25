package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MortalityResponse {
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
        @SerializedName("mortality")
        @Expose
        private List<Mortality> mortality = null;
        @SerializedName("count")
        @Expose
        private Integer count;
        @SerializedName("ymin")
        @Expose
        private Integer ymin;
        @SerializedName("ymax")
        @Expose
        private Integer ymax;
        @SerializedName("upperlimit")
        @Expose
        private Integer upperlimit;
        @SerializedName("lowerlimit")
        @Expose
        private Integer lowerlimit;
        @SerializedName("add_default_value")
        @Expose
        private Integer addDefaultValue;
        @SerializedName("animal_dead")
        @Expose
        private String animalDead;
        @SerializedName("animal_remaining")
        @Expose
        private String animalRemaining;
        @SerializedName("mortality_percentage")
        @Expose
        private String mortalityPercentage;

        public List<Mortality> getMortality() {
            return mortality;
        }

        public void setMortality(List<Mortality> mortality) {
            this.mortality = mortality;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Integer getYmin() {
            return ymin;
        }

        public void setYmin(Integer ymin) {
            this.ymin = ymin;
        }

        public Integer getYmax() {
            return ymax;
        }

        public void setYmax(Integer ymax) {
            this.ymax = ymax;
        }

        public Integer getUpperlimit() {
            return upperlimit;
        }

        public void setUpperlimit(Integer upperlimit) {
            this.upperlimit = upperlimit;
        }

        public Integer getLowerlimit() {
            return lowerlimit;
        }

        public void setLowerlimit(Integer lowerlimit) {
            this.lowerlimit = lowerlimit;
        }

        public Integer getAddDefaultValue() {
            return addDefaultValue;
        }

        public void setAddDefaultValue(Integer addDefaultValue) {
            this.addDefaultValue = addDefaultValue;
        }

        public String getAnimalDead() {
            return animalDead;
        }

        public void setAnimalDead(String animalDead) {
            this.animalDead = animalDead;
        }

        public String getAnimalRemaining() {
            return animalRemaining;
        }

        public void setAnimalRemaining(String animalRemaining) {
            this.animalRemaining = animalRemaining;
        }

        public String getMortalityPercentage() {
            return mortalityPercentage;
        }

        public void setMortalityPercentage(String mortalityPercentage) {
            this.mortalityPercentage = mortalityPercentage;
        }

        public class Mortality {

            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("quantity")
            @Expose
            private Integer quantity;
            @SerializedName("number")
            @Expose
            private Integer number;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public Integer getQuantity() {
                return quantity;
            }

            public void setQuantity(Integer quantity) {
                this.quantity = quantity;
            }

            public Integer getNumber() {
                return number;
            }

            public void setNumber(Integer number) {
                this.number = number;
            }

        }
    }
}