package com.drpashu.sdk;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.drpashu.sdk.adapter.SelectAnimalDetailAdapter.breedName;
import static com.drpashu.sdk.adapter.SelectAnimalDetailAdapter.breedNameByLanguage;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.drpashu.sdk.adapter.SelectAnimalAdapter;
import com.drpashu.sdk.adapter.VetListAdapter;
import com.drpashu.sdk.databinding.FragmentConsultDoctorBinding;
import com.drpashu.sdk.dialog.CallConnectFailedDialog;
import com.drpashu.sdk.dialog.FreeCallActionInterface;
import com.drpashu.sdk.dialog.FreeCallDialog;
import com.drpashu.sdk.dialog.PaymentFailedDialog;
import com.drpashu.sdk.network.NetworkingInterface;
import com.drpashu.sdk.network.model.response.AnimalListResponse;
import com.drpashu.sdk.network.model.response.RazorpayOrderIdResponse;
import com.drpashu.sdk.network.model.response.StartCallResponse;
import com.drpashu.sdk.network.model.response.VetListResponse;
import com.razorpay.Checkout;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ConsultDoctorFragment extends BaseFragment implements NetworkingInterface, FreeCallActionInterface {
    private Boolean doneAnalysis = false, companySelected = false, freeCall = false;
    private FragmentConsultDoctorBinding binding;
    private String farmId = "", animalType = "", animalTypeByLanguage= "", farmName="", vetCategory = "", companyName = "", groupId = "", paymentId = "";
    private int amount = 0, mrpAmount = 0, addMoneyAmount = 0;
    private ProgressDialog progressDialog;
    private View view1;
    private static final String[] REQUESTED_PERMISSIONS = {Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA};
    private static final int PERMISSION_REQ_ID_AUDIO = 1;
    private static final int PERMISSION_REQ_ID_CAMERA = 2;
    private Checkout checkout;
    private LocalBroadcastManager localBroadcastManager;
    private VetListResponse.Data vetListResponse;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        localBroadcastManager = LocalBroadcastManager.getInstance(context);

        IntentFilter paymentResultIntent = new IntentFilter();
        paymentResultIntent.addAction("payment_result");
        localBroadcastManager.registerReceiver(paymentResultBroadcastReceiver , paymentResultIntent);
    }

    private final BroadcastReceiver paymentResultBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (intent.getExtras().getBoolean("paymentStatus")) {
                    paymentId = intent.getExtras().getString("paymentId");
                    Toast.makeText(context, "Payment Successful. Adding coins to your wallet.", Toast.LENGTH_SHORT).show();

                    utils.updateErrorEvent("Payment Step 6", "Amount- "+ addMoneyAmount + ", Payment Success - " + paymentId);

                    progressDialog.show();
                    networking.addCoinsToWallet(addMoneyAmount+"", paymentId);
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.payment_unsuccessful), Toast.LENGTH_SHORT).show();

                    String errorMessage = intent.getExtras().getString("errorDetail");
                    errorMessage = errorMessage.replace(",", " ");
                    utils.updateErrorEvent("Payment Step 6 - Consult Doctor Payment Failed Event", "Amount- "+ addMoneyAmount + " " + errorMessage);

//                    showPaymentFailure();
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            farmId = getArguments().getString("farmId");
            farmName = getArguments().getString("farmName");
            animalType = getArguments().getString("animalType");
            animalTypeByLanguage = getArguments().getString("animalTypeByLanguage");
            doneAnalysis = getArguments().getBoolean("analysis");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConsultDoctorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        activity.updateTooblar(ContextCompat.getDrawable(getActivity(), R.drawable.ic_consult_doctor), true);

        Checkout.preload(activity.getApplicationContext());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getContext().getString(R.string.loading));
        progressDialog.setCancelable(false);
        view1 = view;

        checkout = new Checkout();
        checkout.setKeyID(getString(R.string.razorpay_key_id));

        networking.getAnimals();

        binding.proceedBtn.setOnClickListener(v -> {
/*            if (breedName.length() == 0)
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.error_select_animal), Toast.LENGTH_SHORT).show();
            else {
                animalType = breedName;
                animalTypeByLanguage = breedNameByLanguage;
                networking.getVetList(farmId, farmName, breedName);
            }*/
        });

        binding.companyView.setOnClickListener(v -> {
            vetCategory = "company";
            companySelected = true;
            binding.companyRecyclerview.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_card));
            binding.recyclerviewOpd.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            binding.recyclerviewSpecialist.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            binding.governmentRecyclerview.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            binding.familyRecyclerview.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

            mrpAmount = vetListResponse.getCategoriesList().getCompanyMrp();
            amount = vetListResponse.getCategoriesList().getCompanyOfferPrice();
