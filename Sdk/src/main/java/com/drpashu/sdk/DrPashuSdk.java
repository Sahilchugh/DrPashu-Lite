package com.drpashu.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.drpashu.sdk.network.model.request.DrPashuRequest;

import org.json.JSONObject;

public interface DrPashuSdk {

    Intent openSdk(Activity activity, String screen, JSONObject jsonObject);
}
