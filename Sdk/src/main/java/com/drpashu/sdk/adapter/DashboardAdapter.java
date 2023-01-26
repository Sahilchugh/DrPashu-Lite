package com.drpashu.sdk.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.drpashu.app.R;
import com.drpashu.app.network.model.response.DashboardResponse;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.Viewholder> {
    Context context;
    Activity activity;
    List<DashboardResponse.Data> dashboardResponses;

    public DashboardAdapter(Context context, Activity activity, List<DashboardResponse.Data> dashboardResponses){
        this.context = context;
        this.activity = activity;
        this.dashboardResponses = dashboardResponses;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.item_dashboard, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.eggCount.setText(dashboardResponses.get(position).getEggs());
        holder.feedCount.setText(dashboardResponses.get(position).getFeed());
        holder.eggText.setText(dashboardResponses.get(position).getProduce());
        holder.vaccineCount.setText(dashboardResponses.get(position).getVaccinationOverdue()+"");
        if (dashboardResponses.get(position).getStatus().equalsIgnoreCase("other")) {
            holder.lotName.setBackgroundColor(context.getResources().getColor(R.color.dark_base));
            holder.lotName.setText(dashboardResponses.get(position).getFirst_name() + " "+context.getResources().getString(R.string.farm)+" - "+
                    dashboardResponses.get(position).getAnimal_type()+" - " + dashboardResponses.get(position).getLotName());
        }  else {
            holder.lotName.setBackgroundColor(context.getResources().getColor(R.color.base));
            holder.lotName.setText(dashboardResponses.get(position).getAnimal_type() + " - " + dashboardResponses.get(position).getLotName());
        }
        holder.profitMonth.setText("₹ "+dashboardResponses.get(position).getProfit_month()+"");
        holder.profitTotal.setText("₹ "+dashboardResponses.get(position).getProfit_total()+"");

        if (dashboardResponses.get(position).getMonth_sign().equalsIgnoreCase("-"))
                holder.profitMonth.setTextColor(ContextCompat.getColor(context, R.color.red));
        else
            holder.profitMonth.setTextColor(ContextCompat.getColor(context, R.color.green_dark));

        if (dashboardResponses.get(position).getTotal_sign().equalsIgnoreCase("-"))
            holder.profitTotal.setTextColor(ContextCompat.getColor(context, R.color.red));
        else
            holder.profitTotal.setTextColor(ContextCompat.getColor(context, R.color.green_dark));

        holder.eggCount.setOnClickListener(v -> holder.eggImage.performClick());

        holder.feedCount.setOnClickListener(v -> holder.feedImage.performClick());

        holder.vaccineCount.setOnClickListener(v -> holder.vaccineImage.performClick());

        holder.eggImage.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("lot_id", Integer.parseInt(dashboardResponses.get(position).getLotId()));
            Navigation.findNavController(v).navigate(R.id.action_nav_dashboard_to_nav_eggs, bundle);
        });

        holder.feedImage.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("lot_id", Integer.parseInt(dashboardResponses.get(position).getLotId()));
            Navigation.findNavController(v).navigate(R.id.action_nav_dashboard_to_nav_feed, bundle);
        });

        holder.vaccineImage.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("lot_id", Integer.parseInt(dashboardResponses.get(position).getLotId()));
            Navigation.findNavController(v).navigate(R.id.action_nav_dashboard_to_nav_vaccinations, bundle);
        });

        holder.financeLayout.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("lot_id", Integer.parseInt(dashboardResponses.get(position).getLotId()));
            Navigation.findNavController(v).navigate(R.id.action_nav_dashboard_to_nav_finance, bundle);
        });

        holder.view.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("lot_id", Integer.parseInt(dashboardResponses.get(position).getLotId()));
            Navigation.findNavController(v).navigate(R.id.action_nav_dashboard_to_nav_health, bundle);
        });

        holder.lotName.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_nav_dashboard_to_nav_manage_farm));
    }

    @Override
    public int getItemCount() {
        return dashboardResponses.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView eggCount, feedCount, vaccineCount, lotName, profitMonth, profitTotal, eggText;
        ImageView eggImage, feedImage, vaccineImage;
        ConstraintLayout financeLayout;
        View view;

        public Viewholder(@NonNull View itemView) {

            super(itemView);

            eggCount = itemView.findViewById(R.id.eggCount);
            feedCount = itemView.findViewById(R.id.feedCount);
            vaccineCount = itemView.findViewById(R.id.vaccineCount);
            lotName = itemView.findViewById(R.id.lotName);
            eggImage = itemView.findViewById(R.id.eggImage);
            feedImage = itemView.findViewById(R.id.feedImage);
            vaccineImage = itemView.findViewById(R.id.vaccineImage);
            profitMonth = itemView.findViewById(R.id.profitMonth);
            profitTotal = itemView.findViewById(R.id.profitTotal);
            financeLayout = itemView.findViewById(R.id.financeLayout);
            eggText = itemView.findViewById(R.id.eggText);
            view = itemView.findViewById(R.id.view12);

        }
    }
}
