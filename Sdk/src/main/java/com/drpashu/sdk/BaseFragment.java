package com.drpashu.sdk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.drpashu.sdk.network.Networking;
import com.drpashu.sdk.network.NetworkingInterface;
import com.drpashu.sdk.utils.PreferenceUtils;
import com.drpashu.sdk.utils.Utils;

import java.io.File;
import java.io.IOException;

public class BaseFragment extends Fragment implements NetworkingInterface {
    protected Context context;
    protected HomeActivity activity;
    protected Networking networking;
    protected PreferenceUtils preferenceUtils;
    protected Utils utils;
    protected String cameraImagePath = "";
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getContext();
        this.activity = (HomeActivity) getActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        networking = new Networking(context, activity, this);
        utils = new Utils(context, activity);
        preferenceUtils = new PreferenceUtils(context);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(utils.getStringValue(R.string.loading));
        progressDialog.setCancelable(false);
    }

    protected void showLoading(){
        progressDialog.show();
    }

    protected void dismissLoading(){
        progressDialog.dismiss();
    }

    protected void dispatchTakePictureIntent(int requestCode) {
        if (utils.checkSelfPermission(3) && utils.checkSelfPermission(4)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
                // Create the File where the photo should go
                File imageFile = null;
                try {
                    imageFile = utils.createImageFile();
                    cameraImagePath = imageFile.getAbsolutePath();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    Log.e("createFile", ex.getMessage() + "");
                }

                if (imageFile != null) {
                    Uri imageUri = FileProvider.getUriForFile(activity,
                            "com.drpashu.sdk.fileprovider",
                            imageFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(takePictureIntent, requestCode);
                }
            }
        }
    }

    protected void dispatchUploadPictureIntent(int requestCode) {
        if (utils.checkSelfPermission(4)) {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, requestCode);
        }
    }

    protected void dispatchUploadFileIntent(int requestCode) {
        if (utils.checkSelfPermission(4)) {
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Pdf"), requestCode);
        }
    }

    @Override
    public <T> void networkingRequest(@Nullable MethodType methodType, boolean status, @Nullable T error, Object object) {
    }
}
