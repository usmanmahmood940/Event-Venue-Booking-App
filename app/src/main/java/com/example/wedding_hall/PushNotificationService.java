package com.example.wedding_hall;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PushNotificationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        String title = message.getNotification().getTitle();
        String text = message.getNotification().getBody();
        String icon = "https://firebasestorage.googleapis.com/v0/b/wedding-hall-99aa0.appspot.com/o/1653776457432.jpg?alt=media&token=b8d4fe60-2e2f-4baf-873e-e9921e9c59f6";
        final String Channel_ID = "HEADS_UP NOTIFICATION";
        NotificationChannel channel = new NotificationChannel(
                Channel_ID,
                "Heads Up Notification",
                NotificationManager.IMPORTANCE_HIGH
        );
        getSystemService(NotificationManager.class).createNotificationChannel(channel);

        Intent resultIntent;
        if(AllData.objUser.getCategory().equals("user")){
            resultIntent = new Intent(this,HomeActivity.class);
            resultIntent.putExtra("position",4);
        }
        else{
            resultIntent = new Intent(this,Vendor_Notification.class);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this,1,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder notification = new Notification.Builder(this,Channel_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.icon)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat.from(this).notify(1,notification.build());
        super.onMessageReceived(message);
    }
}
