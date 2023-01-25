package com.drpashu.sdk.network.model.request;

public class LoginRequest {
    private String email;
    private String phone;
    private String user_id;
    private String device_id;
    private String app_version;

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }
}
