package com.drpashu.sdk.network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.drpashu.sdk.R;
import com.drpashu.sdk.network.model.request.DrPashuRequest;
import com.drpashu.sdk.network.model.response.AnimalListResponse;
import com.drpashu.sdk.network.model.response.BaseResponse;
import com.drpashu.sdk.network.model.response.CallDetailResponse;
import com.drpashu.sdk.network.model.response.CallHistoryListResponse;
import com.drpashu.sdk.network.model.response.DeviceTokenUpdateResponse;
import com.drpashu.sdk.network.model.response.DrPashuResponse;
import com.drpashu.sdk.network.model.response.RazorpayOrderIdResponse;
import com.drpashu.sdk.network.model.response.StartCallResponse;
import com.drpashu.sdk.network.model.response.VetListResponse;
import com.drpashu.sdk.network.model.response.WalletResponse;
import com.drpashu.sdk.network.model.response.WalletTransactionResponse;
import com.drpashu.sdk.utils.PreferenceUtils;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Networking {
    private final PreferenceUtils preferenceUtils;
    private final Context context;
    private final Activity activity;
    Retrofit retrofit = ApiClient.getRetrofitInstance();
    Retrofit sdkRetrofit = ApiClient.getSdkRetrofitInstance();
    private static ProgressDialog progressdialog;
    NetworkingInterface networkingInterface;
    public static int lot_id = 0;
    public static String lot_name_text = "";
    ApiInterface apiInterface = retrofit.create(ApiInterface.class);
    ApiInterface apiSdkInterface = sdkRetrofit.create(ApiInterface.class);

    public Networking(Context context, Activity activity, NetworkingInterface networkingInterface) {
        this.context = context;
        this.activity = activity;
        this.networkingInterface = networkingInterface;
        preferenceUtils = new PreferenceUtils(context);
    }

    public void getAnimals() {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(context.getString(R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.show();

        Call<List<AnimalListResponse>> animalListResponse = apiInterface.getAnimalList(preferenceUtils.getUserId());
        animalListResponse.enqueue(new Callback<List<AnimalListResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<AnimalListResponse>> call, @NonNull Response<List<AnimalListResponse>> response) {

                if (response.isSuccessful()) {
                    List<AnimalListResponse> animalResponseList = response.body();
                    for (int i = 0; i<animalResponseList.size();i++){
                        for (int j =0; j< animalResponseList.get(i).getData().size(); j++){
                            if (animalResponseList.get(i).getData().get(j).getName().equalsIgnoreCase("Chicken"))
                                animalResponseList.get(i).getData().get(j).setLocalImage(R.drawable.animal_chicken);
                            else if (animalResponseList.get(i).getData().get(j).getName().equalsIgnoreCase("Cow"))
                                animalResponseList.get(i).getData().get(j).setLocalImage(R.drawable.animal_cow);
                            else if (animalResponseList.get(i).getData().get(j).getName().equalsIgnoreCase("Buffalo"))
                                animalResponseList.get(i).getData().get(j).setLocalImage(R.drawable.animal_buffalo);
                            else if (animalResponseList.get(i).getData().get(j).getName().equalsIgnoreCase("Pig"))
                                animalResponseList.get(i).getData().get(j).setLocalImage(R.drawable.animal_pig);
                            else if (animalResponseList.get(i).getData().get(j).getName().equalsIgnoreCase("Goat"))
                                animalResponseList.get(i).getData().get(j).setLocalImage(R.drawable.animal_goat);
                            else if (animalResponseList.get(i).getData().get(j).getName().equalsIgnoreCase("Sheep"))
                                animalResponseList.get(i).getData().get(j).setLocalImage(R.drawable.animal_sheep);
                            else if (animalResponseList.get(i).getData().get(j).getName().equalsIgnoreCase("Dog"))
                                animalResponseList.get(i).getData().get(j).setLocalImage(R.drawable.animal_dog);
                            else if (animalResponseList.get(i).getData().get(j).getName().equalsIgnoreCase("Cat"))
                                animalResponseList.get(i).getData().get(j).setLocalImage(R.drawable.animal_cat);
                        }
                    }
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.getAnimalList, true, null, animalResponseList);
                } else {
                    Toast.makeText(context, context.getString(R.string.error_animal_list), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.getAnimalList, false, null, null);
                }
                progressdialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<List<AnimalListResponse>> call, @NonNull Throwable t) {
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.getAnimalList, false, null, null);
                Toast.makeText(context, context.getString(R.string.error_animal_list), Toast.LENGTH_SHORT).show();
                progressdialog.dismiss();
            }
        });
    }

    public void getVetList(String breedName) {
        Call<VetListResponse> vetListResponseCall = apiInterface.getVetList(preferenceUtils.getUserId(), null, breedName);
        vetListResponseCall.enqueue(new Callback<VetListResponse>() {
            @Override
            public void onResponse(@NonNull Call<VetListResponse> call, @NonNull Response<VetListResponse> response) {
                if (response.isSuccessful()) {
                    VetListResponse vetListResponse = response.body();

                    if (vetListResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.getVetList, true, null, vetListResponse.getData());
                    else {
                        Toast.makeText(context, vetListResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.getVetList, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_vet_list), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.getVetList, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<VetListResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_vet_list), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.getVetList, false, null, null);
            }
        });
    }

    public void startCall(String farmId, String animalType, String vetCategory, Boolean healthCheck, String companyName,
                          Boolean reconnecting, String groupId, Boolean freeCall, String callAmount, String paymentId) {
        String healthValue = "", reconnectingStatus = "", freeCallValue = "";
        if (healthCheck)
            healthValue = "True";
        else
            healthValue = "False";

        if (reconnecting)
            reconnectingStatus = "True";
        else
            reconnectingStatus = "False";

        if (freeCall)
            freeCallValue = "True";
        else
            freeCallValue = "False";

        String companyNameText = null;

        if (companyName.length() != 0)
            companyNameText = companyName;

        Call<StartCallResponse> startCallResponseCall;
        if (farmId.length() == 0) {
            startCallResponseCall = apiInterface.startCall(preferenceUtils.getUserId(), null, animalType, vetCategory,
                    healthValue, companyNameText, reconnectingStatus, groupId, freeCallValue, callAmount, paymentId);
        }
        else {
            startCallResponseCall = apiInterface.startCall(preferenceUtils.getUserId(), farmId, null, vetCategory,
                    healthValue, companyNameText, reconnectingStatus, groupId, freeCallValue, callAmount, paymentId);
        }

        startCallResponseCall.enqueue(new Callback<StartCallResponse>() {
            @Override
            public void onResponse(@NonNull Call<StartCallResponse> call, @NonNull Response<StartCallResponse> response) {
                if (response.isSuccessful()) {
                    StartCallResponse startCallResponse = response.body();
                    if (startCallResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.startCall, true, null, startCallResponse.getData());
                    else {
//                        Toast.makeText(context, startCallResponse.getMessage() + "", Toast.LENGTH_LONG).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.startCall, false, true, startCallResponse.getMessage()+"");
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_start_call), Toast.LENGTH_SHORT).show();
                    try {
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.startCall, false, null, response.errorBody().string()+"");
                    } catch (IOException e) {
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.startCall, false, null, "");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<StartCallResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_start_call), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.startCall, false, null, t.getMessage()+"");
            }
        });
    }

    public void callBackUser(String callId) {
        Call<StartCallResponse> startCallResponseCall = apiInterface.callBackUser(preferenceUtils.getUserId(), callId);
        startCallResponseCall.enqueue(new Callback<StartCallResponse>() {
            @Override
            public void onResponse(@NonNull Call<StartCallResponse> call, @NonNull Response<StartCallResponse> response) {
                if (response.isSuccessful()) {
                    StartCallResponse startCallResponse = response.body();
                    if (startCallResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.callBackUser, true, null, startCallResponse.getData());
                    else {
                        Toast.makeText(context, startCallResponse.getMessage() + "", Toast.LENGTH_LONG).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.callBackUser, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_call_back_user), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.callBackUser, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<StartCallResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_call_back_user), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.callBackUser, false, null, null);
            }
        });
    }

    public void uploadPrescription(String callId, String prescriptionDescription, byte[] prescription1Img, byte[] prescription2Img) {
        long imagename = System.currentTimeMillis();

        MultipartBody.Part requestPrescription1Img = null;
        MultipartBody.Part requestPrescription2Img = null;
        RequestBody requestPrescriptionDescription = null;

        RequestBody requestCallId = RequestBody.create(callId, MediaType.parse("multipart/form-data"));

        if (prescriptionDescription.length() != 0)
            requestPrescriptionDescription = RequestBody.create(prescriptionDescription, MediaType.parse("multipart/form-data"));

        if (prescription1Img != null) {
            RequestBody requestPrescription1 = RequestBody.create(prescription1Img, MediaType.parse("multipart/form-data"));
            requestPrescription1Img = MultipartBody.Part.createFormData("file_first", imagename + ".png", requestPrescription1);
        }

        if (prescription2Img != null){
            RequestBody requestPrescription2 = RequestBody.create(prescription2Img, MediaType.parse("multipart/form-data"));
            requestPrescription2Img = MultipartBody.Part.createFormData("file_second", imagename + ".png", requestPrescription2);
        }

        Call<BaseResponse> baseResponseCall = apiInterface.uploadPrescription(preferenceUtils.getUserId(), requestCallId, requestPrescriptionDescription, requestPrescription1Img, requestPrescription2Img);
        baseResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.uploadPrescription, true, null, null);
                    else {
                        Toast.makeText(context, baseResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.uploadPrescription, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_upload_prescription), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.uploadPrescription, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_upload_prescription), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.uploadPrescription, false, t, null);
            }
        });
    }

    public void getCallHistoryList() {
        Call<CallHistoryListResponse> callHistoryListResponseCall = apiInterface.getCallHistoryList(preferenceUtils.getUserId());
        callHistoryListResponseCall.enqueue(new Callback<CallHistoryListResponse>() {
            @Override
            public void onResponse(@NonNull Call<CallHistoryListResponse> call, @NonNull Response<CallHistoryListResponse> response) {
                if (response.isSuccessful()) {
                    CallHistoryListResponse callHistoryListResponse = response.body();
                    if (callHistoryListResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.getCallHistoryList, true, null, callHistoryListResponse.getData());
                    else {
                        Toast.makeText(context, callHistoryListResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.getCallHistoryList, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_call_history_list), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.getCallHistoryList, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CallHistoryListResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_call_history_list), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.getCallHistoryList, false, null, null);
            }
        });
    }

    public void getCallDetail(String callId) {
        Call<CallDetailResponse> callDetailResponseCall = apiInterface.getCallDetail(preferenceUtils.getUserId(), callId);
        callDetailResponseCall.enqueue(new Callback<CallDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<CallDetailResponse> call, @NonNull Response<CallDetailResponse> response) {
                if (response.isSuccessful()) {
                    CallDetailResponse callDetailResponse = response.body();
                    if (callDetailResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.getCallDetail, true, null, callDetailResponse.getData());
                    else {
                        Toast.makeText(context, callDetailResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.getCallDetail, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_call_detail), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.getCallDetail, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CallDetailResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_call_detail), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.getCallDetail, false, null, null);
            }
        });
    }

    public void updateCallStatus(String callId, String callStatus) {
        Call<BaseResponse> baseResponseCall = apiInterface.updateCallStatus(preferenceUtils.getUserId(), callId, callStatus);
        baseResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateCallStatus, true, null, null);
                    else {
                        Toast.makeText(context, baseResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateCallStatus, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_update_call_status), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateCallStatus, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_update_call_status), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateCallStatus, false, null, null);
            }
        });
    }

    public void rejectCall(String callId, String notificationId, String callInitiated) {
        Call<BaseResponse> baseResponseCall = apiInterface.rejectCall(preferenceUtils.getUserId(), callId, notificationId, "farmer", callInitiated);
        baseResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.rejectCall, true, null, null);
                    else {
                        Toast.makeText(context, baseResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.rejectCall, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_reject_call), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.rejectCall, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_reject_call), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.rejectCall, false, null, null);
            }
        });
    }

    public void getRazorpayOrderId(String amount) {
        Call<RazorpayOrderIdResponse> razorpayOrderIdResponseCall = apiInterface.getRazorpayOrderId(preferenceUtils.getUserId(), amount);
        razorpayOrderIdResponseCall.enqueue(new Callback<RazorpayOrderIdResponse>() {
            @Override
            public void onResponse(@NonNull Call<RazorpayOrderIdResponse> call, @NonNull Response<RazorpayOrderIdResponse> response) {
                if (response.isSuccessful()) {
                    RazorpayOrderIdResponse razorpayOrderIdResponse = response.body();
                    if (razorpayOrderIdResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.getRazorpayOrderId, true, null, razorpayOrderIdResponse.getData());
                    else {
                        Toast.makeText(context, razorpayOrderIdResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.getRazorpayOrderId, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_get_razorpay_order_id), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.getRazorpayOrderId, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RazorpayOrderIdResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_get_razorpay_order_id), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.getRazorpayOrderId, false, null, null);
            }
        });
    }

    public void updateDeviceId() {
        if (preferenceUtils.getFcmToken().length() == 0)
            return;

        Call<DeviceTokenUpdateResponse> deviceTokenUpdateResponseCall = apiInterface.updateDeviceToken(preferenceUtils.getUserId(), preferenceUtils.getFcmToken(), "BuildConfig.VERSION_NAME");

        deviceTokenUpdateResponseCall.enqueue(new Callback<DeviceTokenUpdateResponse>() {
            @Override
            public void onResponse(@NonNull Call<DeviceTokenUpdateResponse> call, @NonNull Response<DeviceTokenUpdateResponse> response) {
                if (response.isSuccessful()) {
                    DeviceTokenUpdateResponse deviceTokenUpdateResponse = response.body();
                    if (deviceTokenUpdateResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateDeviceToken, true, null, deviceTokenUpdateResponse.getUserRole());
                    else {
                        Toast.makeText(context, deviceTokenUpdateResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateDeviceToken, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_update_device_token), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateDeviceToken, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DeviceTokenUpdateResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_update_device_token), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateDeviceToken, false, null, null);
            }
        });
    }

    public void postErrorDetails(String title, String errorMessage, String errorDetails) {
        Call<JsonObject> jsonObjectCall = apiInterface.postErrorDetails(preferenceUtils.getUserId(), title, errorMessage, errorDetails);
        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
//                networkingInterface.networkingRequest(NetworkingInterface.MethodType.postErrorDetails, response.isSuccessful(), null, null);
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
//                networkingInterface.networkingRequest(NetworkingInterface.MethodType.postErrorDetails, false, null, null);
            }
        });
    }

    public void fetchBalance() {
        Call<WalletResponse> walletResponseCall = apiInterface.fetchBalance(preferenceUtils.getUserId());
        walletResponseCall.enqueue(new Callback<WalletResponse>() {
            @Override
            public void onResponse(@NonNull Call<WalletResponse> call, @NonNull Response<WalletResponse> response) {
                if (response.isSuccessful()) {
                    WalletResponse walletResponse = response.body();
                    if (walletResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchBalance, true, null, walletResponse.getData().getWalletBalance());
                    else {
                        Toast.makeText(context, walletResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchBalance, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_fetch_wallet_balance), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchBalance, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WalletResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_fetch_wallet_balance), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchBalance, false, null, null);
            }
        });
    }

    public void fetchWalletTransaction() {
        Call<WalletTransactionResponse> walletTransactionResponseCall = apiInterface.fetchWalletTransaction(preferenceUtils.getUserId());
        walletTransactionResponseCall.enqueue(new Callback<WalletTransactionResponse>() {
            @Override
            public void onResponse(@NonNull Call<WalletTransactionResponse> call, @NonNull Response<WalletTransactionResponse> response) {
                if (response.isSuccessful()) {
                    WalletTransactionResponse walletTransactionResponse = response.body();
                    if (walletTransactionResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchWalletTransaction, true, null, walletTransactionResponse.getData());
                    else {
                        Toast.makeText(context, walletTransactionResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchWalletTransaction, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_fetch_wallet_transaction), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchWalletTransaction, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WalletTransactionResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_fetch_wallet_transaction), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchWalletTransaction, false, null, null);
            }
        });
    }

    public void addCoinsToWallet(String amount, String paymentReferenceNo) {
        Call<BaseResponse> baseResponseCall = apiInterface.addMoneyToWallet(preferenceUtils.getUserId(), amount, paymentReferenceNo);
        baseResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.addCoinsToWallet, true, null, null);
                    else {
                        Toast.makeText(context, baseResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.addCoinsToWallet, false, null, baseResponse.getMessage()+"");
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_add_coins_to_wallet), Toast.LENGTH_SHORT).show();
                    try {
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.addCoinsToWallet, false, null, response.errorBody().string()+"");
                    } catch (IOException e) {
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.addCoinsToWallet, false, null, "");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_add_coins_to_wallet), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.addCoinsToWallet, false, null, t.getMessage()+"");
            }
        });
    }

    public void addUserFromSdk(String jsonData) {
        DrPashuRequest drPashuRequest = new DrPashuRequest();

        try {
            JSONObject jsonObject = new JSONObject(jsonData);

            drPashuRequest.setApi_key(jsonObject.getString("api_key"));
            drPashuRequest.setFirst_name(jsonObject.getString("first_name"));
            drPashuRequest.setLast_name(jsonObject.getString("last_name"));
            drPashuRequest.setPhone_number(jsonObject.getString("phone_number"));
            drPashuRequest.setDevice_id(jsonObject.getString("device_id"));
            drPashuRequest.setGender(jsonObject.getString("gender"));
            drPashuRequest.setLocation(jsonObject.getString("location"));
            drPashuRequest.setCountry(jsonObject.getString("country"));
            drPashuRequest.setState(jsonObject.getString("state"));
            drPashuRequest.setDistrict(jsonObject.getString("district"));
            drPashuRequest.setPincode(jsonObject.getString("pincode"));

            preferenceUtils.setAnimal(jsonObject.getString("animal"));
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        Call<DrPashuResponse> drPashuResponseCall = apiSdkInterface.addUserFromSdk(drPashuRequest);
        drPashuResponseCall.enqueue(new Callback<DrPashuResponse>() {
            @Override
            public void onResponse(@NonNull Call<DrPashuResponse> call, @NonNull Response<DrPashuResponse> response) {
                if (response.isSuccessful()) {
                    DrPashuResponse drPashuResponse = response.body();
                    if (drPashuResponse.getStatus()) {
                        preferenceUtils.setUserId(drPashuResponse.getData().getPhoneUserId());
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.addUserFromSdk, true, null, null);
                    } else {
                        Toast.makeText(context, drPashuResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.addUserFromSdk, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_add_user_from_sdk), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.addUserFromSdk, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DrPashuResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_add_user_from_sdk), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.addUserFromSdk, false, null, null);
            }
        });
    }
}