package com.drpashu.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.drpashu.sdk.databinding.FragmentIncomingCallBinding;
//import com.drpashu.app.dialog.CallConnectFailedDialog;
//import com.drpashu.app.fragments.BaseFragment;
//import com.drpashu.app.network.ApiClient;
//import com.drpashu.app.network.model.response.StartCallResponse;
import com.squareup.picasso.Picasso;

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;

public class IncomingCallFragment extends BaseFragment {
    private FragmentIncomingCallBinding binding;
    private RtcEngine mRtcEngine;
    private Boolean callIncoming = false, callRedial = false, audioDisabled = false, videoDisabled = false,
            callStarted = false, doneAnalysis = false, companySelected = false, freeCall = false;
    private int notificationId = 0;
    private String callId = "", channelId = "", firstName = "", lastName = "", profileImg ="", groupId = "", farmId = "",
            animalType = "", vetCategory = "", companyName = "", callAmount = "", paymentId = "", unixNotificationTime = "", callInitiated = "";
    private View view1;
    private CountDownTimer countDownTimer;
    private MediaPlayer mediaPlayer;
    private LocalBroadcastManager localBroadcastManager;
    private long countDownTimerValue = 45;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        localBroadcastManager = LocalBroadcastManager.getInstance(context);

        IntentFilter silentIntentForReconnecting = new IntentFilter();
        silentIntentForReconnecting.addAction("vet_call_decline");
        localBroadcastManager.registerReceiver(silentNotificationForReconnecting , silentIntentForReconnecting);

        IntentFilter intentForFarmerCallDecline = new IntentFilter();
        intentForFarmerCallDecline.addAction("missed_call");
        localBroadcastManager.registerReceiver(notificationForFarmerCallDecline , intentForFarmerCallDecline);
    }

    private final BroadcastReceiver silentNotificationForReconnecting = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                countDownTimer.cancel();

                if (callRedial){
                    utils.shortToast(utils.getStringValue(R.string.connecting_to_next_vet));
                    countDownTimer.onFinish();
                } else {
                    utils.shortToast(utils.getStringValue(R.string.user_busy));
                    preferenceUtils.setBlockNavigationStatus(false);
                    activity.onBackPressed();
                }
            }
        }
    };

    private final BroadcastReceiver notificationForFarmerCallDecline = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                utils.shortToast(utils.getStringValue(R.string.farmer_ended_call));
                preferenceUtils.setBlockNavigationStatus(false);
                activity.onBackPressed();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            callIncoming = getArguments().getBoolean("callIncoming");
            callRedial = getArguments().getBoolean("callRedial");
            callInitiated = getArguments().getString("callInitiated");

            callId = getArguments().getString("callId");
            channelId = getArguments().getString("channelId");
            firstName = getArguments().getString("firstName");
            lastName = getArguments().getString("lastName");
            notificationId = getArguments().getInt("notificationId");

            profileImg = getArguments().getString("profile_picture");
            unixNotificationTime = getArguments().getString("unixNotificationTime");

            groupId = getArguments().getString("groupId");
            farmId = getArguments().getString("farmId");
            animalType = getArguments().getString("animalType");
            vetCategory = getArguments().getString("vetCategory");
            companyName = getArguments().getString("companyName");
            doneAnalysis = getArguments().getBoolean("doneAnalysis");
            companySelected = getArguments().getBoolean("companySelected");
            callAmount = getArguments().getString("callAmount");
            paymentId = getArguments().getString("paymentId");
            freeCall = getArguments().getBoolean("freeCall");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentIncomingCallBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
