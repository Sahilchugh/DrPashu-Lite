package com.drpashu.sdk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.drpashu.app.R;
import com.drpashu.app.network.model.response.TransactionsResponse;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.Viewholder> {

    Context context;
    TransactionsResponse transactionsResponse;

    public TransactionsAdapter(Context context, TransactionsResponse transactionsResponse) {
        this.context = context;
        this.transactionsResponse = transactionsResponse;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_transactions, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.vaccineDateText.setText(transactionsResponse.getTransactions().get(position).getDate());
        holder.priceText.setText("₹ " + transactionsResponse.getTransactions().get(position).getAmount());
        holder.vaccineName.setText(transactionsResponse.getTransactions().get(position).getInfo());
//        holder.categoryText.setText(transactionsResponse.getTransactions().get(position).getCategory());

        if (transactionsResponse.getTransactions().get(position).getAccount().equalsIgnoreCase("-")){
//            holder.statusText.setText("Expenditure");
            holder.priceText.setTextColor(ContextCompat.getColor(context, R.color.red));
        } else{
//            holder.statusText.setText("Income");
            holder.priceText.setTextColor(ContextCompat.getColor(context, R.color.green_dark));

        }
    }

    @Override
    public int getItemCount() {
        return transactionsResponse.getTransactions().size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView vaccineDateText, categoryText, priceText, vaccineName, statusText;
        CardView cardTransaction;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            vaccineDateText = itemView.findViewById(R.id.vaccineDateText);
            categoryText = itemView.findViewById(R.id.categoryText);
            priceText = itemView.findViewById(R.id.priceText);
            vaccineName = itemView.findViewById(R.id.vaccineName);
            statusText = itemView.findViewById(R.id.statusText);
            cardTransaction = itemView.findViewById(R.id.cardTransactions);
        }
    }
}