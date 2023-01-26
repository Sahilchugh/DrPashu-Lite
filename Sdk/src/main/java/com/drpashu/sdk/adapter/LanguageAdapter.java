package com.drpashu.sdk.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.drpashu.app.R;
import com.drpashu.app.network.model.response.LanguageDataResponse;

import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.Viewholder> {
    Context context;
    Activity activity;
    MultiSelectInterface multiSelectInterface;
    List<LanguageDataResponse> languageDataResponseList;

    public LanguageAdapter(Context context, Activity activity, List<LanguageDataResponse> languageDataResponseList, MultiSelectInterface multiSelectInterface){
        this.context = context;
        this.activity = activity;
        this.languageDataResponseList = languageDataResponseList;
        this.multiSelectInterface = multiSelectInterface;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.item_multi_selection, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.languageText.setText(languageDataResponseList.get(position).getLanguage());
        if (languageDataResponseList.get(position).isLanguageCheck()) {
            holder.languageText.setTextColor(context.getResources().getColor(R.color.white));
            holder.languageCardView.setCardBackgroundColor(context.getResources().getColor(R.color.base));
        } else {
            holder.languageText.setTextColor(context.getResources().getColor(R.color.base));
            holder.languageCardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        }

        holder.itemView.setOnClickListener(view -> {
            languageDataResponseList.get(position).setLanguageCheck(!languageDataResponseList.get(position).isLanguageCheck());
            multiSelectInterface.selectLanguageLayout(position, position+"");
        });
    }

    @Override
    public int getItemCount() {
        return languageDataResponseList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        private TextView languageText;
        private CardView languageCardView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            languageText = itemView.findViewById(R.id.textview);
            languageCardView = itemView.findViewById(R.id.cardview);
        }
    }
}
