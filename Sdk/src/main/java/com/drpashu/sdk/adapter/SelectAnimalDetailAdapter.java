package com.drpashu.sdk.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.drpashu.sdk.R;
import com.drpashu.sdk.network.model.response.AnimalListResponse;
import com.drpashu.sdk.utils.PreferenceUtils;

import java.util.List;

public class SelectAnimalDetailAdapter extends RecyclerView.Adapter<SelectAnimalDetailAdapter.Viewholder>  {

    Context context;
    Activity activity;
    SelectAnimalInterface selectAnimalInterface;
    PreferenceUtils preferenceUtils;
    List<AnimalListResponse.Data> animalResponseDetailList;
    private int checkedPosition = -1;
    private int listPosition = -1;
    public static String breedName = "", breedNameByLanguage = "";


    public SelectAnimalDetailAdapter(Context context, Activity activity, int listPosition, List<AnimalListResponse.Data> animalResponseDetailList, SelectAnimalInterface selectAnimalInterface) {
        this.context = context;
        this.activity = activity;
        this.listPosition= listPosition;
        this.animalResponseDetailList = animalResponseDetailList;
        this.selectAnimalInterface = selectAnimalInterface;
        this.preferenceUtils = new PreferenceUtils(context);
        checkedPosition = -1;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.item_animal_detail, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.bind();

        holder.item_text.setText(animalResponseDetailList.get(position).getType());
        holder.item_image.setImageResource(animalResponseDetailList.get(position).getLocalImage());
    }

    @Override
    public int getItemCount() {
        return animalResponseDetailList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView item_image;
        TextView item_text;
        ConstraintLayout item_layout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            item_image = itemView.findViewById(R.id.item_image);
            item_text = itemView.findViewById(R.id.item_name);
            item_layout = itemView.findViewById(R.id.item_layout);
        }

        void bind() {
            if (checkedPosition == -1) {
                item_layout.setSelected(false);
                item_text.setSelected(false);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    item_layout.setSelected(true);
                    item_text.setSelected(true);
                } else {
                    item_layout.setSelected(false);
                    item_text.setSelected(false);
                }
            }

            itemView.setOnClickListener(v -> {
                item_layout.setSelected(true);
                item_text.setSelected(true);
                if (checkedPosition != getAdapterPosition()) {
                    notifyItemChanged(checkedPosition);
                    checkedPosition = getAdapterPosition();
                    breedName = getSelected().getName();
                    breedNameByLanguage = getSelected().getType();
                }
                selectAnimalInterface.selectedAnimalList(listPosition);
            });
        }
    }

    public AnimalListResponse.Data getSelected(){
        if (checkedPosition != -1){
            return animalResponseDetailList.get(checkedPosition);
        }
        return null;
    }

}
