package com.drpashu.sdk.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("temperature")
    @Expose
    private Double temperature;
    @SerializedName("weather")
    @Expose
    private String weather;
    @SerializedName("weather_description")
    @Expose
    private String weatherDescription;
    @SerializedName("alert")
    @Expose
    private String alert;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("humidity")
    @Expose
    private String humidity;

    public String getHumidity() {
        return humidity;
    }

    public String getDistrict() {
        return district;
    }

    public String getUserId() {
        return userId;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getLocation() {
        return location;
    }

    public Double getTemperature() {
        return temperature;
    }

    public String getWeather() {
        return weather;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getAlert() {
        return alert;
    }

    public String getIcon() {
        return icon;
    }
}