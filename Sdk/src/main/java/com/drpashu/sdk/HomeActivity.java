package com.drpashu.sdk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;

import com.drpashu.sdk.databinding.ActivityHomeBinding;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

public class HomeActivity extends AppCompatActivity implements PaymentResultWithDataListener {
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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