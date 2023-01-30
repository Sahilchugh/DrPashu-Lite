package com.drpashu.sdk;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.drpashu.sdk.databinding.FragmentCallDetailBinding;
import com.drpashu.sdk.network.ApiClient;
import com.drpashu.sdk.network.model.response.CallDetailResponse;
import com.drpashu.sdk.network.model.response.StartCallResponse;
import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;

public class CallDetailFragment extends BaseFragment {
    private FragmentCallDetailBinding binding;
    private String callId = "", baseUrl = ApiClient.BASE_URL_MEDIA;
    private byte[] prescription1Byte = null, prescription2Byte = null;
    private static final int RESULT_LOAD_PRESCRIPTION_1 = 1, RESULT_LOAD_PRESCRIPTION_2 = 2;
    private View view1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            callId = getArguments().getString("callId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCallDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view1 = view;
        onClickListeners();

        showLoading();
        networking.getCallDetail(callId);
    }

    private void onClickListeners() {
        binding.prescription1Img.setOnClickListener(v -> previewImg(binding.prescription1Img.getDrawable()));
        binding.prescription2Img.setOnClickListener(v -> previewImg(binding.prescription2Img.getDrawable()));
        binding.animalImg1.setOnClickListener(v -> previewImg(binding.animalImg1.getDrawable()));
        binding.animalImg2.setOnClickListener(v -> previewImg(binding.animalImg2.getDrawable()));
    }

    private void previewImg(Drawable drawable) {
        Dialog previewImgDialog = new Dialog(getContext());
        previewImgDialog.setCancelable(false);
//        previewImgDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        previewImgDialog.setContentView(R.layout.layout_image_preview);

        TouchImageView previewImg = previewImgDialog.findViewById(R.id.previewImg);
        ImageView closeImg = previewImgDialog.findViewById(R.id.closeImg);

        previewImg.setImageDrawable(drawable);

        closeImg.setOnClickListener(dialogView -> previewImgDialog.dismiss());

        previewImgDialog.show();
    }

