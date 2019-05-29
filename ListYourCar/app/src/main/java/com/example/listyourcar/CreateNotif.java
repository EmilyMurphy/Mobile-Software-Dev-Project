package com.example.listyourcar;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class CreateNotif extends AppCompatActivity {
private NotificationManagerCompat notificationManager;

    // different notification channels
    public static final String CHANNEL_1 = "channel 1";
    public static final String CHANNEL_2 = "channel 2";

    EditText t,b;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_notif);
        createNotificationChannel();
        notificationManager = NotificationManagerCompat.from(this);
        t =(EditText)findViewById(R.id.t);
        b =(EditText)findViewById(R.id.b);
        Button b1=(Button)findViewById(R.id.button);
        Button b2=(Button)findViewById(R.id.button2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                addNotification();

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                addNotification2();
            }
        });
    }

    public void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String description = "desc";
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1, "channel 1", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription(description);

            NotificationChannel channel2 = new NotificationChannel(CHANNEL_2, "channel 2", NotificationManager.IMPORTANCE_HIGH);
            channel2.setDescription(description);

            NotificationManager Manager = getSystemService(NotificationManager.class);

            Manager.createNotificationChannel(channel1);
            Manager.createNotificationChannel(channel2);
        }
    }

    // Creates and displays a notification
    public void addNotification() {

        String title= t.getText().toString().trim();
        String body= b.getText().toString().trim();
        // Builds your notification
        Notification mBuilder = new NotificationCompat.Builder(this,CHANNEL_1)
                .setSmallIcon(R.drawable.bell)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH).build();

        // Creates the intent needed to show the notification
// Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Add as notification
// notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, mBuilder);
    }

    //pre-made notification
    public void addNotification2() {

        String title= "List Your Car";
        String body= "Check out the app";
        // Builds your notification
        Notification mBuilder = new NotificationCompat.Builder(this,CHANNEL_2)
                .setSmallIcon(R.drawable.bell)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH).build();

        // Creates the intent needed to show the notification
// Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Add as notification
// notificationId is a unique int for each notification that you must define
        notificationManager.notify(2, mBuilder);
    }

}