/*

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.updateTooblar(ContextCompat.getDrawable(activity, R.drawable.ic_consult_doctor), false);
        activity.updateFirebaseEvents("navigation", "calling_screen");

        mediaPlayer = MediaPlayer.create(context, R.raw.calling_sound);

        preferenceUtils.setBlockNavigationStatus(true);
        view1 = view;
        Glide.with(context).load(R.raw.phone_ringing).into(new DrawableImageViewTarget(binding.animationView));
//        Glide.with(context).load(R.raw.call_accept).into(new DrawableImageViewTarget(binding.callAcceptImg));

        binding.videoBtn.setEnabled(false);

        if (callIncoming) {
            if (!checkCallTime()){
                NotificationManagerCompat.from(activity).cancel(null, notificationId);
                utils.shortToast(utils.getStringValue(R.string.call_already_completed));
                preferenceUtils.setBlockNavigationStatus(false);
                activity.onBackPressed();
            } else {
                utils.hideView(binding.mainLayout);
                utils.visibleView(binding.callLayout);
                binding.userText.setText(firstName + " " + lastName);
                if (profileImg.length() != 0)
                    Picasso.get().load(ApiClient.BASE_URL_MEDIA + profileImg).into(binding.userImg);

                startCountDown();
            }
        } else {
            binding.animationLayout.setVisibility(View.VISIBLE);
            mediaPlayer.start();

            if (preferenceUtils.getUserRole() == 2)
                binding.statusText.setText(utils.getStringValue(R.string.animal_owner_will_be_there));
            else
                binding.statusText.setText(utils.getStringValue(R.string.vet_will_be_there));

            initializeCall();
        }

        binding.endCallBtn.setOnClickListener(v -> endCall(v));
        binding.videoBtn.setOnClickListener(v -> onVideoMuteClicked());
        binding.audioBtn.setOnClickListener(v -> onAudioMuteClicked());
        binding.flipCameraBtn.setOnClickListener(v -> mRtcEngine.switchCamera());
        binding.callAcceptText.setOnClickListener(v -> binding.callAcceptImg.performClick());
        binding.callDeclineText.setOnClickListener(v -> binding.callDeclineText.performClick());

        binding.callAcceptImg.setOnClickListener(v -> {
            NotificationManagerCompat.from(activity).cancel(null, notificationId);
            countDownTimer.cancel();

            if (checkCallTime()) {
                activity.updateFirebaseEvents("button_click", "doctor_accept_call");
                initializeCall();
            } else {
                utils.shortToast(utils.getStringValue(R.string.call_already_completed));
                preferenceUtils.setBlockNavigationStatus(false);
                activity.onBackPressed();
            }

        });

        binding.callDeclineImg.setOnClickListener(v -> {
            NotificationManagerCompat.from(activity).cancel(null, notificationId);
            countDownTimer.cancel();

            if (checkCallTime()) {
                activity.updateFirebaseEvents("button_click", "doctor_reject_call");
                showLoading();
                networking.rejectCall(callId, notificationId + "", callInitiated);
            } else {
                utils.shortToast(utils.getStringValue(R.string.call_already_completed));
                preferenceUtils.setBlockNavigationStatus(false);
                activity.onBackPressed();
            }
        });
    }

    private boolean checkCallTime(){
        long currentTime = System.currentTimeMillis();
        long notificationLongTime = Long.parseLong(unixNotificationTime);

        long differece = (currentTime - notificationLongTime)/1000;
        countDownTimerValue = 45 - differece;
        Log.e("timeDifferenceLog", differece + "      "+unixNotificationTime);

        return differece <= 45;
    }

    private void initializeCall() {
        binding.userNameText.setText(utils.getStringValue(R.string.calling) + " - " + firstName + " " + lastName);
        utils.hideView(binding.callLayout);
        utils.visibleView(binding.mainLayout);
        initAgoraEngine();
    }

    private void endCall(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage(utils.getStringValue(R.string.end_call_message))
                .setPositiveButton(utils.getStringValue(R.string.end_call), (dialog, which) -> {
                    if(callStarted) {
                        finishCall(view);
                    } else {
                        showLoading();

                        if (callRedial)
                            countDownTimer.cancel();

                        networking.rejectCall(callId, notificationId + "", callInitiated);
                    }
                })
                .setNegativeButton(utils.getStringValue(R.string.cancel), null);
        alert.show();
    }

    // Handle SDK Events
    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
        @Override
        public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) {
            if (!callStarted) {
                requireActivity().runOnUiThread(() -> {
                    Log.e("videoFirst", "there " + uid);

                    binding.videoBtn.setEnabled(true);
                    utils.hideView(binding.animationLayout);

                    callStarted = true;
                    if (mediaPlayer.isPlaying())
                        mediaPlayer.pause();

                    countDownTimer.cancel();

                    binding.userNameText.setText(utils.getStringValue(R.string.call_started) + " - " + firstName + " " + lastName);
                    setupRemoteVideoStream(uid);
                    networking.updateCallStatus(callId, "Started");
                });
            }
        }

        // remote user has left channel
        @Override
        public void onUserOffline(int uid, int reason) {
            requireActivity().runOnUiThread(() -> {
                binding.incomingVideoLayout.removeAllViews();
                finishCall(view1);
            });
        }

        @Override
        public void onUserMuteAudio(int uid, boolean muted) {
            requireActivity().runOnUiThread(() -> {
                binding.userAudioImg.setVisibility(!muted ? View.GONE : View.VISIBLE);
                binding.userAudioImg.setText(firstName+" "+utils.getStringValue(R.string.muted_call));
            });
        }

        // remote stream has been toggled
        @Override
        public void onUserMuteVideo(final int uid, final boolean toggle) { // Tutorial Step 10
            requireActivity().runOnUiThread(() -> {
                binding.incomingVideoLayout.setVisibility(toggle ? View.GONE : View.VISIBLE);
                binding.videoOffImg.setVisibility(toggle ? View.VISIBLE : View.GONE);
            });
        }
    };

    private void initAgoraEngine() {
        try {
            mRtcEngine = RtcEngine.create(activity.getBaseContext(), utils.getStringValue(R.string.agora_app_id), mRtcEventHandler);
        } catch (Exception e) {
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
        setupSession();
    }

    private void setupSession() {
        mRtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_COMMUNICATION);
        mRtcEngine.enableVideo();
        mRtcEngine.setVideoEncoderConfiguration(new VideoEncoderConfiguration(VideoEncoderConfiguration.VD_640x360,
                                                                              VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
                                                                              VideoEncoderConfiguration.STANDARD_BITRATE,
                                                                              VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT));

        mRtcEngine.joinChannel(null, channelId, "Extra Optional Data", 0); // need to change the s1 variable for multiple channels
        setupLocalVideoFeed();
    }

    private void setupLocalVideoFeed() {
        SurfaceView videoSurface = RtcEngine.CreateRendererView(activity.getBaseContext());
        videoSurface.setZOrderMediaOverlay(true);
        binding.selfVideoLayout.addView(videoSurface);
        mRtcEngine.setupLocalVideo(new VideoCanvas(videoSurface, VideoCanvas.RENDER_MODE_FIT, 0));

        if (!callIncoming)
            startCountDown();
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(countDownTimerValue*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {

                if (callIncoming) {
                    NotificationManagerCompat.from(activity).cancel(null, notificationId);

                    utils.shortToast(utils.getStringValue(R.string.call_already_completed));
                    preferenceUtils.setBlockNavigationStatus(false);
                    activity.onBackPressed();
                } else {
                    if (callRedial) {
                        if (mediaPlayer.isPlaying())
                            mediaPlayer.pause();
                        mRtcEngine.leaveChannel();
                        mRtcEngine.destroy();

                        showLoading();
                        if (companySelected)
                            networking.startCall(farmId, animalType, vetCategory, doneAnalysis, companyName, true, groupId, freeCall, callAmount, paymentId);
                        else
                            networking.startCall(farmId, animalType, vetCategory, doneAnalysis, "", true, groupId, freeCall, callAmount, paymentId);
                    } else {
                        utils.shortToast(utils.getStringValue(R.string.user_busy));
                        preferenceUtils.setBlockNavigationStatus(false);
                        activity.onBackPressed();
                    }
                }
            }
        }.start();
    }

    private void setupRemoteVideoStream(int uid) {
        SurfaceView videoSurface = RtcEngine.CreateRendererView(activity.getBaseContext());
        binding.incomingVideoLayout.addView(videoSurface);
        mRtcEngine.setupRemoteVideo(new VideoCanvas(videoSurface, VideoCanvas.RENDER_MODE_FIT, uid));
        mRtcEngine.setRemoteSubscribeFallbackOption(Constants.STREAM_FALLBACK_OPTION_AUDIO_ONLY);
    }

    public void onAudioMuteClicked() {
        if (audioDisabled) {
            binding.audioBtn.setImageResource(R.drawable.ic_mic_on);
            audioDisabled = false;
        }
        else {
            binding.audioBtn.setImageResource(R.drawable.ic_mic_off);
            audioDisabled = true;
        }
        mRtcEngine.muteLocalAudioStream(audioDisabled);
    }

    public void onVideoMuteClicked() {
        if (videoDisabled) {
            binding.videoBtn.setImageResource(R.drawable.ic_camera_on);
            videoDisabled = false;
        }
        else {
            binding.videoBtn.setImageResource(R.drawable.ic_camera_off);
            videoDisabled = true;
        }

        mRtcEngine.muteLocalVideoStream(videoDisabled);

        FrameLayout container = binding.selfVideoLayout;
        container.setVisibility(videoDisabled ? View.GONE : View.VISIBLE);
        SurfaceView videoSurface = (SurfaceView) container.getChildAt(0);
        videoSurface.setZOrderMediaOverlay(!videoDisabled);
        videoSurface.setVisibility(videoDisabled ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        localBroadcastManager.unregisterReceiver(silentNotificationForReconnecting);
        localBroadcastManager.unregisterReceiver(notificationForFarmerCallDecline);
    }

    private void finishCall(View view) {
        preferenceUtils.setBlockNavigationStatus(false);
        binding.incomingVideoLayout.removeAllViews();
        binding.selfVideoLayout.removeAllViews();

        if (callStarted) {
            utils.shortToast(utils.getStringValue(R.string.call_completed));
            networking.updateCallStatus(callId, "Ended");
            if (preferenceUtils.getUserRole() == 2) {
                Bundle bundle = new Bundle();
                bundle.putString("callId", callId);
                Navigation.findNavController(view).navigate(R.id.action_incomingCallFragment_to_prescriptionFragment, bundle);
            } else
                activity.onBackPressed();
        } else
            activity.onBackPressed();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (preferenceUtils.getUserRole() == 2)
            networking.updateVetStatus("Online");

        if (countDownTimer != null)
            countDownTimer.cancel();

        if (mRtcEngine!= null) {
            mRtcEngine.leaveChannel();
            mRtcEngine.destroy();
            if (mediaPlayer.isPlaying())
                mediaPlayer.pause();
        }
    }

    private void showCallNotConnectedDialog(String message) {
        CallConnectFailedDialog callConnectFailedDialog = new CallConnectFailedDialog(getContext(), activity, message);
        callConnectFailedDialog.setCancelable(true);
        callConnectFailedDialog.show();
        callConnectFailedDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public <T> void networkingRequest(@Nullable MethodType methodType, boolean status, @Nullable T error, Object o) {
        if (methodType == MethodType.startCall && status) {
            dismissLoading();
            StartCallResponse.Data startCallDataResponse = (StartCallResponse.Data) o;

            callId = startCallDataResponse.getCallId()+"";
            channelId = startCallDataResponse.getChannel_id();
            firstName = startCallDataResponse.getFirst_name();
            lastName = startCallDataResponse.getLast_name();
            notificationId = Integer.parseInt(startCallDataResponse.getNotificationId());

            binding.userNameText.setText(utils.getStringValue(R.string.calling) + " - " + firstName + " " + lastName);

            if (!mediaPlayer.isPlaying())
                mediaPlayer.start();

            utils.visibleView(binding.animationLayout);
            initAgoraEngine();
        } else if (methodType == MethodType.rejectCall) {
            dismissLoading();
            preferenceUtils.setBlockNavigationStatus(false);
            activity.onBackPressed();
        } else if (methodType == MethodType.startCall && !status) {
            dismissLoading();

            if (error != null) {
                if ((Boolean) error)
                    showCallNotConnectedDialog((String) o);
            }

            utils.updateErrorEvent("Start Call Error Event", "Call Id - " + groupId + " Error Message - " + (String) o);

            finishCall(view1);
        }
    }
*/
}