package com.drpashu.sdk.network;

import com.drpashu.sdk.network.model.request.AddFarmRequest;
import com.drpashu.sdk.network.model.request.AddPriceRequest;
import com.drpashu.sdk.network.model.request.DrPashuRequest;
import com.drpashu.sdk.network.model.request.LoginRequest;
import com.drpashu.sdk.network.model.request.SignupOptionalRequest;
import com.drpashu.sdk.network.model.request.SignupRequest;
import com.drpashu.sdk.network.model.request.UpdateFeedRequest;
import com.drpashu.sdk.network.model.request.UpdateProduceRequest;
import com.drpashu.sdk.network.model.request.UpdateProfileRequest;
import com.drpashu.sdk.network.model.request.UpdateVaccinationRequest;
import com.drpashu.sdk.network.model.response.AddFarmResponse;
import com.drpashu.sdk.network.model.response.AddPriceResponse;
import com.drpashu.sdk.network.model.response.AddProductionResponse;
import com.drpashu.sdk.network.model.response.AnimalDataResponse;
import com.drpashu.sdk.network.model.response.AnimalListResponse;
import com.drpashu.sdk.network.model.response.BaseResponse;
import com.drpashu.sdk.network.model.response.BreedListResponse;
import com.drpashu.sdk.network.model.response.CallDetailResponse;
import com.drpashu.sdk.network.model.response.CallHistoryListResponse;
import com.drpashu.sdk.network.model.response.DashboardResponse;
import com.drpashu.sdk.network.model.response.DeviceTokenUpdateResponse;
import com.drpashu.sdk.network.model.response.DiseaseListResponse;
import com.drpashu.sdk.network.model.response.DrPashuResponse;
import com.drpashu.sdk.network.model.response.FeedResponse;
import com.drpashu.sdk.network.model.response.FetchPostsResponse;
import com.drpashu.sdk.network.model.response.FinanceResponse;
import com.drpashu.sdk.network.model.response.GetProfileResponse;
import com.drpashu.sdk.network.model.response.HealthResponse;
import com.drpashu.sdk.network.model.response.LanguageResponse;
import com.drpashu.sdk.network.model.response.LoginResponse;
import com.drpashu.sdk.network.model.response.LotListResponse;
import com.drpashu.sdk.network.model.response.MortalityResponse;
import com.drpashu.sdk.network.model.response.ProductionResponse;
import com.drpashu.sdk.network.model.response.RazorpayOrderIdResponse;
import com.drpashu.sdk.network.model.response.ResultsResponse;
import com.drpashu.sdk.network.model.response.SearchResponse;
import com.drpashu.sdk.network.model.response.SignupOptionalResponse;
import com.drpashu.sdk.network.model.response.StartCallResponse;
import com.drpashu.sdk.network.model.response.SymptomsListResponse;
import com.drpashu.sdk.network.model.response.TransactionTypeResponse;
import com.drpashu.sdk.network.model.response.TransactionsResponse;
import com.drpashu.sdk.network.model.response.UpdateFeedResponse;
import com.drpashu.sdk.network.model.response.UpdateResponse;
import com.drpashu.sdk.network.model.response.UpdateVaccinationResponse;
import com.drpashu.sdk.network.model.response.UserDetailResponse;
import com.drpashu.sdk.network.model.response.UserSearchResponse;
import com.drpashu.sdk.network.model.response.VaccinationResponse;
import com.drpashu.sdk.network.model.response.VersionUpdateResponse;
import com.drpashu.sdk.network.model.response.VetDashboardResponse;
import com.drpashu.sdk.network.model.response.VetListResponse;
import com.drpashu.sdk.network.model.response.WalletResponse;
import com.drpashu.sdk.network.model.response.WalletTransactionResponse;
import com.drpashu.sdk.network.model.response.WeatherResponse;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @GET("animals")
    Call<List<AnimalListResponse>> getAnimalList(@Header("user-id") String user_id);

    @POST("vet_list")
    @FormUrlEncoded
    Call<VetListResponse> getVetList(@Header("user-id") String user_id,
                                     @Field("lot_id") String lot_id,
                                     @Field("animal_type") String animalType);

    @POST("start_call")
    @FormUrlEncoded
    Call<StartCallResponse> startCall(@Header("user-id") String user_id,
                                      @Field("lot_id") String lot_id,
                                      @Field("animal_type") String animalType,
                                      @Field("vet_category") String vetCategory,
                                      @Field("health_val") String healthValue,
                                      @Field("company_name") String companyName,
                                      @Field("reconnecting_call") String reconnectingCall,
                                      @Field("group_id") String groupId,
                                      @Field("free_call") String freeCall,
                                      @Field("call_price") String callPrice,
                                      @Field("payment_id") String paymentId);

    @Multipart
    @POST("upload_prescription")
    Call<BaseResponse> uploadPrescription(@Header("user-id") String user_id,
                                          @Part("call_id") RequestBody callId,
                                          @Part("details") RequestBody prescriptionDescription,
                                          @Part MultipartBody.Part file_first,
                                          @Part MultipartBody.Part file_second);

    @GET("call_history")
    Call<CallHistoryListResponse> getCallHistoryList(@Header("user-id") String user_id);

    @POST("call_details")
    @FormUrlEncoded
    Call<CallDetailResponse> getCallDetail(@Header("user-id") String user_id,
                                           @Field("call_id") String callId);

    @POST("call_status_update")
    @FormUrlEncoded
    Call<BaseResponse> updateCallStatus(@Header("user-id") String user_id,
                                        @Field("call_id") String callId,
                                        @Field("call_status") String callStatus);

    @POST("update_device_id")
    @FormUrlEncoded
    Call<DeviceTokenUpdateResponse> updateDeviceToken(@Header("user-id") String user_id,
                                                      @Field("device_id") String deviceId,
                                                      @Field("app_version") String appVersion);

    @POST("call_reject_api")
    @FormUrlEncoded
    Call<BaseResponse> rejectCall(@Header("user-id") String user_id,
                                  @Field("callid") String callId,
                                  @Field("notificationId") String notificationId,
                                  @Field("userType") String userType,
                                  @Field("call_initiated") String callInitiated);

    @POST("create_order")
    @FormUrlEncoded
    Call<RazorpayOrderIdResponse> getRazorpayOrderId(@Header("user-id") String user_id,
                                                     @Field("amount") String amount);

    @POST("call_back")
    @FormUrlEncoded
    Call<StartCallResponse> callBackUser(@Header("user-id") String user_id,
                                         @Field("call_id") String callId);

    @POST("error_api")
    @FormUrlEncoded
    Call<JsonObject> postErrorDetails(@Header("user-id") String user_id,
                                      @Field("title") String title,
                                      @Field("message") String message,
                                      @Field("details") String details);

    @GET("view_balance")
    Call<WalletResponse> fetchBalance(@Header("user-id") String user_id);

    @POST("sdk/add_user")
    Call<DrPashuResponse> addUserFromSdk(@Body DrPashuRequest drPashuRequest);

    @GET("transaction_history")
    Call<WalletTransactionResponse> fetchWalletTransaction(@Header("user-id") String user_id);

    @POST("add_money")
    @FormUrlEncoded
    Call<BaseResponse> addMoneyToWallet(@Header("user-id") String user_id,
                                        @Field("amount") String amount,
                                        @Field("reference_no") String referenceNo);
}