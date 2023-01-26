package com.drpashu.sdk.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.drpashu.app.R;
import com.drpashu.app.network.ApiClient;
import com.drpashu.app.network.model.response.ResultsResponse;
import com.squareup.picasso.Picasso;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.Viewholder> {

    Context context;
    ResultsResponse resultsResponse;

    public ResultsAdapter(Context context, ResultsResponse resultsResponse) {
        this.context = context;
        this.resultsResponse = resultsResponse;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_results, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.disease.setText(resultsResponse.getDisease().get(position)+"");
        holder.precautions.setText(resultsResponse.getBrief().get(position));
        holder.percentage.setText(resultsResponse.getProb().get(position)+" %");
        Picasso.get().load(ApiClient.BASE_URL_MEDIA+resultsResponse.getDisease_image().get(position)+"").into(holder.resultImg);

        holder.cardTransaction.setOnClickListener(v -> {
            String disease = resultsResponse.getDisease().get(position);
            String information = resultsResponse.getInformation().get(position) + "\n\n"+context.getResources().getString(R.string.symptoms_analysis)+"- "+
                    resultsResponse.getSymptoms().get(position)+ "\n\n"+context.getResources().getString(R.string.treatment) +"- "+ resultsResponse.getTreatment().get(position)+ "\n\n" + resultsResponse.getLink().get(position);
            showDiseaseDetails(disease, information, ApiClient.BASE_URL_MEDIA+resultsResponse.getDisease_image().get(position));
        });

    }

    private void showDiseaseDetails(String disease, String information, String imagelink) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setIcon(R.drawable.animal_logo);
        TextView title = new TextView(context);

        // You Can Customise your Title here
        title.setText(disease);
        title.setBackgroundColor(context.getResources().getColor(R.color.base));
        title.setPadding(20, 0, 20, 20);
        title.setGravity(Gravity.CENTER);
        //   title.setTypeface(, Typeface.NORMAL);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        //  title.setTypeface(getResources().getFont(R.font.poppins_medium));
        builder.setCustomTitle(title);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(context);

        imageView.setPadding(20, 20, 20, 20);

//        imageView.setLayoutParams(new LinearLayout.LayoutParams(650, 450));
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setMinimumHeight(450);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.get().load(imagelink).into(imageView);
        layout.addView(imageView);

        final TextView message = new TextView(context);

        final SpannableString s =
                new SpannableString(information);
        Linkify.addLinks(s, Linkify.WEB_URLS);
        message.setText(s);
        message.setPadding(20, 20, 20, 20);
        message.setTextColor(Color.BLACK);
        message.setTextSize(20);

        message.setMovementMethod(LinkMovementMethod.getInstance());
        layout.addView(message);

        builder.setView(layout)
//        builder.setMessage(information)
                .setPositiveButton(context.getResources().getString(R.string.okay), new DialogInterface.OnClickListener()                 {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    @Override
    public int getItemCount() {
        return Math.min(resultsResponse.getDisease().size(), 5);
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView disease, precautions, percentage;
        CardView cardTransaction;
        ImageView resultImg;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            disease = itemView.findViewById(R.id.disease);
            percentage = itemView.findViewById(R.id.percentage);
            precautions = itemView.findViewById(R.id.precautions);
            cardTransaction = itemView.findViewById(R.id.cardTransactions);
            resultImg = itemView.findViewById(R.id.diseaseImg);
        }
    }
}