//            setValueText();
        });

        binding.opdView.setOnClickListener(v -> {
            vetCategory= "OPD";
            companySelected = false;
            binding.recyclerviewOpd.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_card));
            binding.companyRecyclerview.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            binding.recyclerviewSpecialist.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            binding.governmentRecyclerview.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            binding.familyRecyclerview.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

            mrpAmount = vetListResponse.getCategoriesList().getOPDMrp();
            amount = vetListResponse.getCategoriesList().getOPDOfferPrice();
//            setValueText();
        });

        binding.specialistView.setOnClickListener(v -> {
            vetCategory = "specialist";
            companySelected = false;
            binding.recyclerviewSpecialist.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_card));
            binding.companyRecyclerview.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            binding.recyclerviewOpd.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            binding.governmentRecyclerview.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            binding.familyRecyclerview.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

            mrpAmount = vetListResponse.getCategoriesList().getSpecialistMrp();
            amount = vetListResponse.getCategoriesList().getSpecialistOfferPrice();
//            setValueText();
        });

        binding.governmentView.setOnClickListener(v -> {
            vetCategory = "government";
            companySelected = false;
            binding.governmentRecyclerview.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_card));
            binding.companyRecyclerview.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            binding.recyclerviewOpd.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            binding.recyclerviewSpecialist.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            binding.familyRecyclerview.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

            mrpAmount = vetListResponse.getCategoriesList().getGovernmentMrp();
            amount = vetListResponse.getCategoriesList().getGovernmentOfferPrice();
//            setValueText();
        });

        binding.familyView.setOnClickListener(v -> {
            vetCategory = "family";
            companySelected = false;
            binding.familyRecyclerview.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_card));
            binding.companyRecyclerview.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            binding.recyclerviewOpd.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            binding.recyclerviewSpecialist.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            binding.governmentRecyclerview.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

            mrpAmount = vetListResponse.getCategoriesList().getFamilyMrp();
            amount = vetListResponse.getCategoriesList().getFamilyOfferPrice();