    @Override
    public <T> void networkingRequest(@Nullable MethodType methodType, boolean status, @Nullable T error, Object o) {
        if (methodType == MethodType.getCallDetail && status) {
            dismissLoading();
            utils.visibleView(binding.mainLayout);

            binding.descriptionInput.setEnabled(false);
            binding.descriptionInput.setTextColor(getContext().getResources().getColor(R.color.black));

            binding.symptomListText.setText(utils.getStringValue(R.string.symptom_list) + " :");
            binding.animalImgText.setText(utils.getStringValue(R.string.animal_image) + " :");
            binding.prescriptionImgText.setText(utils.getStringValue(R.string.prescription) + " :");

            CallDetailResponse.Data callDetailResponse = (CallDetailResponse.Data) o;

            callId = callDetailResponse.getId() + "";
            binding.userText.setText(callDetailResponse.getFirstName() + " " + callDetailResponse.getLastName());
            binding.dateText.setText(callDetailResponse.getDate() + " " + callDetailResponse.getTime());

            if (callDetailResponse.getProfilePicture() != null) {
                if (callDetailResponse.getProfilePicture().length() != 0)
                    Picasso.get().load(ApiClient.BASE_URL_MEDIA + callDetailResponse.getProfilePicture()).into(binding.userImg);
            }

            if (callDetailResponse.getCallInitiated().equalsIgnoreCase("Farmer") && callDetailResponse.getCallStatusRes().equalsIgnoreCase("Missed"))
                binding.infoIcon.setImageResource(R.drawable.call_out_miss);
            else if (callDetailResponse.getCallInitiated().equalsIgnoreCase("Farmer") && callDetailResponse.getCallStatusRes().equalsIgnoreCase("Completed"))
                binding.infoIcon.setImageResource(R.drawable.call_out_done);
            else if (callDetailResponse.getCallInitiated().equalsIgnoreCase("Vet") && callDetailResponse.getCallStatusRes().equalsIgnoreCase("Missed"))
                binding.infoIcon.setImageResource(R.drawable.call_in_miss);
            else if (callDetailResponse.getCallInitiated().equalsIgnoreCase("Vet") && callDetailResponse.getCallStatusRes().equalsIgnoreCase("Completed"))
                binding.infoIcon.setImageResource(R.drawable.call_in_done);
            else if (callDetailResponse.getCallInitiated().equalsIgnoreCase("Admin") && callDetailResponse.getCallStatusRes().equalsIgnoreCase("Missed"))
                binding.infoIcon.setImageResource(R.drawable.call_in_miss);
            else if (callDetailResponse.getCallInitiated().equalsIgnoreCase("Admin") && callDetailResponse.getCallStatusRes().equalsIgnoreCase("Completed"))
                binding.infoIcon.setImageResource(R.drawable.call_in_done);

            if (callDetailResponse.getCallStatusRes().equalsIgnoreCase("Completed")) {
                if (callDetailResponse.getCallDuration() != null)
                    binding.dateText.setText(callDetailResponse.getDate() + " " + callDetailResponse.getTime() + " (" + callDetailResponse.getCallDuration() + ")");
            } else {
                utils.hideView(binding.updateText);
                utils.hideView(binding.prescriptionImgText);
                utils.hideView(binding.prescription1Img);
                utils.hideView(binding.prescription2Img);
                utils.hideView(binding.descriptionLayout);
            }

            if (callDetailResponse.getPrescriptionImageFirst().length() != 0)
                Picasso.get().load(baseUrl + "/media/" + callDetailResponse.getPrescriptionImageFirst()).into(binding.prescription1Img);
            else {
                utils.hideView(binding.prescriptionImgText);
                utils.hideView(binding.prescription1Img);
                utils.hideView(binding.prescription2Img);
            }

            if (callDetailResponse.getPrescriptionImageSecond().length() != 0)
                Picasso.get().load(baseUrl + "/media/" + callDetailResponse.getPrescriptionImageSecond()).into(binding.prescription2Img);
            else {
                utils.hideView(binding.prescription2Img);
            }

            if (callDetailResponse.getDetails() != null)
                binding.descriptionInput.setText(callDetailResponse.getDetails() + "");
            else {
                utils.hideView(binding.descriptionLayout);
            }

            if (callDetailResponse.getHealthVal()) {
                if (callDetailResponse.getLotExists()) {
                    utils.visibleView(binding.farmDetailLayout);
                    utils.hideView(binding.animalTypeTitleText);
                    utils.hideView(binding.animalTypeText);

                    binding.animalText.setText(callDetailResponse.getAnimal() + "");
                    binding.typeText.setText(callDetailResponse.getBreed() + "");
                    binding.quantityText.setText(callDetailResponse.getQuantity() + "");
                    binding.dobText.setText(callDetailResponse.getDOB() + "");
                } else {
                    utils.visibleView(binding.animalTypeTitleText);
                    utils.visibleView(binding.animalTypeText);
                    utils.hideView(binding.farmDetailLayout);

                    binding.animalTypeText.setText(callDetailResponse.getAnimal() + "");
                }

                binding.symptomListView.setAdapter(new ArrayAdapter<>(getContext(), R.layout.symptom_list, R.id.symptom, callDetailResponse.getSymptomsList()));
                binding.symptomListView.setDivider(null);

                if (callDetailResponse.getAnalysisImage() != null)
                    Picasso.get().load(baseUrl + callDetailResponse.getAnalysisImage()).into(binding.animalImg1);
                else {
                    utils.hideView(binding.animalImgText);
                    utils.hideView(binding.animalImg1);
                    utils.hideView(binding.animalImg2);
                }

                if (callDetailResponse.getPostmortemImage() != null) {
                    utils.visibleView(binding.animalImg2);
                    Picasso.get().load(baseUrl + callDetailResponse.getPostmortemImage()).into(binding.animalImg2);
                } else
                    utils.hideView(binding.animalImg2);

            } else {
                utils.hideView(binding.symptomListText);
                utils.hideView(binding.symptomListView);
                utils.hideView(binding.animalImgText);
                utils.hideView(binding.animalImg1);
                utils.hideView(binding.animalImg2);

                utils.visibleView(binding.animalTypeTitleText);
                utils.visibleView(binding.animalTypeText);
                utils.hideView(binding.farmDetailLayout);
                binding.animalTypeText.setText(callDetailResponse.getAnimal() + "");
            }
        } else if (methodType == MethodType.getCallDetail && !status)
            dismissLoading();
    }
}