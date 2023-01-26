package com.drpashu.sdk.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drpashu.sdk.R;
import com.drpashu.sdk.network.model.response.AnimalListResponse;

import java.util.List;

public class SelectAnimalAdapter extends RecyclerView.Adapter<SelectAnimalAdapter.Viewholder> implements SelectAnimalInterface {

    Context context;
    Activity activity;
    List<AnimalListResponse> animalResponseList;
    private int checkedPosition = -1;
    int selectedListPosition = -1;

    public SelectAnimalAdapter(Context context, Activity activity, List<AnimalListResponse> animalResponseList) {
        this.context = context;
        this.activity = activity;
        this.animalResponseList = animalResponseList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.item_select_animal, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.animalClassText.setText(animalResponseList.get(position).getAnimalClass()+"");
        SelectAnimalDetailAdapter selectAnimalDetailAdapter = new SelectAnimalDetailAdapter(context, activity, position, animalResponseList.get(position).getData(), this);
        holder.animalDetailRecyclerview.setAdapter(selectAnimalDetailAdapter);
    }

    @Override
    public int getItemCount() {
        return animalResponseList.size();
    }

    @Override
    public void selectedAnimal(String animalType) {

    }

    @Override
    public void selectedAnimalList(int animalList) {
        if (selectedListPosition != -1 && selectedListPosition != animalList) {
            notifyItemChanged(selectedListPosition);
        }
        selectedListPosition = animalList;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        RecyclerView animalDetailRecyclerview;
        TextView animalClassText;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            animalClassText = itemView.findViewById(R.id.animalClassText);
            animalDetailRecyclerview = itemView.findViewById(R.id.animalDetailRecyclerview);
            animalDetailRecyclerview.setLayoutManager(new GridLayoutManager(context, 2));
        }
    }
}
