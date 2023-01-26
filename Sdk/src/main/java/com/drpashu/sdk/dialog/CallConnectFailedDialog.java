package com.drpashu.sdk.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.drpashu.app.activity.HomeActivity;
import com.drpashu.app.databinding.DialogCallConnectFailedBinding;

public class CallConnectFailedDialog extends Dialog {
    private DialogCallConnectFailedBinding binding;
    private HomeActivity activity;
    private Context context;
    private String message;

    public CallConnectFailedDialog(@NonNull Context context, HomeActivity activity, String message) {
        super(context);
        this.context = context;
        this.activity = activity;
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogCallConnectFailedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.messageText.setText(message);
        binding.cancelImg.setOnClickListener(v -> dismiss());
    }
}
