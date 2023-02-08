package com.drpashu.sdk.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.drpashu.sdk.R;
import com.drpashu.sdk.activity.HomeActivity;

public class NotificationHelper extends ContextWrapper {

    private NotificationManagerCompat notificationManager;
    private NotificationManager notificationDeviceManager;
    long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500,
                      500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500,
                      500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500};

    private static final String APP_CHANNEL_ID = "APP_NOTIFICATION";
    private static final String CALL_CHANNEL_ID = "CALL_NOTIFICATION";
    private static final String SILENT_CHANNEL_ID = "SILENT_NOTIFICATION";
    private static final String CALL_ONGOING_CHANNEL_ID = "CALL_ONGOING_NOTIFICATION";

    public NotificationHelper(Context context) {
        super(context);
        notificationManager = NotificationManagerCompat.from(context);
        notificationDeviceManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private void createCallNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            Uri sounduri;
            if (Build.MANUFACTURER.equalsIgnoreCase("realme") || Build.MANUFACTURER.equalsIgnoreCase("oneplus")) {
                sounduri = Uri.parse("android.resource://" +
                        getApplicationContext().getPackageName() +
                        "/" +
                        R.raw.call_ringtone);
            }
            else {
                sounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
            
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .build();
//                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                    .build();

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(CALL_CHANNEL_ID, "Call notifications", importance);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(pattern);
            notificationChannel.setSound(sounduri, audioAttributes);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createCallOngoingNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(CALL_ONGOING_CHANNEL_ID, "Ongoing call notifications", importance);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(APP_CHANNEL_ID, "Dr Pashu", importance);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public NotificationCompat.Builder getCallNotification(String title, String description, String callStatus, String callId, String notificationId, String channelId,
                                                          String firstName, String lastName, String profileImg, String unixNotificationTime){
        createCallNotificationChannel();

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("call", callStatus);
        intent.putExtra("callId", callId);
        intent.putExtra("notificationId", notificationId);
        intent.putExtra("channelId", channelId);
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        intent.putExtra("profile_picture", profileImg);
        intent.putExtra("unixNotificationTime", unixNotificationTime);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);  // Make click action send data, replace with 0

        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.call_notification_icon);

        return new NotificationCompat.Builder(getApplicationContext(), CALL_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setWhen(0)
                .setTimeoutAfter(45000) // after this time the notification auto dismiss
                .setAutoCancel(false) // will not dismiss notification on click
                .setOngoing(true) //notification will not be dismissed
                .setFullScreenIntent(pendingIntent, true);

//              .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//              .setStyle(new NotificationCompat.BigPictureStyle()       // used to display the expandable image inside notification
//              .bigPicture(icon))

        //Full screen intents allows us to show notification on the top of the screen
        //It will not remove until user's action
    }

    public NotificationCompat.Builder getCallOngoingNotification(String title, String description, String callStatus, String callId,
                                                                 String notificationId, String channelId, String firstName, String lastName){
        createCallOngoingNotificationChannel();

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("call", callStatus);
        intent.putExtra("callId", callId);
        intent.putExtra("notificationId", notificationId);
        intent.putExtra("channelId", channelId);
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);  // Make click action send data, replace with 0

        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.register);

        return new NotificationCompat.Builder(getApplicationContext(), CALL_ONGOING_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setAutoCancel(false) // will not dismiss notification on click
                .setOngoing(true) //notification will not be dismissed
                .setContentIntent(pendingIntent);
    }

    public NotificationCompat.Builder getNotification(String title, String description, String callStatus, String screen, String callId) {
        createNotificationChannel();

        if (screen.equalsIgnoreCase("call_history")){
            Intent intent = new Intent("missed_call");
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }

        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("screen", screen);
        intent.putExtra("call", callStatus);
        intent.putExtra("callId", callId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        return new NotificationCompat.Builder(getApplicationContext(), APP_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(description)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);
    }

    public NotificationCompat.Builder getSilentNotification(String title, String description) {
        Intent intent = new Intent("vet_call_decline");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

        return new NotificationCompat.Builder(getApplicationContext(), SILENT_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(description)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    public void notify(int notificationId, NotificationCompat.Builder notification){ // notificationId is a unique int for each notification that you must define
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            notificationManager.notify(notificationId, notification.build());
        else
            notificationDeviceManager.notify(notificationId, notification.build());
    }
}