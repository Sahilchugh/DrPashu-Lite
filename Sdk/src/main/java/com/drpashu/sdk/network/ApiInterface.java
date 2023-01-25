package com.drpashu.sdk.network;

import com.drpashu.sdk.network.model.request.AddFarmRequest;
import com.drpashu.sdk.network.model.request.AddPriceRequest;
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
    @POST("login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @GET("animals")
    Call<List<AnimalListResponse>> getAnimalList(@Header("user-id") String user_id);

    @POST("breeds")
    @FormUrlEncoded
    Call<BreedListResponse> getBreeds(@Header("user-id") String user_id,
                                      @Field("animal_type") String animalType);

    @POST("language")
    @FormUrlEncoded
    Call<LanguageResponse> updateLanguage(@Header("user-id") String user_id,
                                          @Field("language") Integer languageIndex);

    @POST("signup")
    Call<BaseResponse> updateSignup(@Header("user-id") String user_id,
                                    @Body SignupRequest signupRequest);

    @POST("create_farm")
    Call<AddFarmResponse> addFarm(@Header("user-id") String user_id,
                                  @Body AddFarmRequest addFarmRequest);

    @GET("vaccination_overdue")
    Call<List<VaccinationResponse>> getOverdueVaccinationList(@Header("user-id") String user_id);

    @GET("vaccination_upcoming")
    Call<List<VaccinationResponse>> getUpcomingVaccinationList(@Header("user-id") String user_id);

    @GET("vaccination_completed")
    Call<List<VaccinationResponse>> getCompletedVaccinationList(@Header("user-id") String user_id);

    @GET("dashboard_farm")
    Call<DashboardResponse> getDashboardData(@Header("user-id") String user_id);

    @GET("vet_dashboard")
    Call<VetDashboardResponse> getVetDashboardData(@Header("user-id") String user_id);

    @GET("getfarm")
    Call<List<LotListResponse>> getFarmList(@Header("user-id") String user_id);

    @POST("second_signup")
    Call<SignupOptionalResponse> updateSignupOptional(@Header("user-id") String user_id,
                                                      @Body SignupOptionalRequest signupOptionalRequest);

    @Multipart
    @POST("card")
    Call<ResponseBody> updateCard(@Header("user-id") String user_id,
                                  @Part MultipartBody.Part card);

    @GET("user_details")
    Call<GetProfileResponse> getUserDetails(@Header("user-id") String user_id);

    @GET("vet_details")
    Call<GetProfileResponse> getVetDetails(@Header("user-id") String user_id);

    @GET("weather")
    Call<WeatherResponse> getWeatherDetails(@Header("user-id") String user_id);

    @POST("profile_update")
    Call<BaseResponse> updateProfile(@Header("user-id") String user_id,
                                     @Body UpdateProfileRequest updateProfileRequest);

    @POST("production")
    @FormUrlEncoded
    Call<ProductionResponse> getProduceData(@Header("user-id") String user_id,
                                            @Field("lot_id") Integer lot_id);

    @POST("feed")
    @FormUrlEncoded
    Call<FeedResponse> getFeedData(@Header("user-id") String user_id,
                                   @Field("lot_id") Integer lot_id);

    @POST("add_farmer_api")
    @FormUrlEncoded
    Call<SignupOptionalResponse> addFarmer(@Header("user-id") String user_id,
                                           @Field("phone") String number);

    @GET("list_of_cooperative")
    Call<List<String>> getCooperatives(@Header("user-id") String user_id);

    @POST("remove_farmer_api")
    @FormUrlEncoded
    Call<SignupOptionalResponse> deleteFarmer(@Header("user-id") String user_id,
                                              @Field("farmer_user_id") String farmer_user_id);

    @POST("delete_lot")
    @FormUrlEncoded
    Call<SignupOptionalResponse> deleteLot(@Header("user-id") String user_id,
                                           @Field("lot_id") Integer lot_id);

    @POST("remove_farmer_api")
    @FormUrlEncoded
    Call<SignupOptionalResponse> deleteVle(@Header("user-id") String user_id,
                                           @Field("vle") String vle);

    @POST("feedadd")
    Call<UpdateFeedResponse> updateFeed(@Header("user-id") String user_id,
                                        @Body UpdateFeedRequest updateFeedRequest);

    @POST("productionadd")
    Call<AddProductionResponse> addProduce(@Header("user-id") String user_id,
                                           @Body UpdateProduceRequest updateProduceRequest);

    @POST("productionsale")
    Call<AddPriceResponse> addProductionPrice(@Header("user-id") String user_id,
                                              @Body AddPriceRequest addPriceRequest);

    @POST("feedbuy")
    Call<AddPriceResponse> addFeedPrice(@Header("user-id") String user_id,
                                        @Body AddPriceRequest addPriceRequest);

    @POST("vaccination_add_completed")
    Call<UpdateVaccinationResponse> updateVaccinationAsCompleted(@Header("user-id") String user_id,
                                                                 @Body UpdateVaccinationRequest updateVaccinationRequest);

    @POST("finances")
    @FormUrlEncoded
    Call<FinanceResponse> getFinanceData(@Header("user-id") String user_id,
                                         @Field("lot_id") Integer lot_id);

    @POST("transactions")
    @FormUrlEncoded
    Call<TransactionsResponse> getTransactionData(@Header("user-id") String user_id,
                                                  @Field("lot_id") Integer lot_id);

    @POST("user_search")
    @FormUrlEncoded
    Call<SearchResponse> seacrhNumber(@Header("user-id") String user_id,
                                      @Field("phone") String number);

    @POST("symptoms")
    @FormUrlEncoded
    Call<List<SymptomsListResponse>> getSymptomsList(@Header("user-id") String user_id,
                                                     @Field("lot_id") String lot_id,
                                                     @Field("animal_type") String animalType);

    @POST("animal_language_images")
    @FormUrlEncoded
    Call<HealthResponse> getHealthImages(@Header("user-id") String user_id,
                                         @Field("lot_id") String lot_id,
                                         @Field("animal_type") String animalType);

    @POST("vet_status_update")
    @FormUrlEncoded
    Call<UpdateResponse> updateVetStatus(@Header("user-id") String user_id,
                                         @Field("update_status") String update_status);

    @Multipart
    @POST("symptoms_analysis")
    Call<ResultsResponse> getResults(@Header("user-id") String user_id,
                                     @Part("symptoms_data") RequestBody data,
                                     @Part("id") RequestBody id,
                                     @Part("free_flow_symptoms") RequestBody freeText,
                                     @Part("lot_id") RequestBody lot_id,
                                     @Part("animal_type") RequestBody animal_type,
                                     @Part("latitude") RequestBody latitude,
                                     @Part("longitude") RequestBody longitude,
                                     @Part MultipartBody.Part animalImg,
                                     @Part MultipartBody.Part postmortemImg);

    @Multipart
    @POST("vet_second_signup")
    Call<UpdateResponse> updateVetDetails(@Header("user-id") String user_id,
                                          @Part("vet_languages") RequestBody vet_languages,
                                          @Part("vet_animals") RequestBody vet_animals,
                                          @Part("pan_card") RequestBody pan_card,
                                          @Part("bank_account") RequestBody bank_account,
                                          @Part("ifsc") RequestBody ifsc,
                                          @Part("vet_goverment_id") RequestBody vet_goverment_id,
                                          @Part("vet_medical_registration_number") RequestBody vet_medical_registration_number,
                                          @Part("vet_degree_details ") RequestBody vet_degree_details,
                                          @Part("vet_specialization_degree_details ") RequestBody vet_specialization_degree_details,
                                          @Part MultipartBody.Part govtIdImg1,
                                          @Part MultipartBody.Part govtIdImg2,
                                          @Part MultipartBody.Part registrationImg,
                                          @Part MultipartBody.Part degreeImg,
                                          @Part MultipartBody.Part specializeImg);

    @POST("version_check_update")
    @FormUrlEncoded
    Call<VersionUpdateResponse> checkVersion(@Field("version") String version);

    @GET("all_animals")
    Call<AnimalDataResponse> getAnimals(@Header("user-id") String user_id);

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

    @POST("call_status_update")
    @FormUrlEncoded
    Call<BaseResponse> rejectIncomingCall(@Header("user-id") String user_id,
                                          @Field("call_id") String callId);

    @POST("update_onboarding")
    @FormUrlEncoded
    Call<BaseResponse> updateOnboardingStatus(@Header("user-id") String user_id,
                                              @Field("onboarding_status") String status);

    @POST("vet_schedule_update")
    @FormUrlEncoded
    Call<BaseResponse> updateVetSchedule(@Header("user-id") String user_id,
                                         @Field("timing") String status);

    @POST("mortality")
    @FormUrlEncoded
    Call<MortalityResponse> getMortalityData(@Header("user-id") String user_id,
                                             @Field("lot_id") String farmId);

    @POST("disease")
    @FormUrlEncoded
    Call<DiseaseListResponse> getDiseaseList(@Header("user-id") String user_id,
                                             @Field("lot_id") String farmId);

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

    @Multipart
    @POST("update_profile_picture")
    Call<BaseResponse> updateProfileImg(@Header("user-id") String user_id,
                                        @Part MultipartBody.Part profileImg);

    @GET("view_balance")
    Call<WalletResponse> fetchBalance(@Header("user-id") String user_id);

    @GET("transaction_history")
    Call<WalletTransactionResponse> fetchWalletTransaction(@Header("user-id") String user_id);

    @GET("view_post")
    Call<FetchPostsResponse> fetchAddaPosts(@Header("user-id") String user_id);

    @POST("view_replies")
    @FormUrlEncoded
    Call<FetchPostsResponse> fetchPostReplies(@Header("user-id") String user_id,
                                              @Field("id") int id);

    @POST("add_money")
    @FormUrlEncoded
    Call<BaseResponse> addMoneyToWallet(@Header("user-id") String user_id,
                                        @Field("amount") String amount,
                                        @Field("reference_no") String referenceNo);

    @POST("admin_search")
    @FormUrlEncoded
    Call<UserSearchResponse> getUserSearchList(@Header("admin-user-id") String admin_user_id,
                                               @Field("search") String search);

    @POST("admin_cust_details")
    @FormUrlEncoded
    Call<UserDetailResponse> getUserDetail(@Header("admin-user-id") String admin_user_id,
                                           @Field("cust-id") int userId);

    @POST("admin_call")
    Call<StartCallResponse> callUserById(@Header("admin-user-id") String admin_user_id,
                                         @Header("user-id") int user_id);

    @GET("transaction_type")
    Call<TransactionTypeResponse> getTransactionTypeList();

    @POST("transaction")
    @FormUrlEncoded
    Call<BaseResponse> createTransactionByAdmin(@Header("admin-user-id") String admin_user_id,
                                                @Header("user-id") int user_id,
                                                @Field("reference_no") String referenceNo,
                                                @Field("amount") int amount,
                                                @Field("transaction_type") int transactionType,
                                                @Field("remark") String remark);

    @Multipart
    @POST("addmortality")
    Call<BaseResponse> addMortalityData(@Header("user-id") String user_id,
                                        @Part("lot_id") RequestBody farmId,
                                        @Part("date") RequestBody date,
                                        @Part("quantity") RequestBody quantity,
                                        @Part("culled_quantity") RequestBody culled_quantity,
                                        @Part("disease") RequestBody disease,
                                        @Part("culled_reason") RequestBody culled_reason,
                                        @Part MultipartBody.Part firstImg,
                                        @Part MultipartBody.Part secondImg);

    @POST("create_post")
    @FormUrlEncoded
    Call<BaseResponse> createReply(@Header("user-id") String user_id,
                                   @Field("message") String message,
                                   @Field("parent_post_id") int parentPostId);

    @POST("post_action")
    @FormUrlEncoded
    Call<BaseResponse> postAction(@Header("user-id") String user_id,
                                  @Field("id") int id,
                                  @Field("action") String action,
                                  @Field("action_status") Boolean actionStatus);

    @POST("search")
    @FormUrlEncoded
    Call<FetchPostsResponse> postSearch(@Header("user-id") String user_id,
                                        @Field("search") String search);

    @Multipart
    @POST("create_post")
    Call<BaseResponse> createPost(@Header("user-id") String user_id,
                                  @Part("title") RequestBody title,
                                  @Part("message") RequestBody message,
                                  @Part MultipartBody.Part image,
                                  @Part MultipartBody.Part video);
}