package com.drpashu.sdk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

//import com.drpashu.app.adapter.CallHistoryInterface;
//import com.drpashu.app.adapter.CallHistoryListAdapter;
import com.drpashu.sdk.databinding.FragmentCallHistoryBinding;
import com.drpashu.sdk.BaseFragment;
import com.drpashu.sdk.network.model.response.CallHistoryListResponse;

import java.util.List;

public class CallHistoryFragment extends BaseFragment {  //implements CallHistoryInterface
    private FragmentCallHistoryBinding binding;
    private View view1;
    private int adminUserRole;
    private Boolean adminUserSearch = false;
    private String notificationCallId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            notificationCallId = getArguments().getString("callId");
            adminUserRole = getArguments().getInt("adminUserRole");
            adminUserSearch = getArguments().getBoolean("adminUserSearch");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCallHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
/*

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.updateTooblar(ContextCompat.getDrawable(activity, R.drawable.ic_history), true);
        activity.updateFirebaseEvents("navigation", "call_history_menu");

        view1 = view;

        if (getArguments() != null) {
            if (getArguments().getString("callId") != null) {
                if (notificationCallId.length() != 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("callId", notificationCallId + "");
                    notificationCallId = "";
                    Navigation.findNavController(view1).navigate(R.id.action_nav_history_to_callDetailFragment, bundle);
                } else {
                    showLoading();
                    networking.getCallHistoryList(adminUserSearch);
                }
            } else {
                showLoading();
                networking.getCallHistoryList(adminUserSearch);
            }
        } else {
            showLoading();
            networking.getCallHistoryList(adminUserSearch);
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

            CallHistoryListAdapter callHistoryListAdapter = new CallHistoryListAdapter(context, activity, getUserRole(adminUserSearch), (CallHistoryInterface) this, callHistoryList);
            binding.historyRecyclerview.setItemViewCacheSize(callHistoryList.size());
            binding.historyRecyclerview.setAdapter(callHistoryListAdapter);
        }
        else if (methodType == MethodType.getCallHistoryList && !status)
            dismissLoading();
    }

    private int getUserRole(Boolean adminUserSearch) {
        if (preferenceUtils.getUserRole() == 3) {
            if (adminUserSearch)
                return adminUserRole;
            else
                return preferenceUtils.getUserRole();
        } else
            return preferenceUtils.getUserRole();
    }

    @Override
    public void selectedCall(int callId) {
        Bundle bundle = new Bundle();
        bundle.putString("callId", callId+"");
        bundle.putInt("adminUserRole", adminUserRole);
        bundle.putBoolean("adminUserSearch", adminUserSearch);
        Navigation.findNavController(view1).navigate(R.id.action_nav_history_to_callDetailFragment, bundle);
    }
*/
}