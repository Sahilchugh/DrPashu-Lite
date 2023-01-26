package com.drpashu.sdk.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drpashu.app.R;

import java.util.List;

public class AnalysisDialog extends Dialog {
    private Button takePhotoBtn, continueAnalysisBtn;
    private TextView noDataText;
    private ListView analysisList;
    private List<String> resultList;
    private String message;
    private ActionInterface actionInterface;


    public AnalysisDialog(@NonNull Context context, String message, List<String> resultList, ActionInterface actionInterface) {
        super(context);
        this.message = message;
        this.resultList = resultList;
        this.actionInterface = actionInterface;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_analysis);

        init();
        analysisList.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.list_item_result, R.id.symptom, resultList));
        analysisList.setDivider(null);

        noDataText.setText(message);

        continueAnalysisBtn.setOnClickListener(v -> {
            dismiss();
            actionInterface.Action(2);
        });
        takePhotoBtn.setOnClickListener(v -> {
            dismiss();
            actionInterface.Action(1);
        });
    }

    private void init() {
        takePhotoBtn = findViewById(R.id.takePhotoBtn);
        continueAnalysisBtn = findViewById(R.id.continueAnalysisBtn);
        analysisList = findViewById(R.id.analysisList);
        noDataText = findViewById(R.id.noDataText);
    }
}
