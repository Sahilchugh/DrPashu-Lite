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

import com.drpashu.sdk.R;
import com.drpashu.sdk.network.model.response.AnimalListResponse;

import java.util.List;

public class AnimalListAdapter extends RecyclerView.Adapter<AnimalListAdapter.Viewholder> {
    Context context;
    Activity activity;
    MultiSelectInterface multiSelectInterface;
    List<AnimalListResponse.Data> animalList;

    public AnimalListAdapter(Context context, Activity activity, List<AnimalListResponse.Data> animalList, MultiSelectInterface multiSelectInterface){
        this.context = context;
        this.activity = activity;
        this.animalList = animalList;
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

        holder.animalText.setText(animalList.get(position).getType());
        if (animalList.get(position).getAnimalCheck() == null)
            animalList.get(position).setAnimalCheck(false);
        if (animalList.get(position).getAnimalCheck()) {
            holder.animalText.setTextColor(context.getResources().getColor(R.color.white));
            holder.animalCardView.setCardBackgroundColor(context.getResources().getColor(R.color.base));
        } else {
            holder.animalText.setTextColor(context.getResources().getColor(R.color.base));
            holder.animalCardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        }

        holder.itemView.setOnClickListener(view -> {
            animalList.get(position).setAnimalCheck(!animalList.get(position).getAnimalCheck());
            multiSelectInterface.selectAnimalLayout(position, animalList.get(position).getId()+"");
        });
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        private TextView animalText;
        private CardView animalCardView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            animalText = itemView.findViewById(R.id.textview);
            animalCardView = itemView.findViewById(R.id.cardview);
        }
    }
}
