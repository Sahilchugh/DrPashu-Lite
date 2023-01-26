package com.drpashu.sdk.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.drpashu.app.R;
import com.drpashu.app.databinding.ItemWalletTransactionBinding;
import com.drpashu.app.network.model.response.WalletTransactionResponse;

import java.util.List;

public class WalletTransactionAdapter extends RecyclerView.Adapter<WalletTransactionAdapter.ViewHolder> {

    Context context;
    Activity activity;
    List<WalletTransactionResponse.Data> walletTransactionResponseList;

    public WalletTransactionAdapter(Context context, Activity activity, List<WalletTransactionResponse.Data> walletTransactionResponseList) {
        this.context = context;
        this.activity = activity;
        this.walletTransactionResponseList = walletTransactionResponseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemWalletTransactionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (walletTransactionResponseList.get(position).getType() == 0){
            holder.binding.transactionDetailText.setText(context.getResources().getString(R.string.transaction_call_debit) +": " + walletTransactionResponseList.get(position).getRemark());

            holder.binding.transactionAmount.setText("-"+walletTransactionResponseList.get(position).getAmount());
            holder.binding.transactionAmount.setTextColor(ContextCompat.getColor(context, R.color.transaction_red));
        }
        else if (walletTransactionResponseList.get(position).getType() == 1){
            holder.binding.transactionDetailText.setText(context.getResources().getString(R.string.transaction_call_credit) +": " + walletTransactionResponseList.get(position).getRemark());

            holder.binding.transactionAmount.setText("+"+walletTransactionResponseList.get(position).getAmount());
            holder.binding.transactionAmount.setTextColor(ContextCompat.getColor(context, R.color.transaction_green));
        }
        else if (walletTransactionResponseList.get(position).getType() == 4){
            holder.binding.transactionDetailText.setText(context.getResources().getString(R.string.welcome_gift));

            holder.binding.transactionAmount.setText("+"+walletTransactionResponseList.get(position).getAmount());
            holder.binding.transactionAmount.setTextColor(ContextCompat.getColor(context, R.color.transaction_green));
        } else {
            holder.binding.transactionDetailText.setText(walletTransactionResponseList.get(position).getRemark());
            holder.binding.transactionAmount.setText(walletTransactionResponseList.get(position).getAmount());
        }

        holder.binding.transactionTimeText.setText(walletTransactionResponseList.get(position).getDate()+ " " + walletTransactionResponseList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return walletTransactionResponseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemWalletTransactionBinding binding;

        public ViewHolder(ItemWalletTransactionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}