package com.drpashu.sdk.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {
    private static final String TAG = "drPashuSdk";
    private Context context;

    public PreferenceUtils(Context context) {
        this.context = context;
    }

    private SharedPreferences getPrefrence() {
        return context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    public String getUserId() {
        return getPrefrence().getString("user-id", "");
    }

    public void setUserId(String token) {
        getPrefrence().edit().putString("user-id", token).apply();
    }

    public Boolean getBlockNavigationStatus(){
        return getPrefrence().getBoolean("back_press", false);
    }

    public void setBlockNavigationStatus(Boolean navigationOption){
        getPrefrence().edit().putBoolean("back_press", navigationOption).apply();
    }

    public String getFcmToken(){
        return getPrefrence().getString("fcm", "");
    }

    public void setFcmToken(String token){
        getPrefrence().edit().putString("fcm", token).apply();
    }

    public String getAnimal(){
        return getPrefrence().getString("animal", "Buffalo");
    }

    public void setAnimal(String animal){
        getPrefrence().edit().putString("animal", animal).apply();
    }
}