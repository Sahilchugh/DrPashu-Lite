package com.drpashu.sdk.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.drpashu.sdk.activity.HomeActivity;
import com.drpashu.sdk.R;
import com.drpashu.sdk.network.Networking;
import com.drpashu.sdk.network.NetworkingInterface;
import com.drpashu.sdk.utils.PreferenceUtils;
import com.drpashu.sdk.utils.Utils;

public class BaseFragment extends Fragment implements NetworkingInterface {
    protected Context context;
    protected HomeActivity activity;
    protected Networking networking;
    protected PreferenceUtils preferenceUtils;
    protected Utils utils;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getContext();
        this.activity = (HomeActivity) getActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        networking = new Networking(context, activity, this);
        utils = new Utils(context, activity);
        preferenceUtils = new PreferenceUtils(context);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(utils.getStringValue(R.string.loading));
        progressDialog.setCancelable(false);
    }

    protected void showLoading(){
        progressDialog.show();
    }

    protected void dismissLoading(){
        progressDialog.dismiss();
    }

    @Override
    public <T> void networkingRequest(@Nullable MethodType methodType, boolean status, @Nullable T error, Object object) {
    }
}
