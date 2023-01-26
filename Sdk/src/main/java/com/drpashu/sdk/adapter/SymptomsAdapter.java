package com.drpashu.sdk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.drpashu.app.R;
import com.drpashu.app.fragments.home.farmer.health.SymptomInterface;
import com.drpashu.app.network.model.response.SymptomsListResponse;

import java.util.ArrayList;
import java.util.List;

public class SymptomsAdapter extends RecyclerView.Adapter<SymptomsAdapter.Viewholder> {
    Context context;
    SymptomInterface symptomInterface;
    List<SymptomsListResponse> symptomsListResponses;
    ArrayList<String> symptomIdList;

    public SymptomsAdapter(Context context, List<SymptomsListResponse> symptomsListResponses, ArrayList<String> symptomIdList, SymptomInterface symptomInterface){
        this.context = context;
        this.symptomsListResponses = symptomsListResponses;
        this.symptomInterface = symptomInterface;
        this.symptomIdList = symptomIdList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.item_symptom, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.symptomCheckbox.setText(symptomsListResponses.get(position).getSymptoms());

        for (int i = 0; i < symptomIdList.size(); i++){
            if (symptomIdList.get(i).equalsIgnoreCase(symptomsListResponses.get(position).getId()+""))
                symptomsListResponses.get(position).setSelected(true);
        }
        holder.symptomCheckbox.setOnCheckedChangeListener(null);
        holder.symptomCheckbox.setChecked(symptomsListResponses.get(position).isSelected());

        holder.symptomCheckbox.setOnClickListener(v ->  symptomInterface.updateSymptoms(position, holder.symptomCheckbox.isChecked()));
    }

    @Override
    public int getItemCount() {
        return symptomsListResponses.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        CheckBox symptomCheckbox;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            symptomCheckbox = itemView.findViewById(R.id.symptom);
        }
    }
}
