package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BreedListResponse {

    @SerializedName("Response")
    @Expose
    private List<Response> response = null;

    public List<Response> getResponse() {
        return response;
    }

    @SerializedName("animal_default")
    @Expose
    private Integer animal_default;

    public Integer getAnimal_default() {
        return animal_default;
    }

    public void setAnimal_default(Integer animal_default) {
        this.animal_default = animal_default;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public class Response {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("breed")
        @Expose
        private String breed;
        @SerializedName("avatar")
        @Expose
        private String avatar;
        @SerializedName("type")
        @Expose
        private String type;

        public String getBreed() {
            return breed;
        }

        public void setBreed(String breed) {
            this.breed = breed;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }

}
