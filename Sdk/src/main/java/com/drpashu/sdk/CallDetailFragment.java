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
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;

import com.drpashu.sdk.databinding.FragmentCallDetailBinding;
import com.drpashu.sdk.BaseFragment;
import com.drpashu.sdk.network.ApiClient;
import com.drpashu.sdk.network.model.response.CallDetailResponse;
import com.drpashu.sdk.network.model.response.StartCallResponse;
import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;

public class CallDetailFragment extends BaseFragment {
    private FragmentCallDetailBinding binding;
    private String callId = "", baseUrl = ApiClient.BASE_URL_MEDIA;
    private byte[] prescription1Byte = null, prescription2Byte = null;
    private Boolean adminUserSearch = false;
    private int adminUserRole;
    private static final int  RESULT_LOAD_PRESCRIPTION_1 = 1, RESULT_LOAD_PRESCRIPTION_2 = 2;
    private View view1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            callId = getArguments().getString("callId");
            adminUserRole = getArguments().getInt("adminUserRole");
            adminUserSearch = getArguments().getBoolean("adminUserSearch");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCallDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
/*

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.updateTooblar(ContextCompat.getDrawable(activity, R.drawable.ic_history), true);

        view1 = view;
        onClickListeners();

        showLoading();
        networking.getCallDetail(callId, adminUserSearch);
    }

    private void onClickListeners() {
        binding.updateText.setOnClickListener(v -> {
            binding.scrollView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            }, 200);
            binding.descriptionInput.setEnabled(true);
            utils.hideView(binding.updateText);
            utils.visibleView(binding.updateBtn);
            utils.visibleView(binding.prescriptionNoteText);

            binding.prescription1Img.setOnClickListener(view1 -> dispatchTakePictureIntent(RESULT_LOAD_PRESCRIPTION_1));
            binding.prescription2Img.setOnClickListener(view1 -> dispatchTakePictureIntent(RESULT_LOAD_PRESCRIPTION_2));
        });

        binding.prescription1Img.setOnClickListener(v -> previewImg(binding.prescription1Img.getDrawable()));
        binding.prescription2Img.setOnClickListener(v -> previewImg(binding.prescription2Img.getDrawable()));
        binding.animalImg1.setOnClickListener(v -> previewImg(binding.animalImg1.getDrawable()));
        binding.animalImg2.setOnClickListener(v -> previewImg(binding.animalImg2.getDrawable()));

        binding.updateBtn.setOnClickListener(v -> {
            if (prescription1Byte == null && prescription2Byte == null && binding.descriptionInput.getText().toString().length() == 0)
                utils.shortToast(utils.getStringValue(R.string.upload_prescription_condition));
            else{
                showLoading();
                networking.uploadPrescription(callId,  binding.descriptionInput.getText().toString(), prescription1Byte, prescription2Byte);
            }
        });

        binding.callBackBtn.setOnClickListener(v -> {
            showLoading();
            networking.callBackUser(callId);
        });
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_PRESCRIPTION_1) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap prescriptionBitmap1 = utils.getScaledDownBitmap(BitmapFactory.decodeFile(cameraImagePath), 720);
                binding.prescription1Img.setImageBitmap(prescriptionBitmap1);
                prescription1Byte = utils.getFileDataFromDrawable(prescriptionBitmap1, 100);
            }
        }
        else if (requestCode == RESULT_LOAD_PRESCRIPTION_2) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap prescriptionBitmap2 = utils.getScaledDownBitmap(BitmapFactory.decodeFile(cameraImagePath), 720);
                binding.prescription2Img.setImageBitmap(prescriptionBitmap2);
                prescription2Byte = utils.getFileDataFromDrawable(prescriptionBitmap2,100);
            }
        }
    }

    private int getUserRole() {
        if (preferenceUtils.getUserRole() == 3) {
            if (adminUserSearch)
                return adminUserRole;
            else
                return preferenceUtils.getUserRole();
        } else
            return preferenceUtils.getUserRole();
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

            int userRole = getUserRole();

            if (userRole == 0 || userRole == 1) {
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
            } else {
                if(userRole == 2) {
                    if (callDetailResponse.getCallInitiated().equalsIgnoreCase("Farmer") && callDetailResponse.getCallStatusRes().equalsIgnoreCase("Missed"))
                        binding.infoIcon.setImageResource(R.drawable.call_in_miss);
                    else if (callDetailResponse.getCallInitiated().equalsIgnoreCase("Farmer") && callDetailResponse.getCallStatusRes().equalsIgnoreCase("Completed"))
                        binding.infoIcon.setImageResource(R.drawable.call_in_done);
                    else if (callDetailResponse.getCallInitiated().equalsIgnoreCase("Vet") && callDetailResponse.getCallStatusRes().equalsIgnoreCase("Missed"))
                        binding.infoIcon.setImageResource(R.drawable.call_out_miss);
                    else if (callDetailResponse.getCallInitiated().equalsIgnoreCase("Vet") && callDetailResponse.getCallStatusRes().equalsIgnoreCase("Completed"))
                        binding.infoIcon.setImageResource(R.drawable.call_out_done);
                    if (callDetailResponse.getCallInitiated().equalsIgnoreCase("Admin") && callDetailResponse.getCallStatusRes().equalsIgnoreCase("Missed"))
                        binding.infoIcon.setImageResource(R.drawable.call_in_miss);
                    else if (callDetailResponse.getCallInitiated().equalsIgnoreCase("Admin") && callDetailResponse.getCallStatusRes().equalsIgnoreCase("Completed"))
                        binding.infoIcon.setImageResource(R.drawable.call_in_done);
                } else if (userRole == 3){
                    if (callDetailResponse.getCallInitiated().equalsIgnoreCase("Farmer") && callDetailResponse.getCallStatusRes().equalsIgnoreCase("Missed"))
                        binding.infoIcon.setImageResource(R.drawable.call_in_miss);
                    else if (callDetailResponse.getCallInitiated().equalsIgnoreCase("Farmer") && callDetailResponse.getCallStatusRes().equalsIgnoreCase("Completed"))
                        binding.infoIcon.setImageResource(R.drawable.call_in_done);
                    else if (callDetailResponse.getCallInitiated().equalsIgnoreCase("Vet") && callDetailResponse.getCallStatusRes().equalsIgnoreCase("Missed"))
                        binding.infoIcon.setImageResource(R.drawable.call_in_miss);
                    else if (callDetailResponse.getCallInitiated().equalsIgnoreCase("Vet") && callDetailResponse.getCallStatusRes().equalsIgnoreCase("Completed"))
                        binding.infoIcon.setImageResource(R.drawable.call_in_done);
                    if (callDetailResponse.getCallInitiated().equalsIgnoreCase("Admin") && callDetailResponse.getCallStatusRes().equalsIgnoreCase("Missed"))
                        binding.infoIcon.setImageResource(R.drawable.call_out_miss);
                    else if (callDetailResponse.getCallInitiated().equalsIgnoreCase("Admin") && callDetailResponse.getCallStatusRes().equalsIgnoreCase("Completed"))
                        binding.infoIcon.setImageResource(R.drawable.call_out_done);
                }

                if (adminUserSearch) {
                    utils.hideView(binding.updateText);
                    utils.hideView(binding.callBackBtn);
                } else {
                    utils.visibleView(binding.updateText);
                    utils.visibleView(binding.callBackBtn);
                }
            }

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
                if (userRole != 2 && userRole != 3) {
                    utils.hideView(binding.prescriptionImgText);
                    utils.hideView(binding.prescription1Img);
                    utils.hideView(binding.prescription2Img);
                }
            }

            if (callDetailResponse.getPrescriptionImageSecond().length() != 0)
                Picasso.get().load(baseUrl + "/media/" + callDetailResponse.getPrescriptionImageSecond()).into(binding.prescription2Img);
            else {
                if (userRole != 2 && userRole != 3)
                    utils.hideView(binding.prescription2Img);
            }

            if (callDetailResponse.getDetails() != null)
                binding.descriptionInput.setText(callDetailResponse.getDetails() + "");
            else {
                if (userRole != 2 && userRole != 3)
                    utils.hideView(binding.descriptionLayout);
            }

*/
/*            if (userRole ==2 || userRole == 3){
                if (adminUserSearch){
                    utils.hideView(binding.prescriptionImgText);
                    utils.hideView(binding.prescription1Img);
                    utils.hideView(binding.prescription2Img);
                    utils.hideView(binding.descriptionLayout);
                }
            }*//*


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
        }
        else if (methodType == MethodType.uploadPrescription && status) {
            dismissLoading();
            utils.shortToast(utils.getStringValue(R.string.prescription_upload_success));
            activity.onBackPressed();
        }
        else if (methodType == MethodType.callBackUser && status) {
            dismissLoading();
            StartCallResponse.Data startCallDataResponse = (StartCallResponse.Data) o;
            Bundle bundle = new Bundle();
            bundle.putBoolean("callIncoming", false);
            bundle.putBoolean("callRedial", false);
            bundle.putString("callInitiated", "Vet");
            bundle.putString("callId", startCallDataResponse.getCallId()+"");
            bundle.putString("channelId", startCallDataResponse.getChannel_id());
            bundle.putString("firstName", startCallDataResponse.getFirst_name());
            bundle.putString("lastName", startCallDataResponse.getLast_name());
            bundle.putInt("notificationId", Integer.parseInt(startCallDataResponse.getNotificationId()));

            Navigation.findNavController(view1).navigate(R.id.action_callDetailFragment_to_incomingCallFragment, bundle);
        }
        else if (methodType == MethodType.getCallDetail || methodType == MethodType.uploadPrescription || methodType == MethodType.callBackUser && !status)
            dismissLoading();
    }
*/
}