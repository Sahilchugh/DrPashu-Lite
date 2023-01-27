package com.drpashu.sdk.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {

    private static final String TAG = "poultry";
    private static final String TAG_LANGUAGE = "selection";
    public Context context;

    public PreferenceUtils(Context context) {
        this.context = context;
    }

    public void removePreference(){
        getPrefrence().edit().clear().apply();
    }

    private SharedPreferences getPrefrence() {
        return context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    private SharedPreferences getPrefrenceSelection() {
        return context.getSharedPreferences(TAG_LANGUAGE, Context.MODE_PRIVATE);
    }

    public Boolean getLoggedIn(){
        return getPrefrence().getBoolean("login", false);
    }

    public void setLoggedIn(Boolean session){
        getPrefrence().edit().putBoolean("login", session).apply();
    }

    public Boolean getVetSupport(){
        return getPrefrence().getBoolean("vet_support", false);
    }

    public void setVetSupport(Boolean status){
        getPrefrence().edit().putBoolean("vet_support", status).apply();
    }

    public Boolean getUserSupport(){
        return getPrefrence().getBoolean("user_support", false);
    }

    public void setUserSupport(Boolean status){
        getPrefrence().edit().putBoolean("user_support", status).apply();
    }

    public Boolean getOnboardingSupport(){
        return getPrefrence().getBoolean("onboarding_support", false);
    }

    public void setOnboardingSupport(Boolean status){
        getPrefrence().edit().putBoolean("onboarding_support", status).apply();
    }

    public Boolean showConsultationOnboarding(){
        return getPrefrence().getBoolean("consultationOnboarding", false);
    }

    public void setConsultationOnboardingStatus(Boolean consultationOnboarding){
        getPrefrence().edit().putBoolean("consultationOnboarding", consultationOnboarding).apply();
    }

    public String getPhoneNumber(){
        return getPrefrence().getString("phone", "");
    }

    public void setPhoneNumber(String phoneNumber){
        getPrefrence().edit().putString("phone", phoneNumber).apply();
    }

    public String getEmail(){
        return getPrefrence().getString("email","");
    }

    public void setEmail(String email){
        getPrefrence().edit().putString("email",email).apply();
    }

    public String getUsername(){
        return getPrefrence().getString("username","");
    }

    public void setUsername(String username){
        getPrefrence().edit().putString("username",username).apply();
    }

    public String getUserId() {
        return getPrefrence().getString("user-id", "FmMH4HN9UYMsHjTSBdlxLxgyFsj2");
    }

    public void setUserId(String token) {
        getPrefrence().edit().putString("user-id", token).apply();
    }

    public String getUserIdLot() {
        return getPrefrence().getString("user-id-lot", "");
    }

    public void setUserIdLot(String tokenLot) {
        getPrefrence().edit().putString("user-id-lot", tokenLot).apply();
    }

    public String getUserIdForAdmin() {
        return getPrefrence().getString("user-id-admin", "");
    }

    public void setUserIdForAdmin(String userToken) {
        getPrefrence().edit().putString("user-id-admin", userToken).apply();
    }

    public int getLotSize(){
        return getPrefrence().getInt("size", 0);
    }

    public void setLotSize(int size){
        getPrefrence().edit().putInt("size", size).apply();
    }

    public int getUserRole(){
        return getPrefrence().getInt("user_role", 0);
    }

    public void setUserRole(int role){
        getPrefrence().edit().putInt("user_role", role).apply();
    }

    public Boolean getActivityStop(){
        return getPrefrence().getBoolean("activity_stop", false);
    }

    public void setActivityStop(Boolean stop){
        getPrefrence().edit().putBoolean("activity_stop", stop).apply();
    }

    // Preference Selection
    public Boolean getBlockNavigationStatus(){
        return getPrefrenceSelection().getBoolean("back_press", false);
    }

    public void setBlockNavigationStatus(Boolean navigationOption){
        getPrefrenceSelection().edit().putBoolean("back_press", navigationOption).apply();
    }

    public String getFcmToken(){
        return getPrefrenceSelection().getString("fcm", "");
    }

    public void setFcmToken(String token){
        getPrefrenceSelection().edit().putString("fcm", token).apply();
    }

    public int getlanguage_index(){
        return getPrefrenceSelection().getInt("index", 0);
    }

    public void setlanguage_index(int index){
        getPrefrenceSelection().edit().putInt("index", index).apply();
    }

    public Boolean getLanguageSelected(){
        return getPrefrenceSelection().getBoolean("selection", false);
    }

    public void setLanguageSelected(Boolean selection){
        getPrefrenceSelection().edit().putBoolean("selection", selection).apply();
    }
}
