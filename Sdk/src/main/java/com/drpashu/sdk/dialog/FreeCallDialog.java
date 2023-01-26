package com.drpashu.sdk.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.drpashu.app.R;
import com.drpashu.app.activity.HomeActivity;
import com.drpashu.app.databinding.DialogFreeCallBinding;
import com.drpashu.app.utils.Utils;

public class FreeCallDialog extends Dialog {
    private FreeCallActionInterface freeCallActionInterface;
    private DialogFreeCallBinding binding;
    private int callMrp, callPrice, userCoinBalance, method, paymentGatewayAmount = 0;
    private HomeActivity activity;
    private Utils utils;

    public FreeCallDialog(@NonNull Context context, HomeActivity activity, int callMrp, int callPrice,
                          int userCoinBalance, FreeCallActionInterface freeCallActionInterface) {
        super(context);
        this.activity = activity;
        this.callMrp = callMrp;
        this.callPrice = callPrice;
        this.userCoinBalance = userCoinBalance;
        this.freeCallActionInterface = freeCallActionInterface;
        utils = new Utils(context, activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogFreeCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (callPrice == 0){
            method = 1;

            binding.imageView.setImageResource(R.drawable.free_call_icon);
            binding.congratsText.setText(activity.getResources().getString(R.string.congratulations));
            binding.messageText.setText(utils.getStringValue(R.string.get_free_call_message)+" "+callMrp+" Pashu Coins");
        } else if (callPrice <= userCoinBalance){
            method = 1;

            binding.imageView.setImageResource(R.drawable.free_call_icon);
            binding.congratsText.setText(activity.getResources().getString(R.string.congratulations));
            binding.messageText.setText(utils.getStringValue(R.string.pashu_coins_in_wallet) + ": " + userCoinBalance + "\n" + utils.getStringValue(R.string.coins_used_to_call) + ": " + callPrice);
        } else if (callPrice > userCoinBalance && userCoinBalance == 0){
            method = 2;
            paymentGatewayAmount = callPrice;

            binding.imageView.setImageResource(R.drawable.wallet_image);
            binding.congratsText.setText(activity.getResources().getString(R.string.one_step_away));
            binding.messageText.setText(utils.getStringValue(R.string.pashu_coins_in_wallet) + ": " + userCoinBalance + "\n" + utils.getStringValue(R.string.add_coins_to_call) + ": " + callPrice);
        } else if (callPrice > userCoinBalance){
            method = 2;
            paymentGatewayAmount = callPrice-userCoinBalance;

            binding.imageView.setImageResource(R.drawable.wallet_image);
            binding.congratsText.setText(activity.getResources().getString(R.string.one_step_away));
            binding.messageText.setText(utils.getStringValue(R.string.pashu_coins_in_wallet) + ": " + userCoinBalance + "\n" + utils.getStringValue(R.string.add_coins_to_call) + ": " + paymentGatewayAmount);
        }

        binding.proceedBtn.setOnClickListener(v -> {
            activity.updateFirebaseEvents("button_click", "call_proceed_button");
            dismiss();
            freeCallActionInterface.freeCallDialogAction(method, paymentGatewayAmount);
        });

        binding.cancelBtn.setOnClickListener(v -> {
            activity.updateFirebaseEvents("button_click", "call_cancel_dialog");
            dismiss();
        });
    }
}
