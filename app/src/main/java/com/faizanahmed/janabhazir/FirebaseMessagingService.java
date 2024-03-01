package com.faizanahmed.janabhazir;

import static android.provider.Settings.System.getString;

import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService {

    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM message,
        // here is where that should be initiated.
     //   sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }



}
