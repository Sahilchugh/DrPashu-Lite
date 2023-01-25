package com.drpashu.sdk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.drpashu.sdk.databinding.FragmentPrescriptionBinding;
import com.drpashu.sdk.BaseFragment;

public class PrescriptionFragment extends BaseFragment {
    private FragmentPrescriptionBinding binding;
    private static final int  RESULT_LOAD_PRESCRIPTION_1 = 1, RESULT_LOAD_PRESCRIPTION_2 = 2;
    private byte[] prescription1Byte = null, prescription2Byte = null;
    private String callId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            callId = getArguments().getString("callId");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPrescriptionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
/*

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.updateTooblar(ContextCompat.getDrawable(activity, R.drawable.ic_prescription), true);

        binding.prescription1Img.setOnClickListener(v -> dispatchTakePictureIntent(RESULT_LOAD_PRESCRIPTION_1));
        binding.prescription2Img.setOnClickListener(v -> dispatchTakePictureIntent(RESULT_LOAD_PRESCRIPTION_2));

        binding.uploadBtn.setOnClickListener(v -> {
            if (prescription1Byte == null && prescription2Byte == null && binding.descriptionInput.getText().toString().length() == 0)
                utils.shortToast(utils.getStringValue(R.string.upload_prescription_condition));
            else{
                showLoading();
                networking.uploadPrescription(callId,  binding.descriptionInput.getText().toString(), prescription1Byte, prescription2Byte);
            }
        });

        binding.additionalBtn.setOnClickListener(v -> {
            if (prescription1Byte != null) {
                utils.visibleView(binding.prescription2CardView);
                binding.scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                }, 300);
                utils.hideView(binding.additionalBtn);
            }
            else
                activity.onBackPressed();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_PRESCRIPTION_1) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap prescriptionBitmap1 = utils.getScaledDownBitmap(BitmapFactory.decodeFile(cameraImagePath), 720);
                binding.prescription1Img.setImageBitmap(prescriptionBitmap1);
                prescription1Byte = utils.getFileDataFromDrawable(prescriptionBitmap1, 100);
                binding.additionalBtn.setText(utils.getStringValue(R.string.add_more_photo));
            }
        }
        else if (requestCode == RESULT_LOAD_PRESCRIPTION_2) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap prescriptionBitmap2 = utils.getScaledDownBitmap(BitmapFactory.decodeFile(cameraImagePath), 720);
                binding.prescription2Img.setImageBitmap(prescriptionBitmap2);
                prescription2Byte = utils.getFileDataFromDrawable(prescriptionBitmap2, 100);
            }
        }
    }

    @Override
    public <T> void networkingRequest(@Nullable MethodType methodType, boolean status, @Nullable T error, Object o) {
        if (methodType == MethodType.uploadPrescription && status) {
            dismissLoading();
            utils.shortToast(utils.getStringValue(R.string.prescription_upload_success));
            activity.onBackPressed();
        }
        else if (methodType == MethodType.uploadPrescription && !status)
            dismissLoading();
    }
*/
}