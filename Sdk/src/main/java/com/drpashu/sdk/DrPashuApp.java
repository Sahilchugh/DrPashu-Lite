package com.drpashu.sdk;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.drpashu.sdk.activity.HomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class DrPashuApp implements DrPashuSdk{
    private static DrPashuApp drPashuApp;
    private Application application;

    public DrPashuApp(Application application) {
        this.application = application;
    }

    public static DrPashuSdk getDrPashuApp(Application application) {
        if (drPashuApp == null) {
            drPashuApp = new DrPashuApp(application);
        }
        return drPashuApp;
    }

    @Override
    public Intent openSdk(Activity activity, JSONObject jsonObject) throws JSONException {
        Intent intent = new Intent(activity, HomeActivity.class);
        intent.putExtra("sdk", jsonObject.toString());
        intent.putExtra("screen", jsonObject.getString("screen"));
        return intent;
    }
}
