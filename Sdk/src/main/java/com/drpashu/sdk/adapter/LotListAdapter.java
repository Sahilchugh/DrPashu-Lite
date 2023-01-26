package com.drpashu.sdk.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.drpashu.app.R;
import com.drpashu.app.network.ApiClient;
import com.drpashu.app.network.Networking;
import com.drpashu.app.network.NetworkingInterface;
import com.drpashu.app.network.model.response.LotListResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LotListAdapter extends RecyclerView.Adapter<LotListAdapter.Viewholder> {
    Context context;
    Activity activity;
    NetworkingInterface networkingInterface;
    List<LotListResponse> lotListResponse;

    public LotListAdapter(Context context, Activity activity, NetworkingInterface networkingInterface, List<LotListResponse> lotListResponse){
        this.context = context;
        this.activity = activity;
        this.networkingInterface = networkingInterface;
        this.lotListResponse = lotListResponse;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.item_lot, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        String text = context.getResources().getString(R.string.remove_farm_list);
        int i = 1;
        Picasso.get().load(ApiClient.BASE_URL_MEDIA+lotListResponse.get(position).getAnimal_url()+"").into(holder.animalLogo);
        if (lotListResponse.get(position).getOwner().equalsIgnoreCase("vle")){
            i = 3;
            text = context.getResources().getString(R.string.remove_vle_list);
            holder.farmerNameText.setVisibility(View.VISIBLE);
            holder.farmerNameText.setText(lotListResponse.get(position).getFirstName() + " " + lotListResponse.get(position).getLastName() + " VLE");
            holder.lotLayout.setBackgroundColor(context.getResources().getColor(R.color.dark_base));

//            holder.lotText.setVisibility(View.GONE);
            holder.lotNameText.setVisibility(View.GONE);
            holder.breedNameText.setVisibility(View.GONE);
            holder.dobText.setVisibility(View.GONE);
            holder.chickenQuantityText.setVisibility(View.GONE);
            holder.loanStatusText.setVisibility(View.GONE);
            holder.emiPerMonthText.setVisibility(View.GONE);
            holder.durationRemainingText.setVisibility(View.GONE);
            holder.emiStarted.setVisibility(View.GONE);
            holder.emiStartDate.setVisibility(View.GONE);
            holder.main.setVisibility(View.GONE);
        } else {

            holder.lotNameText.setText(lotListResponse.get(position).getAnimal_name()+ " - "+lotListResponse.get(position).getLotName());
            holder.breedNameText.setText(lotListResponse.get(position).getBreedName());
            holder.dobText.setText(lotListResponse.get(position).getAnimal_dob());

            if (!lotListResponse.get(position).getAnimal_quantity().equalsIgnoreCase(lotListResponse.get(position).getAnimalRemaining()))
                holder.chickenQuantityText.setText(lotListResponse.get(position).getAnimalRemaining() + " / " + lotListResponse.get(position).getAnimal_quantity());
            else
                holder.chickenQuantityText.setText("" + lotListResponse.get(position).getAnimal_quantity());

            if (lotListResponse.get(position).getOwner().equalsIgnoreCase("other")) {
                i = 2;
                text = context.getResources().getString(R.string.remove_farmer_list);
                holder.farmerNameText.setVisibility(View.VISIBLE);
                holder.lotNameText.setVisibility(View.INVISIBLE);
                holder.farmerNameText.setText(lotListResponse.get(position).getFirstName() + " "+ "'s Farm - "+lotListResponse.get(position).getLotName());
                holder.farmerNameText.setBackgroundColor(context.getResources().getColor(R.color.dark_base));
            } else
                holder.lotNameText.setVisibility(View.VISIBLE);

            if (lotListResponse.get(position).getIsLoan() != null) {
                if (Integer.parseInt(lotListResponse.get(position).getIsLoan()) == 0) {
                    holder.loanStatusText.setText(context.getResources().getString(R.string.no));
                    holder.emiPerMonthText.setVisibility(View.GONE);
                    holder.emiStartDate.setVisibility(View.GONE);
                    holder.emiStartDatePlainText.setVisibility(View.GONE);
                    holder.durationRemainingText.setVisibility(View.GONE);
                    holder.emiRemainPlainText.setVisibility(View.GONE);
                    holder.emiStartDatePlainText.setVisibility(View.GONE);
                    holder.getEmiPerMonthPlainText.setVisibility(View.GONE);
                    holder.view4.setVisibility(View.GONE);
                    holder.view5.setVisibility(View.GONE);
                } else if (Integer.parseInt(lotListResponse.get(position).getIsLoan()) == 1) {
                    holder.loanStatusText.setText(context.getResources().getString(R.string.yes));
                    holder.emiStartDate.setVisibility(View.VISIBLE);
                    holder.loanPlainText.setVisibility(View.INVISIBLE);
                    holder.loanStatusText.setVisibility(View.INVISIBLE);
                    holder.emiPerMonthText.setVisibility(View.VISIBLE);
                    holder.durationRemainingText.setVisibility(View.VISIBLE);
                    holder.emiRemainPlainText.setVisibility(View.VISIBLE);
                    holder.emiStartDatePlainText.setVisibility(View.VISIBLE);
                    holder.getEmiPerMonthPlainText.setVisibility(View.VISIBLE);
                }
                holder.emiPerMonthText.setText("₹ " + lotListResponse.get(position).getEmi());
                holder.durationRemainingText.setText(lotListResponse.get(position).getDurationMonths() + " "+context.getResources().getString(R.string.months));
                holder.emiStartDate.setText( lotListResponse.get(position).getEmi_start_date());

            } else {
                holder.emiPerMonthText.setVisibility(View.GONE);
                holder.durationRemainingText.setVisibility(View.GONE);
                holder.emiStarted.setVisibility(View.GONE);
                holder.emiStartDate.setVisibility(View.GONE);
                holder.emiRemainPlainText.setVisibility(View.GONE);
                holder.emiStartDatePlainText.setVisibility(View.GONE);
                holder.getEmiPerMonthPlainText.setVisibility(View.GONE);
            }
        }

        String finalText = text;
        int finalI = i;

        holder.chickenQuantityText.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_nav_manage_farm_to_nav_animal_mortality));

        holder.deleteIcon.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setMessage(finalText)
                    .setPositiveButton(context.getResources().getString(R.string.delete), (dialog, which) -> {
                        if (finalI == 2) {
                            new Networking(context, activity, networkingInterface).deleteFarmer(lotListResponse.get(position).getUser_id());
                        } else if (finalI == 3) {
                            new Networking(context, activity, networkingInterface).deleteVle(lotListResponse.get(position).getUser_id_vle());
                        } else {
                            new Networking(context, activity, networkingInterface).deleteLot(lotListResponse.get(position).getId());
                        }
                    }).setNegativeButton(context.getResources().getString(R.string.cancel), null);
            alert.show();
        });
    }

    @Override
    public int getItemCount() {
        return lotListResponse.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView lotNameText,  breedNameText, dobText, chickenQuantityText, loanStatusText, emiPerMonthText,
                durationRemainingText, emiStarted, emiStartDate, farmerNameText, emiStartDatePlainText,
                getEmiPerMonthPlainText, emiRemainPlainText, loanPlainText ;
        ImageView deleteIcon, animalLogo;
        ConstraintLayout lotLayout, main;
        View view4, view5;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
//            lotText = itemView.findViewById(R.id.lotNameText);
            lotNameText = itemView.findViewById(R.id.name_of_lot);
            breedNameText = itemView.findViewById(R.id.breedText);
            dobText = itemView.findViewById(R.id.dobText);
            chickenQuantityText = itemView.findViewById(R.id.chickenQuantityText);
            loanStatusText = itemView.findViewById(R.id.loanStatusText);
            emiPerMonthText = itemView.findViewById(R.id.emiPerMonthText);
            durationRemainingText = itemView.findViewById(R.id.durationRemainingText);
            deleteIcon = itemView.findViewById(R.id.deleteBtn);
            emiStarted = itemView.findViewById(R.id.emiStarted);
            emiStartDate = itemView.findViewById(R.id.emiStartDate);
            farmerNameText = itemView.findViewById(R.id.farmerNameText);
            lotLayout = itemView.findViewById(R.id.lotLayout);
            emiStartDatePlainText = itemView.findViewById(R.id.emiStartPlainText);
            getEmiPerMonthPlainText = itemView.findViewById(R.id.emiMonthPlainText);
            emiRemainPlainText = itemView.findViewById(R.id.totalEmiPlainText);
            loanPlainText = itemView.findViewById(R.id.loanPlainText);
            view4 = itemView.findViewById(R.id.view4);
            view5 = itemView.findViewById(R.id.view5);
            main = itemView.findViewById(R.id.main);
            animalLogo = itemView.findViewById(R.id.animalLogo);
        }
    }
}
