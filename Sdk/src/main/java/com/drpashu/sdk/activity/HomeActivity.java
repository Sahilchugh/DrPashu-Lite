package com.drpashu.sdk.activity;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;

import com.drpashu.sdk.databinding.ActivityHomeBinding;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

public class HomeActivity extends BaseActivity implements PaymentResultWithDataListener{
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent() != null) {
            if (getIntent().getExtras().getString("sdk") != null) {
                showLoading();
                networking.addUserFromSdk(getIntent().getExtras().getString("sdk"));
            }
        }
    }

    @Override
    public <T> void networkingRequest(@Nullable MethodType methodType, boolean status, @Nullable T error, Object o) {
        if (methodType == MethodType.addUserFromSdk && status) {
            dismissLoading();
            Intent intent = new Intent("screenNavigation");
            intent.putExtra("screen", getIntent().getExtras().getString("screen"));
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        } else if (methodType == MethodType.addUserFromSdk && !status) {
            dismissLoading();
            utils.shortToast("Error Loading DrPashu App");
            onBackPressed();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentId, PaymentData paymentData) {
        Intent intent = new Intent("payment_result");
        intent.putExtra("paymentStatus", true);
        intent.putExtra("paymentId", razorpayPaymentId);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Intent intent = new Intent("payment_result");
        intent.putExtra("paymentStatus", false);
        intent.putExtra("errorDetail", "Error Code - "+ i +"  Error Message - "+ paymentData.getData().toString());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}