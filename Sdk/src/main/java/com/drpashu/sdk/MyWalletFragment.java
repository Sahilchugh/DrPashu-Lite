package com.drpashu.sdk;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drpashu.sdk.R;
import com.drpashu.sdk.adapter.WalletTransactionAdapter;
import com.drpashu.sdk.databinding.FragmentMyWalletBinding;
import com.drpashu.sdk.dialog.PaymentFailedDialog;
import com.drpashu.sdk.network.NetworkingInterface;
import com.drpashu.sdk.network.model.response.RazorpayOrderIdResponse;
import com.drpashu.sdk.network.model.response.WalletTransactionResponse;
import com.razorpay.Checkout;

import org.json.JSONObject;

import java.util.List;

public class MyWalletFragment extends BaseFragment {
    private FragmentMyWalletBinding binding;
    private Checkout checkout;
    private LocalBroadcastManager localBroadcastManager;
    private String paymentId = "";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        localBroadcastManager = LocalBroadcastManager.getInstance(context);

        IntentFilter paymentResultIntent = new IntentFilter();
        paymentResultIntent.addAction("payment_result");
        localBroadcastManager.registerReceiver(paymentResultBroadcastReceiver , paymentResultIntent);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        localBroadcastManager.unregisterReceiver(paymentResultBroadcastReceiver);
    }

    private final BroadcastReceiver paymentResultBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (intent.getExtras().getBoolean("paymentStatus")) {
                    paymentId = intent.getExtras().getString("paymentId");
                    utils.shortToast(utils.getStringValue(R.string.payment_done_adding_coins));

                    showLoading();
                    networking.addCoinsToWallet(binding.coinInput.getText().toString(), paymentId);
                } else {
                    utils.shortToast(utils.getStringValue(R.string.payment_unsuccessful));

                    String errorMessage = intent.getExtras().getString("errorDetail");
                    errorMessage = errorMessage.replace(",", " ");
                    utils.updateErrorEvent("Add Money Payment Failed Event", "Amount- "+ binding.coinInput.getText().toString() + "  " + errorMessage);

                    showPaymentFailure();
                }
            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyWalletBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Checkout.preload(activity.getApplicationContext());
        checkout = new Checkout();
        checkout.setKeyID(getString(R.string.razorpay_key_id));

        showLoading();
        networking.fetchBalance();

        binding.coinInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (binding.coinInput.getText().length() != 0){
                    binding.addCoinsBtn.setEnabled(true);
                    binding.addCoinsBtn.setText(utils.getStringValue(R.string.proceed_to_pay)+" ₹"+ binding.coinInput.getText().toString());
                } else {
                    binding.addCoinsBtn.setText(utils.getStringValue(R.string.enter_coins_to_add));
                    binding.addCoinsBtn.setEnabled(false);
                }
            }
        });

        binding.addCoinsBtn.setOnClickListener(v -> {
            if (Integer.parseInt(binding.coinInput.getText().toString()) == 0)
                utils.shortToast("Enter Valid Amount");
            else {
                showLoading();
                networking.getRazorpayOrderId(Integer.parseInt(binding.coinInput.getText().toString()) * 100 + "");
            }
        });
    }

    private void initiatePayment(String orderId){
        try {
            JSONObject options = new JSONObject();

            options.put("name", "DrPashu Technologies");
            options.put("description", "24/7 Vet in your Smartphone");
            options.put("order_id", orderId);//from response of step 3.
            options.put("prefill.email", preferenceUtils.getEmail());
            try {
                options.put("prefill.contact",preferenceUtils.getPhoneNumber().substring(3));
            } catch (Exception e){
                Log.e("phone_capture", e.getMessage()+"");
            }
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", false);
            options.put("retry", retryObj);
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
            utils.updateErrorEvent("Payment Gateway Initiate Error", e.getMessage()+"");
        }
    }

    private void showPaymentFailure() {
        PaymentFailedDialog paymentFailedDialog = new PaymentFailedDialog(context, activity);
        paymentFailedDialog.setCancelable(true);
        paymentFailedDialog.show();
        paymentFailedDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    @Override
    public <T> void networkingRequest(@Nullable NetworkingInterface.MethodType methodType, boolean status, @Nullable T error, Object object) {

        if (methodType == MethodType.fetchBalance && status) {
            String walletBalance = object + " "+utils.getStringValue(R.string.coins);
            binding.coinValueText.setText(walletBalance);

            utils.visibleView(binding.mainLayout);
            if (preferenceUtils.getUserRole() == 2 || preferenceUtils.getUserRole() == 3){
                utils.hideView(binding.addCoinText);
                utils.hideView(binding.coinInputLayout);
                utils.hideView(binding.addCoinsBtn);
                utils.hideView(binding.noteText);
            }

            networking.fetchWalletTransaction();
        } else if (methodType == MethodType.fetchWalletTransaction && status){
            dismissLoading();
            List<WalletTransactionResponse.Data> walletTransactionList = (List<WalletTransactionResponse.Data>) object;

            if (walletTransactionList.size() == 0)
                binding.noTransactionText.setVisibility(View.VISIBLE);
            else
                binding.noTransactionText.setVisibility(View.GONE);

            binding.walletTansactionRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            WalletTransactionAdapter walletTransactionAdapter = new WalletTransactionAdapter(context, activity, walletTransactionList);
            binding.walletTansactionRecyclerView.setAdapter(walletTransactionAdapter);
            binding.walletTansactionRecyclerView.setItemViewCacheSize(walletTransactionList.size());
        } else if (methodType == MethodType.addCoinsToWallet && status) {
            utils.shortToast(utils.getStringValue(R.string.coins_added_success));
            binding.coinInput.setText("");

            networking.fetchBalance();
        } else if (methodType == MethodType.getRazorpayOrderId && status){
            dismissLoading();

            RazorpayOrderIdResponse.Data razorpayOrderIdResponse = (RazorpayOrderIdResponse.Data) object;
            initiatePayment(razorpayOrderIdResponse.getId());
        } else if (methodType == MethodType.addCoinsToWallet && !status){
            dismissLoading();
            utils.updateErrorEvent("Add Money Api Failure Event", "Amount- " +  binding.coinInput.getText().toString() +"  Payment Id- "+ paymentId +"  "+ (String) object);

            showPaymentFailure();
        }
        else if (methodType == MethodType.fetchBalance || methodType == MethodType.fetchWalletTransaction
                || methodType == MethodType.getRazorpayOrderId  && !status)
            dismissLoading();
    }
}