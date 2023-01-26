package com.drpashu.sdk.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.drpashu.app.activity.HomeActivity;
import com.drpashu.app.databinding.DialogVertificationStatusBinding;
import com.drpashu.app.utils.PreferenceUtils;

public class VerificationStatusDialog extends Dialog {
    private DialogVertificationStatusBinding binding;
    private HomeActivity activity;
    private Context context;
    private PreferenceUtils preferenceUtils;

    public VerificationStatusDialog(@NonNull Context context, HomeActivity activity) {
        super(context);
        this.context = context;
        this.activity = activity;
        preferenceUtils = new PreferenceUtils(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogVertificationStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        binding.proceedBtn.setOnClickListener(v -> {
            try {
                String text = "Hi, My name is " + preferenceUtils.getUsername() + ". I have submitted my documents and waiting for verification. Please update the status.";

                String toNumber = "919845556565"; // Replace with mobile phone number without +Sign or leading zeros, but with country code
                //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text));
                activity.startActivity(intent);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            dismiss();
        });
    }
}
