package com.drpashu.sdk.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.drpashu.sdk.R;
import com.drpashu.sdk.adapter.CallHistoryInterface;
import com.drpashu.sdk.adapter.CallHistoryListAdapter;
import com.drpashu.sdk.databinding.FragmentCallHistoryBinding;
import com.drpashu.sdk.network.model.response.CallHistoryListResponse;

import java.util.List;

public class CallHistoryFragment extends BaseFragment implements CallHistoryInterface {
    private FragmentCallHistoryBinding binding;
    private View view1;
    private String notificationCallId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            notificationCallId = getArguments().getString("callId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCallHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view1 = view;

        if (notificationCallId.length() != 0) {
            Bundle bundle = new Bundle();
            bundle.putString("callId", notificationCallId + "");
            notificationCallId = "";
            Navigation.findNavController(view1).navigate(R.id.action_nav_history_to_callDetailFragment, bundle);
        } else {
            showLoading();
            networking.getCallHistoryList();
        }
    }

    @Override
    public <T> void networkingRequest(@Nullable MethodType methodType, boolean status, @Nullable T error, Object o) {
        if (methodType == MethodType.getCallHistoryList && status) {
            dismissLoading();

            List<CallHistoryListResponse.Data> callHistoryList = (List<CallHistoryListResponse.Data>) o;

            if (callHistoryList.size() == 0)
                binding.noHistoryText.setVisibility(View.VISIBLE);

            binding.historyRecyclerview.setLayoutManager(new LinearLayoutManager(context));

            CallHistoryListAdapter callHistoryListAdapter = new CallHistoryListAdapter(context, activity, (CallHistoryInterface) this, callHistoryList);
            binding.historyRecyclerview.setItemViewCacheSize(callHistoryList.size());
            binding.historyRecyclerview.setAdapter(callHistoryListAdapter);
        }
        else if (methodType == MethodType.getCallHistoryList && !status)
            dismissLoading();
    }

    @Override
    public void selectedCall(int callId) {
        Bundle bundle = new Bundle();
        bundle.putString("callId", callId+"");
        Navigation.findNavController(view1).navigate(R.id.action_nav_history_to_callDetailFragment, bundle);
    }
}