package com.drpashu.sdk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.drpashu.app.R;
import com.drpashu.app.network.ApiClient;
import com.drpashu.app.network.model.response.BreedListResponse;
import com.squareup.picasso.Picasso;

public class FarmSettingsAdapter extends RecyclerView.Adapter<FarmSettingsAdapter.Viewholder>  {

    Context context;
    BreedListResponse breedListResponse;
    private int checkedPosition = -1;
    public static int chic_id= 0;

    public FarmSettingsAdapter(Context context, BreedListResponse breedListResponse){
        this.context = context;
        this.breedListResponse = breedListResponse;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.item_farm_settings, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.bind(breedListResponse.getResponse().get(position));

        holder.item_text.setText(breedListResponse.getResponse().get(position).getBreed());
        Picasso.get().load(ApiClient.BASE_URL_MEDIA+ breedListResponse.getResponse().get(position).getAvatar()).into(holder.item_image);
    }

    @Override
    public int getItemCount() {
        return breedListResponse.getResponse().size();
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

        void bind(final BreedListResponse.Response farmSettingsResponse) {
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
                    chic_id = getSelected().getId();
                }
            });
        }
    }

    public BreedListResponse.Response getSelected(){
        if (checkedPosition != -1){
            return breedListResponse.getResponse().get(checkedPosition);
        }
        return null;
    }
}
