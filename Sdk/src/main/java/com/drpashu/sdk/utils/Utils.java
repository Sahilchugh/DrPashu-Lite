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

    public Bitmap getScaledDownBitmap(Bitmap bitmap, int threshold) {
        try {
            if (bitmap.getWidth() >= bitmap.getHeight()) {
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int newWidth = width;
            int newHeight = height;

            if (width > height && width > threshold) {
                newWidth = threshold;
                newHeight = (int) (height * (float) newWidth / width);
            }

            if (width > height && width <= threshold) {
                //the bitmap is already smaller than our required dimension, no need to resize it
                return bitmap;
            }
            if (width < height && height > threshold) {
                newHeight = threshold;
                newWidth = (int) (width * (float) newHeight / height);
            }

            if (width < height && height <= threshold) {
                //the bitmap is already smaller than our required dimension, no need to resize it
                return bitmap;
            }
            if (width == height && width > threshold) {
                newWidth = threshold;
                newHeight = newWidth;
            }
            if (width == height && width <= threshold) {
                //the bitmap is already smaller than our required dimension, no need to resize it
                return bitmap;
            }
            return getResizedBitmap(bitmap, newWidth, newHeight);
        } catch (Exception e) {
            shortToast("Error updating the image");
            return bitmap;
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        matrix.postRotate(0);
        //matrix.setRotate(degreesAngle, scaleWidth, scaleHeight);
        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return resizedBitmap;
    }
}