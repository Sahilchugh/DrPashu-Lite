package com.drpashu.sdk.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.drpashu.sdk.R;
import com.drpashu.sdk.network.Networking;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private final Context context;
    private final Activity activity;
    private final Networking networking;
    private static final String[] REQUESTED_PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    public Utils(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        networking = new Networking(context, activity, null);
    }

    public String getStringValue(int id){
        return context.getResources().getString(id);
    }

    public String[] getArrayValue(int id){
        return context.getResources().getStringArray(id);
    }

    public void shortToast(String text){
        Toast.makeText(context, text+"", Toast.LENGTH_SHORT).show();
    }

    public void longToast(String text){
        Toast.makeText(context, text+"", Toast.LENGTH_LONG).show();
    }

    public void hideView(@NonNull View view){
        view.setVisibility(View.GONE);
    }

    public void visibleView(@NonNull View view){
        view.setVisibility(View.VISIBLE);
    }

    public void invisibleView(@NonNull View view){
        view.setVisibility(View.INVISIBLE);
    }

    public void updateErrorEvent(String errorTitle, String errorDescription){
        networking.postErrorDetails("SDK - " + errorTitle, errorDescription,
                "Manufacturer: " + Build.MANUFACTURER + ", Model: " + Build.MODEL + ", Android Version: " + Build.VERSION.SDK_INT);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap, int quality) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public File createImageFile() throws IOException {
        //remove old cache images
        File[] cacheImages = activity.getExternalFilesDir("captures").listFiles();
        if (cacheImages != null && cacheImages.length > 0) {
            for (File file : cacheImages)
                file.delete();
        }

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir("captures");
//        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

    public boolean checkSelfPermission(int permissionIndex) {
        if (ContextCompat.checkSelfPermission(context, REQUESTED_PERMISSIONS[permissionIndex]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, REQUESTED_PERMISSIONS, permissionIndex);
            shortToast(getStringValue(R.string.allow_permission_message));
            return false;
        }
        return true;
    }
}