//            setValueText();
        });

        binding.selectionBtn.setOnClickListener(v -> {
//            activity.updateFirebaseEvents("button_click", "start_call_button");
            if (vetCategory.length() == 0)
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.please_select_vet_type), Toast.LENGTH_SHORT).show();
            else {
                if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID_AUDIO) && checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID_CAMERA)) {
                    if (amount == 0) {
                        freeCall =  true;
                        showFreeCallDialog(mrpAmount, amount, 0);
                    }
                    else {
                        progressDialog.show();
                        networking.fetchBalance(false);
                    }
                }
            }
        });
    }

    private void showFreeCallDialog(int callMrp, int offerPrice, int userCoinBalance) {
        FreeCallDialog freeCallDialog = new FreeCallDialog(getContext(), activity, callMrp, offerPrice, userCoinBalance, this);
        freeCallDialog.setCancelable(false);
        freeCallDialog.show();
        freeCallDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void showCallNotConnectedDialog(String message) {
        CallConnectFailedDialog callConnectFailedDialog = new CallConnectFailedDialog(getContext(), activity, message);
        callConnectFailedDialog.setCancelable(true);
        callConnectFailedDialog.show();
        callConnectFailedDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void showPaymentFailure() {
        PaymentFailedDialog paymentFailedDialog = new PaymentFailedDialog(getContext(), activity);
        paymentFailedDialog.setCancelable(true);
        paymentFailedDialog.show();
        paymentFailedDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void setValueText() {
        String pay = getContext().getResources().getString(R.string.pay);
        String start_your_call = getContext().getResources().getString(R.string.start_your_call);
        String and = getContext().getResources().getString(R.string.and);

        int pay_length = pay.length();

        if (amount == mrpAmount)
            binding.selectionBtn.setText(pay+" ₹"+amount+" "+and +" "+start_your_call);
        else {
            SpannableString spannableString = new SpannableString(pay+" ₹" + mrpAmount + " ₹" + amount +" "+ and +" "+start_your_call);

            if (mrpAmount < 10) {
                spannableString.setSpan(new StrikethroughSpan(), pay_length+1, pay_length+3, 0);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), pay_length+1, pay_length+3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            } else if (mrpAmount >= 10 && mrpAmount < 100) {
                spannableString.setSpan(new StrikethroughSpan(), pay_length+1, pay_length+4, 0);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), pay_length+1, pay_length+4, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            } else if (mrpAmount >= 100 && mrpAmount < 1000) {
                spannableString.setSpan(new StrikethroughSpan(), pay_length+1, pay_length+5, 0);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), pay_length+1, pay_length+5, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            } else {
                spannableString.setSpan(new StrikethroughSpan(), pay_length+1, pay_length+6, 0);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), pay_length+1, pay_length+6, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }

            binding.selectionBtn.setText(spannableString);
        }
    }

    private void initiatePayment(String orderId){
        try {
            JSONObject options = new JSONObject();

            options.put("name", "DrPashu Technologies");
            options.put("description", "24/7 Vet in your Smartphone");
            options.put("order_id", orderId);//from response of step 3.
            options.put("prefill.email", preferenceUtils.getEmail());
            try {
                options.put("prefill.contact",preferenceUtils.getPhoneNumber().substring(3));
            } catch (Exception e){
                Log.e("phone_capture", e.getMessage()+"");
            }
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", false);
            options.put("retry", retryObj);
            checkout.open(activity, options);

            utils.updateErrorEvent("Payment Step 5", "Initiate Payment Gateway Success");
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
            utils.updateErrorEvent("Payment Step 5", "Initiate Payment Gateway Failure- " +  e.getMessage());
        }
    }

    private void setFarmName(){
        if (farmName.length() != 0)
            binding.farmText.setText(farmName);
        else
            binding.farmText.setText(animalTypeByLanguage);
    }

    private boolean checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), REQUESTED_PERMISSIONS, requestCode);
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.allow_permission_message), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initiateCall(){
        progressDialog.show();

        if (companySelected)
            networking.startCall(farmId, animalType, vetCategory, doneAnalysis, companyName, false, getCurrentTime(), freeCall, amount+"", paymentId);
        else
            networking.startCall(farmId, animalType, vetCategory, doneAnalysis, "", false, getCurrentTime(), freeCall, amount +"", paymentId);
    }

    private String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyHHmmss", Locale.ENGLISH);
        Date date = new Date();
        dateFormat.format(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        groupId = dateFormat.format(calendar.getTime());
        Log.e("call_time", groupId);
        return groupId;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        localBroadcastManager.unregisterReceiver(paymentResultBroadcastReceiver);
    }

    @Override
    public <T> void networkingRequest(@Nullable MethodType methodType, boolean status, @Nullable T error, Object o) {
        if (methodType == MethodType.getAnimalList && status) {
            binding.mainLayout.setVisibility(View.GONE);
            binding.selectAnimalLayout.setVisibility(View.VISIBLE);

            breedName = "";
            breedNameByLanguage = "";

            List<AnimalListResponse> animalResponseList = (List<AnimalListResponse>) o;
            SelectAnimalAdapter selectAnimalAdapter = new SelectAnimalAdapter(getContext(), getActivity(), animalResponseList);
            binding.recyclerviewAnimals.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recyclerviewAnimals.setAdapter(selectAnimalAdapter);
        } else if (methodType == MethodType.getVetList && status) {
            binding.selectAnimalLayout.setVisibility(View.GONE);
            binding.mainLayout.setVisibility(View.VISIBLE);
            binding.selectionBtn.setVisibility(View.VISIBLE);

            setFarmName();

            List<VetListResponse.Data.Vet> opdList = new ArrayList<>();
            List<VetListResponse.Data.Vet> specialistList = new ArrayList<>();
            List<VetListResponse.Data.Vet> companyList = new ArrayList<>();
            List<VetListResponse.Data.Vet> governmentList = new ArrayList<>();
            List<VetListResponse.Data.Vet> familyList = new ArrayList<>();

            vetListResponse = (VetListResponse.Data) o;

            for (int i = 0; i < vetListResponse.getVetList().size(); i++) {
                if (vetListResponse.getVetList().get(i).getVetType().equalsIgnoreCase("OPD"))
                    opdList.add(vetListResponse.getVetList().get(i));
                else if (vetListResponse.getVetList().get(i).getVetType().equalsIgnoreCase("specialist"))
                    specialistList.add(vetListResponse.getVetList().get(i));
                else if (vetListResponse.getVetList().get(i).getVetType().equalsIgnoreCase("company"))
                    companyList.add(vetListResponse.getVetList().get(i));
                else if (vetListResponse.getVetList().get(i).getVetType().equalsIgnoreCase("government"))
                    governmentList.add(vetListResponse.getVetList().get(i));
                else if (vetListResponse.getVetList().get(i).getVetType().equalsIgnoreCase("family"))
                    familyList.add(vetListResponse.getVetList().get(i));
            }

            if (opdList.size() != 0) {
                binding.opdCardview.setVisibility(View.VISIBLE);
                VetListAdapter opdListAdapter = new VetListAdapter(getContext(), getActivity(), opdList);
                binding.recyclerviewOpd.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerviewOpd.setAdapter(opdListAdapter);

                binding.opdView.performClick();
            }

            if (specialistList.size() != 0) {
                binding.specialistCardview.setVisibility(View.VISIBLE);
                VetListAdapter specialistListAdapter = new VetListAdapter(getContext(), getActivity(), specialistList);
                binding.recyclerviewSpecialist.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerviewSpecialist.setAdapter(specialistListAdapter);
            }

            if (companyList.size() != 0) {
                binding.companyCardview.setVisibility(View.VISIBLE);
                companyName = companyList.get(0).getCompanyName();
                VetListAdapter companyListAdapter = new VetListAdapter(getContext(), getActivity(), companyList);
                binding.companyRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.companyRecyclerview.setAdapter(companyListAdapter);

                binding.companyView.performClick();
            }

            if (governmentList.size() != 0) {
                binding.governmentCardview.setVisibility(View.VISIBLE);
                VetListAdapter governmentListAdapter = new VetListAdapter(getContext(), getActivity(), governmentList);
                binding.governmentRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.governmentRecyclerview.setAdapter(governmentListAdapter);
            }

            if (familyList.size() != 0) {
                binding.familyCardview.setVisibility(View.VISIBLE);
                VetListAdapter familyListAdapter = new VetListAdapter(getContext(), getActivity(), familyList);
                binding.familyRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.familyRecyclerview.setAdapter(familyListAdapter);
            }

            if (opdList.size() == 0 && specialistList.size() == 0 && companyList.size() == 0 && governmentList.size() == 0 && familyList.size() == 0) {
                binding.selectionBtn.setVisibility(View.GONE);
                binding.noDoctorAvailableText.setVisibility(View.VISIBLE);
            }

            binding.companyPriceText.setText(vetListResponse.getCategoriesList().getCompanyOfferPrice() + " Coins");
            binding.companyMrpText.setText(String.valueOf(vetListResponse.getCategoriesList().getCompanyMrp()));
            binding.companyMrpText.setPaintFlags(binding.companyMrpText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            binding.opdPriceText.setText(vetListResponse.getCategoriesList().getOPDOfferPrice() + " Coins");
            binding.opdMrpText.setText(String.valueOf(vetListResponse.getCategoriesList().getOPDMrp()));
            binding.opdMrpText.setPaintFlags(binding.opdMrpText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            binding.specialistPriceText.setText(vetListResponse.getCategoriesList().getSpecialistOfferPrice() + " Coins");
            binding.specialistMrpText.setText(String.valueOf(vetListResponse.getCategoriesList().getSpecialistMrp()));
            binding.specialistMrpText.setPaintFlags(binding.specialistMrpText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            binding.governmentPriceText.setText(vetListResponse.getCategoriesList().getGovernmentOfferPrice() + " Coins");
            binding.governmentMrpText.setText(String.valueOf(vetListResponse.getCategoriesList().getGovernmentMrp()));
            binding.governmentMrpText.setPaintFlags(binding.governmentMrpText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            binding.familyPriceText.setText(vetListResponse.getCategoriesList().getFamilyOfferPrice() + " Coins");
            binding.familyMrpText.setText(String.valueOf(vetListResponse.getCategoriesList().getFamilyMrp()));
            binding.familyMrpText.setPaintFlags(binding.familyMrpText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else if (methodType == MethodType.getVetList && !status)
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.error_vet_list), Toast.LENGTH_SHORT).show();
        else if (methodType == MethodType.startCall && status) {
            progressDialog.dismiss();
            StartCallResponse.Data startCallDataResponse = (StartCallResponse.Data) o;
            Bundle bundle = new Bundle();
            bundle.putBoolean("callIncoming", false);
            bundle.putBoolean("callRedial", true);
            bundle.putString("callInitiated", "Farmer");
            bundle.putString("callId", startCallDataResponse.getCallId() + "");
            bundle.putString("channelId", startCallDataResponse.getChannel_id());
            bundle.putString("firstName", startCallDataResponse.getFirst_name());
            bundle.putString("lastName", startCallDataResponse.getLast_name());
            bundle.putInt("notificationId", Integer.parseInt(startCallDataResponse.getNotificationId()));

            bundle.putString("groupId", groupId);
            bundle.putString("farmId", farmId);
            bundle.putString("animalType", animalType);
            bundle.putString("animalTypeByLanguage", animalTypeByLanguage);
            bundle.putString("vetCategory", vetCategory);
            bundle.putBoolean("doneAnalysis", doneAnalysis);
            bundle.putString("companyName", companyName);
            bundle.putBoolean("companySelected", companySelected);
            bundle.putString("callAmount", amount + "");
            bundle.putString("paymentId", paymentId);
            bundle.putBoolean("freeCall", freeCall);

            Navigation.findNavController(view1).navigate(R.id.action_nav_consult_doctor_to_incomingCallFragment, bundle);
        } else if (methodType == MethodType.getRazorpayOrderId && status) {
            progressDialog.dismiss();

            RazorpayOrderIdResponse.Data razorpayOrderIdResponse = (RazorpayOrderIdResponse.Data) o;

            utils.updateErrorEvent("Payment Step 4", "Receive Order Id: " + razorpayOrderIdResponse.getId());
            initiatePayment(razorpayOrderIdResponse.getId());
        } else if (methodType == MethodType.fetchBalance && status) {
            progressDialog.dismiss();

            int balance = Integer.parseInt((String) o);
            showFreeCallDialog(mrpAmount, amount, balance);
        } else if (methodType == MethodType.addCoinsToWallet && status) {
            progressDialog.dismiss();
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.payment_success), Toast.LENGTH_SHORT).show();

            utils.updateErrorEvent("Payment Step 7", "Add money Success - " + addMoneyAmount);
            initiateCall();
        } else if (methodType == MethodType.addCoinsToWallet && !status) {
            progressDialog.dismiss();

            utils.updateErrorEvent("Payment Step 7 - Add Money Api Failure Event", "Amount- " + addMoneyAmount + "  Payment Id- " + paymentId + "  " + (String) o);


            showPaymentFailure();
        }
        else if (methodType == MethodType.startCall && !status) {
            progressDialog.dismiss();

            if (error != null) {
                if ((Boolean) error)
                    showCallNotConnectedDialog((String) o);
            }
            utils.updateErrorEvent("Start Call Error Event", "Call Id - " + groupId + " Error Message - " + (String) o);
        } else if (methodType == MethodType.getRazorpayOrderId || methodType == MethodType.fetchBalance || methodType == MethodType.dashboardInfo && !status)
            progressDialog.dismiss();
    }

    @Override
    public void freeCallDialogAction(int method, int paymentGatewayAmount) {
        if (method == 1)
            initiateCall();
        else if (method == 2){
            progressDialog.show();
            freeCall = false;
            addMoneyAmount = paymentGatewayAmount;
            networking.getRazorpayOrderId(addMoneyAmount * 100 + "");

            utils.updateErrorEvent("Payment Step 1", "Create Order Id: " + addMoneyAmount);
        }
    }
}