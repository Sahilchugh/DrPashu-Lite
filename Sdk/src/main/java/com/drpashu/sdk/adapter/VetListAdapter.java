package com.drpashu.sdk.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.drpashu.sdk.R;
import com.drpashu.sdk.databinding.ItemVetBinding;
import com.drpashu.sdk.network.ApiClient;
import com.drpashu.sdk.network.model.response.VetListResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VetListAdapter extends RecyclerView.Adapter<VetListAdapter.ViewHolder> {

    Context context;
    Activity activity;
    List<VetListResponse.Data.Vet> vetListResponse;

    public VetListAdapter(Context context, Activity activity, List<VetListResponse.Data.Vet> vetListResponse) {
        this.context = context;
        this.activity = activity;
        this.vetListResponse = vetListResponse;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemVetBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.nameText.setText(vetListResponse.get(position).getFirstName()+" "+vetListResponse.get(position).getLastName());

        if (!vetListResponse.get(position).getVetStatus().equalsIgnoreCase("Online"))
            holder.binding.degreeText.setText(vetListResponse.get(position).getVetDegree()+"*");
        else
            holder.binding.degreeText.setText(vetListResponse.get(position).getVetDegree()+".");

        if (vetListResponse.get(position).getVetProfileImage() != null)
            Picasso.get().load(ApiClient.BASE_URL_MEDIA+vetListResponse.get(position).getVetProfileImage()).into(holder.binding.doctorImg);

        String[] languageList = context.getResources().getStringArray(R.array.language_list_vet);
        String languageListValue = "";

        try {
            for (int i = 0; i< vetListResponse.get(position).getVetLanguages().size(); i++)
                languageListValue += ", "+ languageList[Integer.parseInt(vetListResponse.get(position).getVetLanguages().get(i))] ;

            if (languageListValue.length()>2)
                holder.binding.languageText.setText(languageListValue.substring(2));
        } catch (Exception e)
        {
            Log.e("exception", e.getMessage()+"");
        }
    }


    @Override
    public int getItemCount() {
        return vetListResponse.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemVetBinding binding;

        public ViewHolder(ItemVetBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}