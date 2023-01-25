package com.drpashu.sdk;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drpashu.sdk.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

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