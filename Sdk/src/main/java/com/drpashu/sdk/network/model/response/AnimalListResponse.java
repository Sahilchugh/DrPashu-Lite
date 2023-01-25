package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnimalListResponse {

    @SerializedName("Animal_class")
    @Expose
    private String animalClass;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    public String getAnimalClass() {
        return animalClass;
    }

    public void setAnimalClass(String animalClass) {
        this.animalClass = animalClass;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("avatar")
        @Expose
        private String avatar;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("class")
        @Expose
        private String _class;
        @SerializedName("animalCheck")
        @Expose
        private Boolean animalCheck;
        private int localImage;

        public Boolean getAnimalCheck() {
            return animalCheck;
        }

        public void setAnimalCheck(Boolean animalCheck) {
            this.animalCheck = animalCheck;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public String getClass_() {
            return _class;
        }

        public void setClass_(String _class) {
            this._class = _class;
        }

        public int getLocalImage() {
            return localImage;
        }

        public void setLocalImage(int localImage) {
            this.localImage = localImage;
        }
    }

}
