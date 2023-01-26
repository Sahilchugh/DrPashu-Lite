package com.drpashu.sdk.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.drpashu.app.R;
import com.drpashu.app.activity.HomeActivity;
import com.drpashu.app.databinding.DialogPaymentFailedBinding;

public class PaymentFailedDialog extends Dialog {
    private DialogPaymentFailedBinding binding;
    private HomeActivity activity;
    private Context context;

    public PaymentFailedDialog(@NonNull Context context, HomeActivity activity) {
        super(context);
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogPaymentFailedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String message = context.getString(R.string.payment_message_1) + "\n\n" + context.getString(R.string.payment_message_2);
        binding.messageText.setText(message);
        
        binding.proceedBtn.setOnClickListener(v -> {
            try {
                String text = "Hi DrPashu Team, I am facing a payment issue. Please call me.";// Replace with your message.

                String toNumber = "919902059730"; // Replace with mobile phone number without +Sign or leading zeros, but with country code
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
