package com.drpashu.sdk.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.drpashu.sdk.R;
import com.drpashu.sdk.databinding.FragmentHomeBinding;

public class HomeFragment extends BaseFragment {
    private FragmentHomeBinding binding;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        localBroadcastManager = LocalBroadcastManager.getInstance(context);

        IntentFilter navigationIntent = new IntentFilter();
        navigationIntent.addAction("screenNavigation");
        localBroadcastManager.registerReceiver(navigationBroadcastReceiver , navigationIntent);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        localBroadcastManager.unregisterReceiver(navigationBroadcastReceiver);
    }


    private final BroadcastReceiver navigationBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (intent.getExtras().getString("screen").equalsIgnoreCase("consultDoctor")) {
                    binding.consultDoctorBtn.performClick();
                } else if (intent.getExtras().getString("screen").equalsIgnoreCase("callHistory")) {
                    binding.callHistoryBtn.performClick();
                } else {
                    activity.onBackPressed();
                    Toast.makeText(context, "Error Loading Sdk", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onClickListeners();
    }

    private void onClickListeners() {
        binding.consultDoctorBtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_consult_doctor));
        binding.callHistoryBtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_call_history));
        binding.myWalletBtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_my_wallet));
    }
}