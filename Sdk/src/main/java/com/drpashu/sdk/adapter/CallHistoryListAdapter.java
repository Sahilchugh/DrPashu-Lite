package com.drpashu.sdk.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.drpashu.sdk.R;
import com.drpashu.sdk.databinding.ItemCallHistoryBinding;
import com.drpashu.sdk.network.ApiClient;
import com.drpashu.sdk.network.model.response.CallHistoryListResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CallHistoryListAdapter extends RecyclerView.Adapter<CallHistoryListAdapter.ViewHolder> {
    private Context context;
    private Activity activity;
    private CallHistoryInterface callHistoryInterface;
    private List<CallHistoryListResponse.Data> callHistoryResponseList;

    public CallHistoryListAdapter(Context context, Activity activity, CallHistoryInterface callHistoryInterface, List<CallHistoryListResponse.Data> callHistoryResponseList) {
        this.context = context;
        this.activity = activity;
        this.callHistoryInterface = callHistoryInterface;
        this.callHistoryResponseList = callHistoryResponseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCallHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.userText.setText(callHistoryResponseList.get(position).getFirstName()+" "+callHistoryResponseList.get(position).getLastName());
        holder.binding.dateText.setText(callHistoryResponseList.get(position).getTime());

        if (callHistoryResponseList.get(position).getProfilePicture() != null){
            if (callHistoryResponseList.get(position).getProfilePicture().length() != 0)
                Picasso.get().load(ApiClient.BASE_URL_MEDIA+callHistoryResponseList.get(position).getProfilePicture()).into(holder.binding.userImg);
        }

        if (callHistoryResponseList.get(position).getCallInitiated().equalsIgnoreCase("Farmer") && callHistoryResponseList.get(position).getCallStatusRes().equalsIgnoreCase("Missed"))
            holder.binding.callStatusIcon.setImageResource(R.drawable.call_out_miss);
        else if (callHistoryResponseList.get(position).getCallInitiated().equalsIgnoreCase("Farmer") && callHistoryResponseList.get(position).getCallStatusRes().equalsIgnoreCase("Completed"))
            holder.binding.callStatusIcon.setImageResource(R.drawable.call_out_done);
        else if (callHistoryResponseList.get(position).getCallInitiated().equalsIgnoreCase("Vet") && callHistoryResponseList.get(position).getCallStatusRes().equalsIgnoreCase("Missed"))
            holder.binding.callStatusIcon.setImageResource(R.drawable.call_in_miss);
        else if (callHistoryResponseList.get(position).getCallInitiated().equalsIgnoreCase("Vet") && callHistoryResponseList.get(position).getCallStatusRes().equalsIgnoreCase("Completed"))
            holder.binding.callStatusIcon.setImageResource(R.drawable.call_in_done);
        else if (callHistoryResponseList.get(position).getCallInitiated().equalsIgnoreCase("Admin") && callHistoryResponseList.get(position).getCallStatusRes().equalsIgnoreCase("Missed"))
            holder.binding.callStatusIcon.setImageResource(R.drawable.call_in_miss);
        else if (callHistoryResponseList.get(position).getCallInitiated().equalsIgnoreCase("Admin") && callHistoryResponseList.get(position).getCallStatusRes().equalsIgnoreCase("Completed"))
            holder.binding.callStatusIcon.setImageResource(R.drawable.call_in_done);

        holder.itemView.setOnClickListener(v -> callHistoryInterface.selectedCall(callHistoryResponseList.get(position).getId()));

        if (callHistoryResponseList.get(position).getPrescriptionUploaded())
            holder.binding.prescriptionStatusIcon.setVisibility(View.VISIBLE);
        else
            holder.binding.prescriptionStatusIcon.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return callHistoryResponseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCallHistoryBinding binding;

        public ViewHolder(ItemCallHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}