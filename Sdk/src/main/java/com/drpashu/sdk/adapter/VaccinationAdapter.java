package com.drpashu.sdk.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.drpashu.app.R;
import com.drpashu.app.network.Networking;
import com.drpashu.app.network.NetworkingInterface;
import com.drpashu.app.network.model.response.VaccinationResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class VaccinationAdapter extends RecyclerView.Adapter<VaccinationAdapter.Viewholder> {

    Context context;
    Activity activity;
    NetworkingInterface networkingInterface;
    List<VaccinationResponse> vaccinationResponse;
    Networking networking;
    UpdateVaccine updateVaccine;

    public VaccinationAdapter(Context context, Activity activity, NetworkingInterface networkingInterface, List<VaccinationResponse> vaccinationResponse){
        this.context = context;
        this.activity = activity;
        this.networkingInterface = networkingInterface;
        this.vaccinationResponse = vaccinationResponse;
    }

    public VaccinationAdapter(Context context, Activity activity, NetworkingInterface networkingInterface, List<VaccinationResponse> vaccinationResponse, UpdateVaccine updateVaccine) {
        this.context = context;
        this.activity = activity;
        this.networkingInterface = networkingInterface;
        this.vaccinationResponse = vaccinationResponse;
        this.updateVaccine = updateVaccine;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.item_vaccination, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.vaccine.setText(vaccinationResponse.get(position).getVaccine());
        holder.type.setText(vaccinationResponse.get(position).getType());
        holder.vaccine_date.setText(vaccinationResponse.get(position).getVaccineDate());
//        holder.name_of_lot.setText("Farm - "+vaccinationResponse.get(position).getNameOfLot());
        if (vaccinationResponse.get(position).getOwner().equalsIgnoreCase("other")) {
            holder.name_of_lot.setText( vaccinationResponse.get(position).getFirstName()+" "+ context.getResources().getString(R.string.farm)+" - " + vaccinationResponse.get(position).getNameOfLot());
            holder.nameLayout.setBackgroundColor(context.getResources().getColor(R.color.dark_base));
        } else {
            holder.name_of_lot.setText(vaccinationResponse.get(position).getAnimal_type()+" - " + vaccinationResponse.get(position).getNameOfLot());
            holder.nameLayout.setBackgroundColor(context.getResources().getColor(R.color.base));
        }
        holder.chicken_name.setText(vaccinationResponse.get(position).getAnimalName());

        holder.card_vaccination.setOnClickListener(v -> {
            if(vaccinationResponse.get(position).getStatus().equalsIgnoreCase("overdue")){
                String userId= vaccinationResponse.get(position).getUser_id();
                int id = vaccinationResponse.get(position).getVaccineId();
                String vaccine_information = vaccinationResponse.get(position).getInformation() + "\n\n" + vaccinationResponse.get(position).getLink();
                String name_lot = vaccinationResponse.get(position).getNameOfLot();
                String vaccination = vaccinationResponse.get(position).getVaccine();
                int lot_id = vaccinationResponse.get(position).getLot_id();
                showdialog_details(id, vaccination, vaccine_information, name_lot, lot_id, userId);
            } else {
                String vaccine_information = vaccinationResponse.get(position).getInformation() + "\n\n" + vaccinationResponse.get(position).getLink();
                String vaccination = vaccinationResponse.get(position).getVaccine();
                showdialog_details_info(vaccination, vaccine_information);
            }
        });
    }

    private void showdialog_details_info(String vaccination, String vaccine_information) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        TextView title = new TextView(context);

        // You Can Customise your Title here
        title.setText(vaccination);
        title.setBackgroundColor(context.getResources().getColor(R.color.base));
        title.setPadding(20, 20, 20, 20);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        builder.setCustomTitle(title);

        final TextView message = new TextView(context);

        final SpannableString s =
                new SpannableString(vaccine_information);
        Linkify.addLinks(s, Linkify.WEB_URLS);
        message.setText(s);
        message.setPadding(20, 20, 20, 20);
        message.setTextColor(Color.BLACK);
        message.setTextSize(20);

        message.setMovementMethod(LinkMovementMethod.getInstance());
        builder.setView(message)
                .setPositiveButton(context.getResources().getString(R.string.okay), (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void showdialog_details(int id, String vaccination, String vaccine_information, String name_lot,
                                    int lot_id, String userId) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        TextView title = new TextView(context);

        // You Can Customise your Title here
        title.setText(vaccination);
        title.setBackgroundColor(context.getResources().getColor(R.color.base));
        title.setPadding(20, 20, 20, 20);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        builder.setCustomTitle(title);

        final TextView message = new TextView(context);

        final SpannableString s =
                new SpannableString(vaccine_information);
        Linkify.addLinks(s, Linkify.WEB_URLS);
        message.setText(s);
        message.setPadding(20, 20, 20, 20);
        message.setTextColor(Color.BLACK);
        message.setTextSize(20);

        message.setMovementMethod(LinkMovementMethod.getInstance());
        builder.setView(message)
                .setPositiveButton(context.getResources().getString(R.string.mark_done), (dialog, which) -> {
                    dialog.cancel();
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, i, i1, i2) -> {
                        networking = new Networking(context, activity, networkingInterface);
                        calendar.set(Calendar.YEAR, i);
                        calendar.set(Calendar.MONTH, i1);
                        calendar.set(Calendar.DAY_OF_MONTH, i2);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
                        String date = simpleDateFormat.format(calendar.getTime());
                        priceDialog(id, name_lot, date, lot_id, userId);
                    };
                    DatePickerDialog datePickerDialog = new DatePickerDialog(context, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000);
                    datePickerDialog.show();
                }).setNegativeButton(context.getResources().getString(R.string.okay), null);

        builder.show();

    }

    private void priceDialog(int id, String name_lot, String date, int lot_id, String userId) {
        android.app.AlertDialog.Builder alertName = new android.app.AlertDialog.Builder(context);
        final EditText priceFeedInput = new EditText(context);
        priceFeedInput.setGravity(Gravity.CENTER);
        priceFeedInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        priceFeedInput.setHint(context.getResources().getString(R.string.price_rupees));


        alertName.setTitle(context.getResources().getString(R.string.price_of_vaccine));
        alertName.setView(priceFeedInput);
        LinearLayout layoutName = new LinearLayout(context);
        layoutName.setOrientation(LinearLayout.VERTICAL);
        layoutName.addView(priceFeedInput); // displays the user input bar
        alertName.setView(layoutName);
        alertName.setPositiveButton(context.getResources().getString(R.string.proceed), null);

        alertName.setPositiveButton(context.getResources().getString(R.string.proceed), (dialog, whichButton) -> {
            if (priceFeedInput.length()>0)
                updateVaccine.updateVaccine(id, name_lot, date, lot_id,priceFeedInput.getText().toString(), userId);
            else
                Toast.makeText(context, context.getResources().getString(R.string.price_not_empty_error), Toast.LENGTH_SHORT).show();
        });

        alertName.setNegativeButton(context.getResources().getString(R.string.cancel), (dialog, whichButton) -> {
            dialog.cancel(); // closes dialog
            Toast.makeText(context, context.getResources().getString(R.string.operation_ended), Toast.LENGTH_SHORT).show();
        });
        alertName.show();
    }

    @Override
    public int getItemCount() {
        return vaccinationResponse.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView vaccine, type, vaccine_date, name_of_lot, chicken_name;
        CardView card_vaccination;
        ConstraintLayout nameLayout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            vaccine = itemView.findViewById(R.id.vaccine);
            type = itemView.findViewById(R.id.type);
            vaccine_date = itemView.findViewById(R.id.vaccine_date);
            name_of_lot = itemView.findViewById(R.id.name_of_lot);
            chicken_name = itemView.findViewById(R.id.chicken_name);
            card_vaccination = itemView.findViewById(R.id.card_vaccination);
            nameLayout = itemView.findViewById(R.id.nameLayout);
        }
    }
}
