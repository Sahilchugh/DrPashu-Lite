package com.drpashu.sdk.network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.drpashu.sdk.BuildConfig;
import com.drpashu.sdk.R;
import com.drpashu.sdk.HomeActivity;
//import com.drpashu.sdk.adapter.FarmSettingsAdapter;
//import com.drpashu.sdk.adapter.TransactionsAdapter;
import com.drpashu.sdk.network.model.request.AddFarmRequest;
import com.drpashu.sdk.network.model.request.AddPriceRequest;
import com.drpashu.sdk.network.model.request.LanguageRequest;
import com.drpashu.sdk.network.model.request.LoginRequest;
import com.drpashu.sdk.network.model.request.PostActionRequest;
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
import com.drpashu.sdk.utils.PreferenceUtils;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Networking {
    private final PreferenceUtils preferenceUtils;
    private final Context context;
    private final Activity activity;
    Retrofit retrofit = ApiClient.getRetrofitInstance();
    private static ProgressDialog progressdialog;
    NetworkingInterface networkingInterface;
    public static int lot_id = 0;
    public static String lot_name_text = "";
    ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    public Networking(Context context, Activity activity, NetworkingInterface networkingInterface) {
        this.context = context;
        this.activity = activity;
        this.networkingInterface = networkingInterface;
        preferenceUtils = new PreferenceUtils(context);
    }

    public void register(String number, String email, String user_id, int signInMethod) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUser_id(user_id);
//        loginRequest.setApp_version(BuildConfig.VERSION_NAME);
        loginRequest.setDevice_id(preferenceUtils.getFcmToken());

        if (signInMethod == 1) {
            loginRequest.setPhone(number);
        } else if (signInMethod == 2) {
            loginRequest.setEmail(email);
        }

        Call<LoginResponse> loginResponseCall = apiInterface.userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.login, true, null, loginResponse);
                } else {
                    Toast.makeText(context, context.getString(R.string.error_login), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.login, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getString(R.string.error_login), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.login, false, null, null);
            }
        });

    }

    public void signup(String first_name_text, String last_name_text, String phone_number_text, String email_text,
                       int gender_value, int farming_type_value, String country_text, String state_text, String district_text,
                       String pincode_text, String address_text, String animals, int userRole) {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(context.getString(R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.show();

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setFirst_name(first_name_text);
        signupRequest.setLast_name(last_name_text);
        if (phone_number_text.length() != 0)
            signupRequest.setPhone("+91" + phone_number_text);
        signupRequest.setEmail(email_text);
        signupRequest.setGender(gender_value + "");
        signupRequest.setUser_role(userRole + "");
        signupRequest.setFarming_type(farming_type_value + "");
        signupRequest.setCountry(country_text.toUpperCase());
        signupRequest.setState(state_text);
        signupRequest.setDistrict(district_text);
        signupRequest.setPincode(pincode_text);
        signupRequest.setLocation(address_text);
        signupRequest.setFarmerAnimals(animals);

        Call<BaseResponse> baseResponseCall = apiInterface.updateSignup(preferenceUtils.getUserId(), signupRequest);
        baseResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse.getStatus()) {
                        preferenceUtils.setLoggedIn(true);
                        preferenceUtils.setUsername(first_name_text + " " + last_name_text);
                        if (phone_number_text.length() != 0)
                            preferenceUtils.setPhoneNumber("+91" + phone_number_text);
                        preferenceUtils.setEmail(email_text);
                        preferenceUtils.setUserRole(userRole);
                        Toast.makeText(context, context.getString(R.string.profile_updated), Toast.LENGTH_SHORT).show();

                        activity.startActivity(new Intent(activity, HomeActivity.class));
                        activity.finish();
                    } else
                        Toast.makeText(context, baseResponse.getMessage()+"", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, context.getString(R.string.error_signup), Toast.LENGTH_SHORT).show();

                progressdialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getString(R.string.error_signup), Toast.LENGTH_SHORT).show();
                progressdialog.dismiss();
            }
        });
    }

    public void getBreedList(RecyclerView recyclerView, String animalType, EditText quantity) {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(context.getString(R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.show();


        Call<BreedListResponse> getBreedList = apiInterface.getBreeds(preferenceUtils.getUserId(), animalType);
        getBreedList.enqueue(new Callback<BreedListResponse>() {
            @Override
            public void onResponse(@NonNull Call<BreedListResponse> call, @NonNull Response<BreedListResponse> response) {

                if (response.isSuccessful()) {

                    BreedListResponse breedListResponseList = response.body();

                    quantity.setText(breedListResponseList.getAnimal_default()+"");
//                    final FarmSettingsAdapter farmSettingsAdapter = new FarmSettingsAdapter(context, breedListResponseList);
//                    recyclerView.setAdapter(farmSettingsAdapter);

                } else
                    Toast.makeText(context, context.getString(R.string.error_animal_list), Toast.LENGTH_SHORT).show();

                progressdialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<BreedListResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getString(R.string.error_animal_list), Toast.LENGTH_SHORT).show();
                progressdialog.dismiss();
            }
        });

    }

    public void update_language(int selection) {
        LanguageRequest languageRequest = new LanguageRequest();
        languageRequest.setLanguage(selection);

        Call<LanguageResponse> languageResponseCall = apiInterface.updateLanguage(preferenceUtils.getUserId(), selection);
        languageResponseCall.enqueue(new Callback<LanguageResponse>() {
            @Override
            public void onResponse(@NonNull Call<LanguageResponse> call, @NonNull Response<LanguageResponse> response) {
                if (response.isSuccessful()) {
                    LanguageResponse languageResponse = response.body();
                    if (languageResponse == null)
                        return;
                    preferenceUtils.setlanguage_index(languageResponse.getLanguage());
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateLanguage, true, null, null);
                } else {
                    Toast.makeText(context, context.getString(R.string.error_update_language), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateLanguage, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<LanguageResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getString(R.string.error_update_language), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateLanguage, false, null, null);
            }
        });
    }

    public void selectChicken(int id, String quantity, String dateOfBirth, String lot_name, int loanValue,
                              String emiAmount, String durationMonths, String emiStartDate, String animalType) {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(context.getString(R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.show();

        AddFarmRequest addFarmRequest = new AddFarmRequest();
        addFarmRequest.setAnimal_id(id + "");
        addFarmRequest.setAnimal_quantity(quantity + "");
        addFarmRequest.setAnimal_dob(dateOfBirth);
        addFarmRequest.setLot_name(lot_name);
        addFarmRequest.setIs_loan(loanValue + "");
        addFarmRequest.setEMI(emiAmount);
        addFarmRequest.setDuration_months(durationMonths);
        addFarmRequest.setEmi_start_date(emiStartDate);
        addFarmRequest.setEmi_start_date(emiStartDate);
        addFarmRequest.setAnimal_type(animalType);

        Call<AddFarmResponse> addChickenResponseCall = apiInterface.addFarm(preferenceUtils.getUserId(), addFarmRequest);
        addChickenResponseCall.enqueue(new Callback<AddFarmResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddFarmResponse> call, @NonNull Response<AddFarmResponse> response) {
                progressdialog.dismiss();
                if (response.isSuccessful()) {
                    activity.onBackPressed();
                    Toast.makeText(context, context.getString(R.string.add_animal_success), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, context.getString(R.string.error_add_animal), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<AddFarmResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getString(R.string.error_add_animal), Toast.LENGTH_SHORT).show();
                progressdialog.dismiss();
            }
        });
    }

    public void getOverdueVaccinationList() {
        ProgressDialog progressdialog_overdue = new ProgressDialog(context);
        progressdialog_overdue.setMessage(context.getString(R.string.loading));
        progressdialog_overdue.setCancelable(false);

        progressdialog_overdue.show();


        Call<List<VaccinationResponse>> overdueVaccinationList = apiInterface.getOverdueVaccinationList(preferenceUtils.getUserId());
        overdueVaccinationList.enqueue(new Callback<List<VaccinationResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<VaccinationResponse>> call, @NonNull Response<List<VaccinationResponse>> response) {

                if (response.isSuccessful()) {
                    List<VaccinationResponse> vaccinationResponse = response.body();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.overdueList, true, null, vaccinationResponse);
                } else
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.overdueList, false, null, null);
                progressdialog_overdue.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<List<VaccinationResponse>> call, @NonNull Throwable t) {
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.overdueList, false, null, null);
                progressdialog_overdue.dismiss();
            }
        });
    }

    public void getUpcomingVaccinationList() {
        ProgressDialog progressdialog_upcoming = new ProgressDialog(context);
        progressdialog_upcoming.setMessage(context.getString(R.string.loading));
        progressdialog_upcoming.setCancelable(false);
        progressdialog_upcoming.show();


        Call<List<VaccinationResponse>> upcomingVaccinationList = apiInterface.getUpcomingVaccinationList(preferenceUtils.getUserId());
        upcomingVaccinationList.enqueue(new Callback<List<VaccinationResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<VaccinationResponse>> call, @NonNull Response<List<VaccinationResponse>> response) {

                if (response.isSuccessful()) {
                    List<VaccinationResponse> vaccinationResponse = response.body();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.upcomingList, true, null, vaccinationResponse);
                } else
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.upcomingList, false, null, null);
                progressdialog_upcoming.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<List<VaccinationResponse>> call, @NonNull Throwable t) {
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.upcomingList, false, null, null);
                progressdialog_upcoming.dismiss();
            }
        });
    }

    public void getCompletedVaccinationList() {
        ProgressDialog progressdialog_completed = new ProgressDialog(context);
        progressdialog_completed.setMessage(context.getString(R.string.loading));
        progressdialog_completed.setCancelable(false);

        progressdialog_completed.show();


        Call<List<VaccinationResponse>> completedVaccinationList = apiInterface.getCompletedVaccinationList(preferenceUtils.getUserId());
        completedVaccinationList.enqueue(new Callback<List<VaccinationResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<VaccinationResponse>> call, @NonNull Response<List<VaccinationResponse>> response) {

                if (response.isSuccessful()) {
                    List<VaccinationResponse> vaccinationResponse = response.body();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.completedList, true, null, vaccinationResponse);
                } else
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.completedList, false, null, null);
                progressdialog_completed.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<List<VaccinationResponse>> call, @NonNull Throwable t) {
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.completedList, false, null, null);
                progressdialog_completed.dismiss();
            }
        });
    }

    public void updateVaccination(int id, String name_lot, String date, int lot_id, String amount, String userId) {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(context.getString(R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.show();

        UpdateVaccinationRequest updateVaccinationRequest = new UpdateVaccinationRequest();
        updateVaccinationRequest.setVaccination(id);
        updateVaccinationRequest.setLot_name(name_lot);
        updateVaccinationRequest.setDate(date);
        updateVaccinationRequest.setAmount(amount);
        updateVaccinationRequest.setLot_id(lot_id);

        Call<UpdateVaccinationResponse> updateVaccinationResponseCall = apiInterface.updateVaccinationAsCompleted(userId, updateVaccinationRequest);
        updateVaccinationResponseCall.enqueue(new Callback<UpdateVaccinationResponse>() {
            @Override
            public void onResponse(@NonNull Call<UpdateVaccinationResponse> call, @NonNull Response<UpdateVaccinationResponse> response) {

                if (response.isSuccessful()) {
                    progressdialog.dismiss();
                    Toast.makeText(context, context.getString(R.string.update_vaccination_success), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateVaccination, true, null, null);
                } else {
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateVaccination, false, null, null);
                    Toast.makeText(context, context.getString(R.string.error_update_vaccination), Toast.LENGTH_SHORT).show();
                }
                progressdialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<UpdateVaccinationResponse> call, @NonNull Throwable t) {
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateVaccination, false, null, null);
                Toast.makeText(context, context.getString(R.string.error_update_vaccination), Toast.LENGTH_SHORT).show();
                progressdialog.dismiss();

            }
        });

    }

    public void signupOptional(String mother_name_text, String age_text, int qualification_id_value, String cooperativeText, String landline_text,
                               String identity_number_text, String family_member_text, String bank_account_text, String ifsc_text, String pan_card_text,
                               String subsidy_information_text, int kissan_card_holder_value, int job_card_holder_value, String aadhar_number_text, String farmer_number_text) {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(context.getString(R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.show();

        SignupOptionalRequest signupOptionalRequest = new SignupOptionalRequest();
        signupOptionalRequest.setMother_name(mother_name_text);
        signupOptionalRequest.setAge(age_text);
        signupOptionalRequest.setQualification_id(qualification_id_value + "");
        signupOptionalRequest.setCooperative(cooperativeText);
        signupOptionalRequest.setLandline(landline_text);
        signupOptionalRequest.setIdentity_number(identity_number_text);
        signupOptionalRequest.setFamily_member(family_member_text);
        signupOptionalRequest.setBank_account(bank_account_text);
        signupOptionalRequest.setIfsc(ifsc_text);
        signupOptionalRequest.setPan_card(pan_card_text);
        signupOptionalRequest.setSubsidy_information(subsidy_information_text);
        signupOptionalRequest.setKissan_card_holder(kissan_card_holder_value + "");
        signupOptionalRequest.setJobcard_holder(job_card_holder_value + "");
        signupOptionalRequest.setAdhaar_number(aadhar_number_text);
        signupOptionalRequest.setFarmer_number(farmer_number_text);

        Call<SignupOptionalResponse> signupOptionalResponseCall = apiInterface.updateSignupOptional(preferenceUtils.getUserId(), signupOptionalRequest);
        signupOptionalResponseCall.enqueue(new Callback<SignupOptionalResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignupOptionalResponse> call, @NonNull Response<SignupOptionalResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, context.getString(R.string.profile_updated), Toast.LENGTH_SHORT).show();
                    preferenceUtils.setLoggedIn(true);
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.signup, true, null, null);
                } else
                    Toast.makeText(context, context.getString(R.string.error_signup_optional), Toast.LENGTH_SHORT).show();
                progressdialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<SignupOptionalResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getString(R.string.error_signup_optional), Toast.LENGTH_SHORT).show();
                progressdialog.dismiss();
            }
        });
    }

    public void getSymptomsList(int lotId, String breedName) {
        Call<List<SymptomsListResponse>> symptomsList;
        if (lotId == 0)
            symptomsList = apiInterface.getSymptomsList(preferenceUtils.getUserId(), null, breedName);
        else
            symptomsList = apiInterface.getSymptomsList(preferenceUtils.getUserId(), lotId+"", null);
        symptomsList.enqueue(new Callback<List<SymptomsListResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<SymptomsListResponse>> call, @NonNull Response<List<SymptomsListResponse>> response) {

                if (response.isSuccessful()) {
                    List<SymptomsListResponse> symptomsListResponses = response.body();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.symptomsList, true, null, symptomsListResponses);
                } else {
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.symptomsList, false, null, null);
                    Toast.makeText(context, context.getString(R.string.error_symptoms_list), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<SymptomsListResponse>> call, @NonNull Throwable t) {
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.symptomsList, false, null, null);
                Toast.makeText(context, context.getString(R.string.error_symptoms_list), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getProfileInformation() {
        Call<GetProfileResponse> getProfileResponseCall;
        if (preferenceUtils.getUserRole() == 0 || preferenceUtils.getUserRole() == 1)
            getProfileResponseCall = apiInterface.getUserDetails(preferenceUtils.getUserId());
        else
            getProfileResponseCall = apiInterface.getVetDetails(preferenceUtils.getUserId());

        getProfileResponseCall.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetProfileResponse> call, @NonNull Response<GetProfileResponse> response) {
                if (response.isSuccessful()) {
                    GetProfileResponse getProfileResponse = response.body();
                    if (getProfileResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.getProfile, true, null, getProfileResponse.getData());
                    else
                        Toast.makeText(context, getProfileResponse.getMessage()+"", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, context.getString(R.string.error_profile_data), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.getProfile, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetProfileResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getString(R.string.error_profile_data), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.getProfile, false, null, null);
            }
        });
    }

    public void uploadCard(Bitmap imageBitmap, String source) {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(context.getString(R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.show();

        MultipartBody.Part requestImage = null;

        long imagename = System.currentTimeMillis();

        RequestBody requestBody = RequestBody.create(getFileDataFromDrawable(imageBitmap), MediaType.parse("multipart/form-data"));

        if (source.equalsIgnoreCase("adhaar"))
            requestImage = MultipartBody.Part.createFormData("adhaar_card", imagename + ".png", requestBody);
        else if (source.equalsIgnoreCase("farmer"))
            requestImage = MultipartBody.Part.createFormData("farmer_card", imagename + ".png", requestBody);

        Call<ResponseBody> call = apiInterface.updateCard(preferenceUtils.getUserId(), requestImage);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                if (response.isSuccessful())
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.uploadImage, true, null, source);
                else
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.uploadImage, false, null, null);
                progressdialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.uploadImage, false, null, null);
                progressdialog.dismiss();
            }
        });
    }

    public void getDashboardData() {
        Call<DashboardResponse> dashboardResponseCall = apiInterface.getDashboardData(preferenceUtils.getUserId());
        dashboardResponseCall.enqueue(new Callback<DashboardResponse>() {
            @Override
            public void onResponse(@NonNull Call<DashboardResponse> call, @NonNull Response<DashboardResponse> response) {
                if (response.isSuccessful()) {
                    DashboardResponse dashboardResponse = response.body();
                    if (dashboardResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.dashboardInfo, true, null, dashboardResponse.getData());
                    else {
                        Toast.makeText(context, dashboardResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.dashboardInfo, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getString(R.string.error_dashboard_list), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.dashboardInfo, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DashboardResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getString(R.string.error_dashboard_list), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.dashboardInfo, false, null, null);
            }
        });
    }

    public void getFarmListHealth() {
        Call<List<LotListResponse>> lotList = apiInterface.getFarmList(preferenceUtils.getUserId());
        lotList.enqueue(new Callback<List<LotListResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<LotListResponse>> call, @NonNull Response<List<LotListResponse>> response) {
                if (response.isSuccessful()) {
                    List<LotListResponse> lotListResponseList = response.body();
//                    getSymptomsList(lot_id);
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchLot, true, null, lotListResponseList);
                } else {
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchLot, false, null, null);
                    Toast.makeText(context, context.getString(R.string.error_farm_list), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<LotListResponse>> call, @NonNull Throwable t) {
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchLot, false, null, null);
                Toast.makeText(context, context.getString(R.string.error_farm_list), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateProfile(String first_name_text, String last_name_text, String phone_number_text, String email_text, int gender_value,
                              int farming_type_value, String country_text, String state_text, String district_text, String pincode_text, String address_text, String animals) {

        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
        updateProfileRequest.setFirst_name(first_name_text);
        updateProfileRequest.setLast_name(last_name_text);
        if (phone_number_text.length() != 0)
            updateProfileRequest.setPhone("+91" + phone_number_text);
        updateProfileRequest.setEmail(email_text);
        updateProfileRequest.setGender(gender_value + "");
        updateProfileRequest.setFarmingType(farming_type_value + "");
        updateProfileRequest.setCountry(country_text.toUpperCase());
        updateProfileRequest.setState(state_text);
        updateProfileRequest.setDistrict(district_text);
        updateProfileRequest.setPincode(pincode_text);
        updateProfileRequest.setLocation(address_text);
        updateProfileRequest.setFarmerAnimals(animals);

        Call<BaseResponse> baseResponseCall = apiInterface.updateProfile(preferenceUtils.getUserId(), updateProfileRequest);
        baseResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateProfile, true, null, null);
                    else {
                        Toast.makeText(context, baseResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateProfile, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getString(R.string.error_update_profile), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateProfile, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getString(R.string.error_update_profile), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateProfile, false, null, null);
            }
        });
    }

    public void getFarmList() {
        Call<List<LotListResponse>> lotList = apiInterface.getFarmList(preferenceUtils.getUserId());
        lotList.enqueue(new Callback<List<LotListResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<LotListResponse>> call, @NonNull Response<List<LotListResponse>> response) {
                if (response.isSuccessful()) {
                    List<LotListResponse> lotListResponseList = response.body();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.getFarmList, true, null, lotListResponseList);
                } else {
                    Toast.makeText(context, context.getString(R.string.error_farm_list), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.getFarmList, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<LotListResponse>> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getString(R.string.error_farm_list), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.getFarmList, false, null, null);
            }
        });
    }

    public void addPriceEgg(int lot_id, int quantity, String dateInput, String price, String userId) {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(context.getString(R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.show();

        AddPriceRequest addPriceRequest = new AddPriceRequest();
        addPriceRequest.setLot_id(lot_id + "");
        addPriceRequest.setInfo(quantity + "");
        addPriceRequest.setDate(dateInput);
        addPriceRequest.setAmount(price);

        Call<AddPriceResponse> addPriceResponseCall = apiInterface.addProductionPrice(userId, addPriceRequest);
        addPriceResponseCall.enqueue(new Callback<AddPriceResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddPriceResponse> call, @NonNull Response<AddPriceResponse> response) {
                if (response.isSuccessful())
                    Toast.makeText(activity, context.getString(R.string.price_added), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(activity, context.getString(R.string.error_produce_sale), Toast.LENGTH_SHORT).show();
                progressdialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<AddPriceResponse> call, @NonNull Throwable t) {
                Toast.makeText(activity, context.getString(R.string.error_produce_sale), Toast.LENGTH_SHORT).show();
                progressdialog.dismiss();
            }
        });
    }

    public void addPriceFeed(int lot_id, int quantity, String dateInput, String price, String userId) {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(context.getString(R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.show();

        AddPriceRequest addPriceRequest = new AddPriceRequest();
        addPriceRequest.setLot_id(lot_id + "");
        addPriceRequest.setInfo(quantity + "");
        addPriceRequest.setDate(dateInput);
        addPriceRequest.setAmount(price);

        Call<AddPriceResponse> addPriceResponseCall = apiInterface.addFeedPrice(userId, addPriceRequest);
        addPriceResponseCall.enqueue(new Callback<AddPriceResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddPriceResponse> call, @NonNull Response<AddPriceResponse> response) {

                if (response.isSuccessful())
                    Toast.makeText(context, context.getString(R.string.price_added), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, context.getString(R.string.error_feed_buy), Toast.LENGTH_SHORT).show();

                progressdialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<AddPriceResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getString(R.string.error_feed_buy), Toast.LENGTH_SHORT).show();
                progressdialog.dismiss();
            }
        });
    }

    public void getTransactions(RecyclerView transactionsRecyclerView) {
        Call<TransactionsResponse> transactionsResponseCall = apiInterface.getTransactionData(preferenceUtils.getUserIdLot(), lot_id);
        transactionsResponseCall.enqueue(new Callback<TransactionsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TransactionsResponse> call, @NonNull Response<TransactionsResponse> response) {
                if (response.isSuccessful()) {
                    TransactionsResponse transactionsResponse = response.body();
//                    final TransactionsAdapter transactionsAdapter = new TransactionsAdapter(context, transactionsResponse);
//                    transactionsRecyclerView.setAdapter(transactionsAdapter);
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchTransactions, true, null, null);
                } else {
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchTransactions, false, null, null);
                    Toast.makeText(context, context.getString(R.string.error_transactions_list), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TransactionsResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getString(R.string.error_transactions_list), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchTransactions, true, null, null);

            }
        });
    }

    public void getFarmListFinance(AutoCompleteTextView lot_name, int lotId) {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(context.getString(R.string.loading));
        progressdialog.setCancelable(false);
        lot_id = lotId;

        Call<List<LotListResponse>> lotList = apiInterface.getFarmList(preferenceUtils.getUserId());
        lotList.enqueue(new Callback<List<LotListResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<LotListResponse>> call, @NonNull Response<List<LotListResponse>> response) {

                if (response.isSuccessful()) {

                    List<LotListResponse> lotListResponseList = response.body();

                    if (lotListResponseList == null)
                        return;

                    List<String> lot_name_list = new ArrayList<>();
                    List<Integer> lot_id_list = new ArrayList<>();
                    List<String> user_id_list = new ArrayList<>();

                    for (int i = 0; i < lotListResponseList.size(); i++) {
                        if (lotListResponseList.get(i).getLotName() != null) {
                            lot_id_list.add(lotListResponseList.get(i).getId());
                            if (lotListResponseList.get(i).getOwner().equalsIgnoreCase("other")) {
                                lot_name_list.add(lotListResponseList.get(i).getFirstName() + "'s Farm- " + lotListResponseList.get(i).getLotName());
                                user_id_list.add(lotListResponseList.get(i).getUser_id());
                            } else {
                                user_id_list.add(lotListResponseList.get(i).getUser_id_vle());
                                lot_name_list.add(lotListResponseList.get(i).getAnimal_name()+" - "+lotListResponseList.get(i).getLotName());
//                                lot_name_list.add(lotListResponseList.get(i).getLotName());
                            }
                        }
                    }

                    ArrayAdapter<String> lot_list_array = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, lot_name_list);
                    lot_list_array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    lot_name.setAdapter(lot_list_array);

                    lot_name.setOnItemClickListener((parent, view, position, id) -> {
                        lot_id = lot_id_list.get(position);
                        preferenceUtils.setUserIdLot(user_id_list.get(position));
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchLotFinance, true, null, lotListResponseList.size());

                    });

                    if (lot_id == 0) {
                        if (lot_id_list.size() != 0) {
                            lot_name.setText(lot_name.getAdapter().getItem(0).toString(), false);
                            lot_id = lot_id_list.get(0);
                            preferenceUtils.setUserIdLot(user_id_list.get(0));
                        }
                    } else {
                        for (int i = 0; i < lot_id_list.size(); i++) {
                            if (lot_id_list.get(i) == lot_id) {
                                lot_name.setText(lot_name.getAdapter().getItem(i).toString(), false);
                                preferenceUtils.setUserIdLot(user_id_list.get(i));
                                break;
                            }
                        }
                    }
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchLotFinance, true, null, lotListResponseList.size());
                } else {
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchLotFinance, false, null, null);
                    Toast.makeText(context, context.getString(R.string.error_farm_list), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<LotListResponse>> call, @NonNull Throwable t) {
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchLotFinance, false, t, null);

                Toast.makeText(context, context.getString(R.string.error_farm_list), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getFinanceData() {
        Call<FinanceResponse> financeResponses = apiInterface.getFinanceData(preferenceUtils.getUserIdLot(), lot_id);
        financeResponses.enqueue(new Callback<FinanceResponse>() {
            @Override
            public void onResponse(@NonNull Call<FinanceResponse> call, @NonNull Response<FinanceResponse> response) {
                if (response.isSuccessful()) {
                    FinanceResponse financeResponse = response.body();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchFinanceData, true, null, financeResponse);
                } else
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchFinanceData, false, null, null);
            }

            @Override
            public void onFailure(@NonNull Call<FinanceResponse> call, @NonNull Throwable t) {
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchFinanceData, false, null, null);
            }
        });
    }

    public void getResults( String farmId, String animalType, String symptomIdList, String symptomDataList, String symptomText,
                            byte[] animalByte, byte[] postmortemByte, String latitude, String longitude) {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(context.getString(R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.show();

        MultipartBody.Part requestAnimalImg = null;
        MultipartBody.Part requestPostmortemImg = null;
        RequestBody requestLongitude = null;
        RequestBody requestLatitude = null;

        long imagename = System.currentTimeMillis();

        RequestBody requestSymptomDataList = RequestBody.create(symptomDataList, MediaType.parse("multipart/form-data"));
        RequestBody requestSymptomText = RequestBody.create(symptomText, MediaType.parse("multipart/form-data"));
        RequestBody requestSymptomIdList = RequestBody.create(symptomIdList, MediaType.parse("multipart/form-data"));


        if (animalByte != null) {
            RequestBody requestBodyAnimal = RequestBody.create(animalByte, MediaType.parse("multipart/form-data"));
            requestAnimalImg = MultipartBody.Part.createFormData("symptoms_image", imagename + ".png", requestBodyAnimal);
            requestLatitude = RequestBody.create(latitude, MediaType.parse("multipart/form-data"));
            requestLongitude = RequestBody.create(longitude, MediaType.parse("multipart/form-data"));
        }

        if (postmortemByte != null){
            RequestBody requestBodyPostmortem = RequestBody.create(postmortemByte, MediaType.parse("multipart/form-data"));
            requestPostmortemImg = MultipartBody.Part.createFormData("postmortem_image", imagename + ".png", requestBodyPostmortem);
        }

        Call<ResultsResponse> resultsResponseCall;

        if (farmId.length() != 0) {
            RequestBody requestfarmId = RequestBody.create(farmId, MediaType.parse("multipart/form-data"));
            resultsResponseCall = apiInterface.getResults(preferenceUtils.getUserId(), requestSymptomDataList, requestSymptomIdList, requestSymptomText, requestfarmId,
                                                            null, requestLatitude, requestLongitude, requestAnimalImg, requestPostmortemImg);
        } else {
            RequestBody requestAnimaltype = RequestBody.create(animalType, MediaType.parse("multipart/form-data"));
            resultsResponseCall = apiInterface.getResults(preferenceUtils.getUserId(), requestSymptomDataList, requestSymptomIdList, requestSymptomText, null,
                                                            requestAnimaltype, requestLatitude, requestLongitude, requestAnimalImg, requestPostmortemImg);
        }

        resultsResponseCall.enqueue(new Callback<ResultsResponse>() {
            @Override
            public void onResponse(@NonNull Call<ResultsResponse> call, @NonNull Response<ResultsResponse> response) {

                if (response.isSuccessful()) {
                    ResultsResponse resultsResponse = response.body();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.results, true, null, resultsResponse);
                } else {
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.results, false, null, null);
                    Toast.makeText(context, context.getString(R.string.error_analysis_result), Toast.LENGTH_SHORT).show();
                }
                progressdialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<ResultsResponse> call, @NonNull Throwable t) {
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.results, false, t, null);
                Toast.makeText(context, context.getString(R.string.error_analysis_result), Toast.LENGTH_SHORT).show();
                progressdialog.dismiss();
            }
        });
    }

    public void deleteLot(Integer id) {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(context.getString(R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.show();

        Call<SignupOptionalResponse> signupOptionalResponseCall = apiInterface.deleteLot(preferenceUtils.getUserId(), id);
        signupOptionalResponseCall.enqueue(new Callback<SignupOptionalResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignupOptionalResponse> call, @NonNull Response<SignupOptionalResponse> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(context, context.getString(R.string.farm_delete_success), Toast.LENGTH_SHORT).show();
                    activity.onBackPressed();
                } else
                    Toast.makeText(context, context.getString(R.string.error_delete_farm), Toast.LENGTH_SHORT).show();

                progressdialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<SignupOptionalResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getString(R.string.error_delete_farm), Toast.LENGTH_SHORT).show();
                progressdialog.dismiss();
            }
        });
    }

    public void getWeatherInfo() {
        Call<WeatherResponse> weatherResponseCall = apiInterface.getWeatherDetails(preferenceUtils.getUserId());
        weatherResponseCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.weatherInfo, true, null, weatherResponse);
                } else {
//                    Toast.makeText(context, context.getString(R.string.error_weather_data), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.weatherInfo, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
//                Toast.makeText(context, context.getString(R.string.error_weather_data), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.weatherInfo, false, null, null);
            }
        });
    }

    public void searchNumber(String number) {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(context.getString(R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.show();

        Call<SearchResponse> searchResponseCall = apiInterface.seacrhNumber(preferenceUtils.getUserId(), "+91" + number);
        searchResponseCall.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    SearchResponse searchResponse = response.body();
                    progressdialog.dismiss();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.search, true, null, searchResponse);
                } else {
                    progressdialog.dismiss();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.search, false, null, null);
                    Toast.makeText(context, context.getString(R.string.error_search_data), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable t) {
                progressdialog.dismiss();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.search, false, null, null);
                Toast.makeText(context, context.getString(R.string.error_search_data), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addFarmer(String number) {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(context.getString(R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.show();

        Call<SignupOptionalResponse> signupOptionalResponseCall = apiInterface.addFarmer(preferenceUtils.getUserId(), number);
        signupOptionalResponseCall.enqueue(new Callback<SignupOptionalResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignupOptionalResponse> call, @NonNull Response<SignupOptionalResponse> response) {
                progressdialog.dismiss();
                if (response.isSuccessful()) {
                    SignupOptionalResponse signupOptionalResponse = response.body();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.addFarmer, true, null, signupOptionalResponse);
                } else {
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.addFarmer, false, null, null);
                    Toast.makeText(context, context.getString(R.string.error_add_farmer), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignupOptionalResponse> call, @NonNull Throwable t) {
                progressdialog.dismiss();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.addFarmer, false, null, null);
                Toast.makeText(context, context.getString(R.string.error_add_farmer), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteFarmer(String user_id) {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(context.getString(R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.show();

        Call<SignupOptionalResponse> signupOptionalResponseCall = apiInterface.deleteFarmer(preferenceUtils.getUserId(), user_id);
        signupOptionalResponseCall.enqueue(new Callback<SignupOptionalResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignupOptionalResponse> call, @NonNull Response<SignupOptionalResponse> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(context, context.getString(R.string.farmer_deleted), Toast.LENGTH_SHORT).show();
                    activity.onBackPressed();
                } else
                    Toast.makeText(context, context.getString(R.string.error_delete_farmer), Toast.LENGTH_SHORT).show();

                progressdialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<SignupOptionalResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getString(R.string.error_delete_farmer), Toast.LENGTH_SHORT).show();
                progressdialog.dismiss();
            }
        });
    }

    public void deleteVle(String user_id) {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(context.getString(R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.show();

        Call<SignupOptionalResponse> signupOptionalResponseCall = apiInterface.deleteVle(preferenceUtils.getUserId(), user_id);
        signupOptionalResponseCall.enqueue(new Callback<SignupOptionalResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignupOptionalResponse> call, @NonNull Response<SignupOptionalResponse> response) {
                progressdialog.dismiss();

                if (response.isSuccessful()) {
                    Toast.makeText(context, context.getString(R.string.vle_delete_success), Toast.LENGTH_SHORT).show();
                    activity.onBackPressed();
                } else
                    Toast.makeText(context, context.getString(R.string.error_delete_farmer), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<SignupOptionalResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getString(R.string.error_delete_farmer), Toast.LENGTH_SHORT).show();
                progressdialog.dismiss();
            }
        });
    }

    public void getCooperativeList() {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(context.getString(R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.show();

        Call<List<String>> cooperativesList = apiInterface.getCooperatives(preferenceUtils.getUserId());
        cooperativesList.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                progressdialog.dismiss();

                if (response.isSuccessful()) {
                    List<String> cooperatives = response.body();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.cooperative, true, null, cooperatives);
                } else {
                    Toast.makeText(context, context.getString(R.string.error_cooperative_list), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.cooperative, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getString(R.string.error_cooperative_list), Toast.LENGTH_SHORT).show();
                progressdialog.dismiss();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.cooperative, false, null, null);
            }
        });
    }

    public void getFarmListVaccine() {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(context.getString(R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.show();

        Call<List<LotListResponse>> lotList = apiInterface.getFarmList(preferenceUtils.getUserId());
        lotList.enqueue(new Callback<List<LotListResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<LotListResponse>> call, @NonNull Response<List<LotListResponse>> response) {

                if (response.isSuccessful()) {
                    List<LotListResponse> lotListResponseList = response.body();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.lotVaccine, true, null, lotListResponseList.size());
                } else {
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.lotVaccine, false, null, null);
                    Toast.makeText(context, context.getString(R.string.error_farm_list), Toast.LENGTH_SHORT).show();
                }

                progressdialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<List<LotListResponse>> call, @NonNull Throwable t) {
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.lotVaccine, false, null, null);

                Toast.makeText(context, context.getString(R.string.error_farm_list), Toast.LENGTH_SHORT).show();
                progressdialog.dismiss();
            }
        });
    }

    public void getHealthImages(int lotId, String breedName) {
        Call<HealthResponse> healthResponseCall;
        if (lotId == 0)
            healthResponseCall = apiInterface.getHealthImages(preferenceUtils.getUserId(), null, breedName);
        else
            healthResponseCall = apiInterface.getHealthImages(preferenceUtils.getUserId(), lotId+"", null);

        healthResponseCall.enqueue(new Callback<HealthResponse>() {
            @Override
            public void onResponse(@NonNull Call<HealthResponse> call, @NonNull Response<HealthResponse> response) {
                if (response.isSuccessful()) {
                    HealthResponse healthResponse = response.body();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.getHealthImages, true, null, healthResponse);
                } else {
                    Toast.makeText(context, context.getString(R.string.error_cooperative_list), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.getHealthImages, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<HealthResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getString(R.string.error_cooperative_list), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.getHealthImages, false, null, null);
            }
        });
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

    public void updateVetDetails(String languageList, String animalList, String govtIdNumber, String registrationNumber,
                                 String degreeDetail, String specializeDetail, String panCard, String bankAccount,
                                 String ifscCode, byte[] govtIdImg1, byte[] govtIdImg2, byte[] registrationImg, byte[] degreeImg, byte[] specializeImg) {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(context.getString(R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.show();

        long imagename = System.currentTimeMillis();

        MultipartBody.Part requestGovtIdImage1 = null;
        MultipartBody.Part requestGovtImage2 = null;
        MultipartBody.Part requestRegistrationImage = null;
        MultipartBody.Part requestDegreeImage = null;
        MultipartBody.Part requestSpecializeImage = null;
        RequestBody requestSpecializeDetail = null;

        RequestBody requestLanguageList = RequestBody.create(languageList, MediaType.parse("multipart/form-data"));
        RequestBody requestAnimalList = RequestBody.create(animalList, MediaType.parse("multipart/form-data"));
        RequestBody requestGovtIdNumber = RequestBody.create(govtIdNumber, MediaType.parse("multipart/form-data"));
        RequestBody requestRegistrationNumber = RequestBody.create(registrationNumber, MediaType.parse("multipart/form-data"));
        RequestBody requestDegreeDetail = RequestBody.create(degreeDetail, MediaType.parse("multipart/form-data"));
        RequestBody requestPanCard = RequestBody.create(panCard, MediaType.parse("multipart/form-data"));
        RequestBody requestBankAccount = RequestBody.create(bankAccount, MediaType.parse("multipart/form-data"));
        RequestBody requestIfscCode = RequestBody.create(ifscCode, MediaType.parse("multipart/form-data"));

        if (specializeDetail.length() != 0)
            requestSpecializeDetail = RequestBody.create(specializeDetail, MediaType.parse("multipart/form-data"));

        if (govtIdImg1 != null){
            RequestBody requestGovtImg1Body = RequestBody.create(govtIdImg1, MediaType.parse("multipart/form-data"));
            requestGovtIdImage1 = MultipartBody.Part.createFormData("vet_goverment_id_image_first", imagename + ".png", requestGovtImg1Body);
        }

        if (govtIdImg2 != null) {
            RequestBody requestGovtImg2Body = RequestBody.create(govtIdImg2, MediaType.parse("multipart/form-data"));
            requestGovtImage2 = MultipartBody.Part.createFormData("vet_goverment_id_image_second", imagename + ".png", requestGovtImg2Body);
        }

        if (registrationImg != null) {
            RequestBody requestRegistrationImgBody = RequestBody.create(registrationImg, MediaType.parse("multipart/form-data"));
            requestRegistrationImage = MultipartBody.Part.createFormData("vet_medical_registration_cert", imagename + ".png", requestRegistrationImgBody);
        }

        if (degreeImg != null) {
            RequestBody requestDegreeImageBody = RequestBody.create(degreeImg, MediaType.parse("multipart/form-data"));
            requestDegreeImage = MultipartBody.Part.createFormData("vet_degree", imagename + ".png", requestDegreeImageBody);
        }

        if (specializeImg != null){
            RequestBody requestSpecializeImageBody = RequestBody.create(specializeImg, MediaType.parse("multipart/form-data"));
            requestSpecializeImage = MultipartBody.Part.createFormData("vet_specialization", imagename + ".png", requestSpecializeImageBody);
        }

        Call<UpdateResponse> updateResponseCall = apiInterface.updateVetDetails(preferenceUtils.getUserId(),
                requestLanguageList, requestAnimalList, requestPanCard, requestBankAccount, requestIfscCode,
                requestGovtIdNumber, requestRegistrationNumber, requestDegreeDetail, requestSpecializeDetail,
                requestGovtIdImage1, requestGovtImage2, requestRegistrationImage, requestDegreeImage, requestSpecializeImage);

        updateResponseCall.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(@NonNull Call<UpdateResponse> call, @NonNull Response<UpdateResponse> response) {
                if (response.isSuccessful()) {
                    UpdateResponse updateResponse = response.body();
                    if (updateResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateVetDetails, true, null, null);
                    else
                        Toast.makeText(context, updateResponse.getMessage()+"", Toast.LENGTH_SHORT).show();
                } else
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateVetDetails, false, null, null);
                progressdialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<UpdateResponse> call, @NonNull Throwable t) {
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateVetDetails, false, t, null);
                progressdialog.dismiss();
            }
        });
    }

    public void getVetDashboardDetails() {
        Call<VetDashboardResponse> vetDashboardResponseCall = apiInterface.getVetDashboardData(preferenceUtils.getUserId());
        vetDashboardResponseCall.enqueue(new Callback<VetDashboardResponse>() {
            @Override
            public void onResponse(@NonNull Call<VetDashboardResponse> call, @NonNull Response<VetDashboardResponse> response) {
                if (response.isSuccessful()) {
                    VetDashboardResponse vetDashboardResponse = response.body();
                    if (vetDashboardResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.vetDashboardData, true, null, vetDashboardResponse.getData());
                    else {
                        Toast.makeText(context, vetDashboardResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.vetDashboardData, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_get_vet_dashboard), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.vetDashboardData, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<VetDashboardResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_get_vet_dashboard), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.vetDashboardData, false, null, null);
            }
        });
    }

    public void updateVetStatus(String status) {
        Call<UpdateResponse> updateResponseCall = apiInterface.updateVetStatus(preferenceUtils.getUserId(), status);
        updateResponseCall.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(@NonNull Call<UpdateResponse> call, @NonNull Response<UpdateResponse> response) {
                if (response.isSuccessful()) {
                    UpdateResponse updateResponse = response.body();
                    if (updateResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateVetStatus, true, null, null);
                    else {
                        if (updateResponse.getData().getUserBio() > 1) {
                            Toast.makeText(context, updateResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                            networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateVetStatus, false, null, null);
                        } else
                            networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateVetStatus, true, null, true);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_update_vet_details), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateVetStatus, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_update_vet_details), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateVetStatus, false, null, null);
            }
        });
    }

    public void getAnimalList() {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(context.getString(R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.show();

        Call<AnimalDataResponse> animalDataResponseCall = apiInterface.getAnimals(preferenceUtils.getUserId());
        animalDataResponseCall.enqueue(new Callback<AnimalDataResponse>() {
            @Override
            public void onResponse(@NonNull Call<AnimalDataResponse> call, @NonNull Response<AnimalDataResponse> response) {
                progressdialog.dismiss();
                if (response.isSuccessful()) {
                    AnimalDataResponse animalDataResponse = response.body();
                    if (animalDataResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.getAnimals, true, null, animalDataResponse.getData());
                    else
                        Toast.makeText(context, animalDataResponse.getMessage()+"", Toast.LENGTH_SHORT).show();
                } else
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.getAnimals, false, null, null);
            }

            @Override
            public void onFailure(@NonNull Call<AnimalDataResponse> call, @NonNull Throwable t) {
                progressdialog.dismiss();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.getAnimals, false, null, null);
            }
        });
    }

    public void getVetList(String lotId, String farmName, String breedName) {
        progressdialog = new ProgressDialog(context);
        progressdialog.setMessage(context.getString(R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.show();

        Call<VetListResponse> vetListResponseCall;
        if (farmName.length() == 0)
            vetListResponseCall = apiInterface.getVetList(preferenceUtils.getUserId(), null, breedName);
        else
            vetListResponseCall = apiInterface.getVetList(preferenceUtils.getUserId(), lotId, null);

        vetListResponseCall.enqueue(new Callback<VetListResponse>() {
            @Override
            public void onResponse(@NonNull Call<VetListResponse> call, @NonNull Response<VetListResponse> response) {
                progressdialog.dismiss();

                if (response.isSuccessful()) {
                    VetListResponse vetListResponse = response.body();
                    if (vetListResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.getVetList, true, null, vetListResponse.getData());
                    else
                        Toast.makeText(context, vetListResponse.getMessage()+"", Toast.LENGTH_SHORT).show();
                } else
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.getVetList, false, null, null);
            }

            @Override
            public void onFailure(@NonNull Call<VetListResponse> call, @NonNull Throwable t) {
                progressdialog.dismiss();
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

    public void getCallHistoryList(Boolean adminUserSearch) {
        Call<CallHistoryListResponse> callHistoryListResponseCall = apiInterface.getCallHistoryList(getUserId(adminUserSearch));
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

    private String getUserId(Boolean adminUserSearch) {
        if (preferenceUtils.getUserRole() == 3) {
            if (adminUserSearch)
                return preferenceUtils.getUserIdForAdmin();
            else
                return preferenceUtils.getUserId();
        } else
            return preferenceUtils.getUserId();
    }

    public void getCallDetail(String callId, Boolean adminUserSearch) {
        Call<CallDetailResponse> callDetailResponseCall = apiInterface.getCallDetail(getUserId(adminUserSearch), callId);
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

    public void updateProfilePicture(byte[] profileImgByte) {
        long imageName = System.currentTimeMillis();

        RequestBody requestBody = RequestBody.create(profileImgByte, MediaType.parse("multipart/form-data"));
        MultipartBody.Part requestProfileImg = MultipartBody.Part.createFormData("profile_picture", imageName + ".jpg", requestBody);

        Call<BaseResponse> baseResponseCall = apiInterface.updateProfileImg(preferenceUtils.getUserId(), requestProfileImg);
        baseResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateProfilePicture, true, null, null);
                    else {
                        Toast.makeText(context, baseResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateProfilePicture, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_update_profile_picture), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateProfilePicture, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_update_profile_picture), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateProfilePicture, false, t, null);
            }
        });
    }

    public void getMortalityData(String farmId) {
        Call<MortalityResponse> mortalityResponseCall = apiInterface.getMortalityData(preferenceUtils.getUserId(), farmId);
        mortalityResponseCall.enqueue(new Callback<MortalityResponse>() {
            @Override
            public void onResponse(@NonNull Call<MortalityResponse> call, @NonNull Response<MortalityResponse> response) {
                if (response.isSuccessful()) {
                    MortalityResponse mortalityResponse = response.body();
                    if (mortalityResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.getMortalityData, true, null, mortalityResponse.getData());
                    else {
                        Toast.makeText(context, mortalityResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.getMortalityData, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_get_mortality_data), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.getMortalityData, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MortalityResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_get_mortality_data), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.getMortalityData, false, null, null);
            }
        });
    }

    public void getDiseaseList(String farmId) {
        Call<DiseaseListResponse> diseaseListResponseCall = apiInterface.getDiseaseList(preferenceUtils.getUserId(), farmId);
        diseaseListResponseCall.enqueue(new Callback<DiseaseListResponse>() {
            @Override
            public void onResponse(@NonNull Call<DiseaseListResponse> call, @NonNull Response<DiseaseListResponse> response) {
                if (response.isSuccessful()) {
                    DiseaseListResponse diseaseListResponse = response.body();
                    if (diseaseListResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.getDiseaseList, true, null, diseaseListResponse.getData());
                    else {
                        Toast.makeText(context, diseaseListResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.getDiseaseList, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_get_disease_list), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.getDiseaseList, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DiseaseListResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_get_disease_list), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.getDiseaseList, false, null, null);
            }
        });
    }

    public void addMortalityData(String farmId, String date, String diedCount, String culledCount, String reasonMortality, String reasonCulled, byte[] animalImgByte, byte[] animalAnotherImgByte) {
        long imagename = System.currentTimeMillis();

        MultipartBody.Part partAdditionalImg = null;

        RequestBody requestfarmId = RequestBody.create(farmId, MediaType.parse("multipart/form-data"));
        RequestBody requestdate = RequestBody.create(date, MediaType.parse("multipart/form-data"));
        RequestBody requestDiedCount = RequestBody.create(diedCount, MediaType.parse("multipart/form-data"));
        RequestBody requestCulledCount = RequestBody.create(culledCount, MediaType.parse("multipart/form-data"));
        RequestBody requestReasonMortality = RequestBody.create(reasonMortality, MediaType.parse("multipart/form-data"));
        RequestBody requestReasonCulled = RequestBody.create(reasonCulled, MediaType.parse("multipart/form-data"));

        RequestBody requestAnimalImg = RequestBody.create(animalImgByte, MediaType.parse("multipart/form-data"));
        MultipartBody.Part partAnimalImg = MultipartBody.Part.createFormData("image", imagename + ".png", requestAnimalImg);

        if (animalAnotherImgByte != null){
            RequestBody requestAdditionalImg = RequestBody.create(animalAnotherImgByte, MediaType.parse("multipart/form-data"));
            partAdditionalImg = MultipartBody.Part.createFormData("optional_image", imagename + ".png", requestAdditionalImg);
        }

        Call<BaseResponse> baseResponseCall = apiInterface.addMortalityData(preferenceUtils.getUserId(), requestfarmId, requestdate, requestDiedCount, requestCulledCount,
                                                                            requestReasonMortality, requestReasonCulled, partAnimalImg, partAdditionalImg);
        baseResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.addMortalityData, true, null, null);
                    else {
                        Toast.makeText(context, baseResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.addMortalityData, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_add_mortality), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.addMortalityData, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_add_mortality), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.addMortalityData, false, t, null);
            }
        });
    }

    public void updateOnboardingStatus() {
        Call<BaseResponse> baseResponseCall = apiInterface.updateOnboardingStatus(preferenceUtils.getUserId(), "True");
        baseResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateOnboardingStatus, true, null, null);
                    else {
                        Toast.makeText(context, baseResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateOnboardingStatus, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_update_onboarding_status), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateOnboardingStatus, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_update_onboarding_status), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateOnboardingStatus, false, null, null);
            }
        });
    }

    public void rejectCall(String callId, String notificationId, String callInitiated) {
        String userRole = "";
        if (preferenceUtils.getUserRole() == 0 || preferenceUtils.getUserRole() == 1)
            userRole = "farmer";
        else
            userRole = "vet";

        Call<BaseResponse> baseResponseCall = apiInterface.rejectCall(preferenceUtils.getUserId(), callId, notificationId, userRole, callInitiated);
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

    public void getProduceData(int farmId) {
        if (farmId == 0){
            Toast.makeText(context, context.getResources().getString(R.string.error_get_produce_data), Toast.LENGTH_SHORT).show();
            networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchProduceData, false, null, null);
            return;
        }

        Call<ProductionResponse> productionResponseCall = apiInterface.getProduceData(preferenceUtils.getUserIdLot(), farmId);
        productionResponseCall.enqueue(new Callback<ProductionResponse>() {
            @Override
            public void onResponse(Call<ProductionResponse> call, Response<ProductionResponse> response) {
                if (response.isSuccessful()) {
                    ProductionResponse productionResponse = response.body();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchProduceData, true, null, productionResponse);
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_get_produce_data), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchProduceData, false, null, null);
                }
            }

            @Override
            public void onFailure(Call<ProductionResponse> call, Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_get_produce_data), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchProduceData, false, null, null);
            }
        });
    }

    public void getFeedData(int farmId) {
        if (farmId == 0){
            Toast.makeText(context, context.getResources().getString(R.string.error_get_feed_data), Toast.LENGTH_SHORT).show();
            networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchFeedData, false, null, null);
            return;
        }

        Call<FeedResponse> feedResponseCall = apiInterface.getFeedData(preferenceUtils.getUserIdLot(), farmId);
        feedResponseCall.enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                if (response.isSuccessful()) {
                    FeedResponse feedResponse = response.body();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchFeedData, true, null, feedResponse);
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_get_feed_data), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchFeedData, false, null, null);
                }
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_get_feed_data), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchFeedData, false, null, null);
            }
        });
    }

    public void updateProduceData(int farmId, int produceQuantity,  String produceDate) {
        UpdateProduceRequest updateProduceRequest = new UpdateProduceRequest();
        updateProduceRequest.setLot_id(farmId);
        updateProduceRequest.setQuantity(produceQuantity);
        updateProduceRequest.setDate(produceDate);

        Call<AddProductionResponse> addProductionResponseCall = apiInterface.addProduce(preferenceUtils.getUserIdLot(), updateProduceRequest);
        addProductionResponseCall.enqueue(new Callback<AddProductionResponse>() {
            @Override
            public void onResponse(Call<AddProductionResponse> call, Response<AddProductionResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, context.getResources().getString(R.string.updated_successfully), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateProduceAdd, true, null, farmId);
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_update_produce), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateProduceAdd, false, null, null);
                }
            }

            @Override
            public void onFailure(Call<AddProductionResponse> call, Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_update_produce), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateProduceAdd, false, null, null);
            }
        });
    }

    public void updateFeedData(int farmId, int feedUsedQuantity,  String feedUsedDate) {
        UpdateFeedRequest updateFeedRequest = new UpdateFeedRequest();
        updateFeedRequest.setLot_id(farmId);
        updateFeedRequest.setFeed(feedUsedQuantity);
        updateFeedRequest.setDate(feedUsedDate);

        Call<UpdateFeedResponse> updateFeedResponseCall = apiInterface.updateFeed(preferenceUtils.getUserIdLot(), updateFeedRequest);
        updateFeedResponseCall.enqueue(new Callback<UpdateFeedResponse>() {
            @Override
            public void onResponse(Call<UpdateFeedResponse> call, Response<UpdateFeedResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, context.getResources().getString(R.string.feed_updated), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateFeedUsed, true, null, farmId);
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_update_feed), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateFeedUsed, false, null, null);
                }
            }

            @Override
            public void onFailure(Call<UpdateFeedResponse> call, Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_update_feed), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateFeedUsed, false, null, null);
            }
        });
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void fetchBalance(Boolean adminUserSearch) {
        Call<WalletResponse> walletResponseCall = apiInterface.fetchBalance(getUserId(adminUserSearch));
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

    public void fetchWalletTransaction(Boolean adminUserSearch) {
        Call<WalletTransactionResponse> walletTransactionResponseCall = apiInterface.fetchWalletTransaction(getUserId(adminUserSearch));
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
    public void updateVetSchedule(String timing) {
        Call<BaseResponse> baseResponseCall = apiInterface.updateVetSchedule(preferenceUtils.getUserId(), timing);
        baseResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateVetSchedule, true, null, null);
                    else {
                        Toast.makeText(context, baseResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateVetSchedule, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_update_onboarding_status), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateVetSchedule, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_update_onboarding_status), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.updateVetSchedule, false, null, null);
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

    public void getUserList(String input) {
        Call<UserSearchResponse> userSearchResponseCall = apiInterface.getUserSearchList(preferenceUtils.getUserId(), input);
        userSearchResponseCall.enqueue(new Callback<UserSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserSearchResponse> call, @NonNull Response<UserSearchResponse> response) {
                if (response.isSuccessful()) {
                    UserSearchResponse userSearchResponse = response.body();
                    if (userSearchResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.getUserList, true, null, userSearchResponse.getData());
                    else {
                        Toast.makeText(context, userSearchResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.getUserList, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_user_search_list), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.getUserList, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserSearchResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_user_search_list), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.getUserList, false, null, null);
            }
        });
    }

    public void getUserDetail(int id) {
        Call<UserDetailResponse> userDetailResponseCall = apiInterface.getUserDetail(preferenceUtils.getUserId(), id);
        userDetailResponseCall.enqueue(new Callback<UserDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserDetailResponse> call, @NonNull Response<UserDetailResponse> response) {
                if (response.isSuccessful()) {
                    UserDetailResponse userDetailResponse = response.body();
                    if (userDetailResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.getUserDetail, true, null, userDetailResponse.getData());
                    else {
                        Toast.makeText(context, userDetailResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.getUserDetail, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_get_user_detail), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.getUserDetail, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserDetailResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_get_user_detail), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.getUserDetail, false, null, null);
            }
        });
    }

    public void getTransactionTypeList() {
        Call<TransactionTypeResponse> transactionTypeResponseCall = apiInterface.getTransactionTypeList();
        transactionTypeResponseCall.enqueue(new Callback<TransactionTypeResponse>() {
            @Override
            public void onResponse(@NonNull Call<TransactionTypeResponse> call, @NonNull Response<TransactionTypeResponse> response) {
                if (response.isSuccessful()) {
                    TransactionTypeResponse transactionTypeResponse = response.body();
                    if (transactionTypeResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.getTransactionTypeList, true, null, transactionTypeResponse.getData());
                    else {
                        Toast.makeText(context, transactionTypeResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.getTransactionTypeList, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_transaction_type_list), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.getTransactionTypeList, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TransactionTypeResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_transaction_type_list), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.getTransactionTypeList, false, null, null);
            }
        });
    }

    public void makeTransaction(int id, String referenceNo, int amount, int transactionType, String remarks) {
        Call<BaseResponse> baseResponseCall = apiInterface.createTransactionByAdmin(preferenceUtils.getUserId(), id, referenceNo, amount, transactionType, remarks);
        baseResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.makeTransaction, true, null, null);
                    else {
                        Toast.makeText(context, baseResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.makeTransaction, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_make_transaction_by_admin), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.makeTransaction, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_make_transaction_by_admin), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.makeTransaction, false, null, null);
            }
        });
    }

    public void callUserById(int userId) {
        Call<StartCallResponse> startCallResponseCall = apiInterface.callUserById(preferenceUtils.getUserId(), userId);
        startCallResponseCall.enqueue(new Callback<StartCallResponse>() {
            @Override
            public void onResponse(@NonNull Call<StartCallResponse> call, @NonNull Response<StartCallResponse> response) {
                if (response.isSuccessful()) {
                    StartCallResponse startCallResponse = response.body();
                    if (startCallResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.callUserById, true, null, startCallResponse.getData());
                    else {
                        Toast.makeText(context, startCallResponse.getMessage() + "", Toast.LENGTH_LONG).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.callUserById, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_call_user_by_id), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.callUserById, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<StartCallResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_call_user_by_id), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.callUserById, false, null, null);
            }
        });
    }

    public void fetchPostRepliesById(int postId) {
        Call<FetchPostsResponse> fetchPostsResponseCall = apiInterface.fetchPostReplies(preferenceUtils.getUserId(), postId);
        fetchPostsResponseCall.enqueue(new Callback<FetchPostsResponse>() {
            @Override
            public void onResponse(@NonNull Call<FetchPostsResponse> call, @NonNull Response<FetchPostsResponse> response) {
                if (response.isSuccessful()) {
                    FetchPostsResponse fetchPostsResponse = response.body();
                    if (fetchPostsResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchPostReplies, true, null, fetchPostsResponse.getData());
                    else {
                        Toast.makeText(context, fetchPostsResponse.getMessage() + "", Toast.LENGTH_LONG).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchPostReplies, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_fetch_post_replies), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchPostReplies, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FetchPostsResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_fetch_post_replies), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchPostReplies, false, null, null);
            }
        });
    }

    public void fetchAddaPosts() {
        Call<FetchPostsResponse> fetchPostsResponseCall = apiInterface.fetchAddaPosts(preferenceUtils.getUserId());
        fetchPostsResponseCall.enqueue(new Callback<FetchPostsResponse>() {
            @Override
            public void onResponse(@NonNull Call<FetchPostsResponse> call, @NonNull Response<FetchPostsResponse> response) {
                if (response.isSuccessful()) {
                    FetchPostsResponse fetchPostsResponse = response.body();
                    if (fetchPostsResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchAddaPosts, true, null, fetchPostsResponse.getData());
                    else {
                        Toast.makeText(context, fetchPostsResponse.getMessage() + "", Toast.LENGTH_LONG).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchAddaPosts, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_fetch_adda_posts), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchAddaPosts, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FetchPostsResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_fetch_adda_posts), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.fetchAddaPosts, false, null, null);
            }
        });
    }

    public void createReply(String message, int postId) {
        Call<BaseResponse> baseResponseCall = apiInterface.createReply(preferenceUtils.getUserId(), message, postId);
        baseResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.createReply, true, null, null);
                    else {
                        Toast.makeText(context, baseResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.createReply, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_create_reply), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.createReply, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_create_reply), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.createReply, false, null, null);
            }
        });
    }

    public void postAction(PostActionRequest postActionRequest) {
        Call<BaseResponse> baseResponseCall = apiInterface.postAction(preferenceUtils.getUserId(), postActionRequest.getPostId(), postActionRequest.getAction(), postActionRequest.getActionStatus());
        baseResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.postAction, true, null, postActionRequest);
                    else {
                        Toast.makeText(context, baseResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.postAction, false, null, postActionRequest);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_post_action), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.postAction, false, null, postActionRequest);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_post_action), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.postAction, false, null, postActionRequest);
            }
        });
    }

    public void createPost(String title, String message, byte[] image, File video) {
        long fileName = System.currentTimeMillis();

        MultipartBody.Part partImage = null;
        MultipartBody.Part partVideo = null;

        RequestBody requestTitle = RequestBody.create(title, MediaType.parse("multipart/form-data"));
        RequestBody requestMessage = RequestBody.create(message, MediaType.parse("multipart/form-data"));

        if (image != null) {
            RequestBody requestImage = RequestBody.create(image, MediaType.parse("multipart/form-data"));
            partImage = MultipartBody.Part.createFormData("image", fileName + ".png", requestImage);
        }

        if (video != null) {
            RequestBody requestVideo = RequestBody.create(video, MediaType.parse("multipart/form-data"));
            partVideo = MultipartBody.Part.createFormData("video", fileName + ".mp4", requestVideo);
        }


        Call<BaseResponse> baseResponseCall = apiInterface.createPost(preferenceUtils.getUserId(), requestTitle, requestMessage, partImage, partVideo);
        baseResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.createPost, true, null, null);
                    else {
                        Toast.makeText(context, baseResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.createPost, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_create_post), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.createPost, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_create_post), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.createPost, false, null, null);
            }
        });
    }

    public void searchAddaPosts(String search) {
        Call<FetchPostsResponse> fetchPostsResponseCall = apiInterface.postSearch(preferenceUtils.getUserId(), search);
        fetchPostsResponseCall.enqueue(new Callback<FetchPostsResponse>() {
            @Override
            public void onResponse(@NonNull Call<FetchPostsResponse> call, @NonNull Response<FetchPostsResponse> response) {
                if (response.isSuccessful()) {
                    FetchPostsResponse fetchPostsResponse = response.body();
                    if (fetchPostsResponse.getStatus())
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.searchAddaPosts, true, null, fetchPostsResponse.getData());
                    else {
                        Toast.makeText(context, fetchPostsResponse.getMessage() + "", Toast.LENGTH_LONG).show();
                        networkingInterface.networkingRequest(NetworkingInterface.MethodType.searchAddaPosts, false, null, null);
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.error_search_adda_posts), Toast.LENGTH_SHORT).show();
                    networkingInterface.networkingRequest(NetworkingInterface.MethodType.searchAddaPosts, false, null, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FetchPostsResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_search_adda_posts), Toast.LENGTH_SHORT).show();
                networkingInterface.networkingRequest(NetworkingInterface.MethodType.searchAddaPosts, false, null, null);
            }
        });
    }
}