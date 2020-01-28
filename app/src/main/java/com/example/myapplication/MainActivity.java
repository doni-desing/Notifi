package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

public class MainActivity extends AppCompatActivity {
    private Button button;
    public static final String CHANNEL_ID = "CHANNEL_ID";
    private static final int NOTIFY_ID = 101;
    Random random;
    final int KEEPUS_NOTIFICATION_ID = 1;
    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    List<String> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        random = new Random();
        messages.add("dash");
        messages.add("koop");
        messages.add("qwerty");
        messages.add("maza");
        messages.add("hello");
        click();
    }
    public void click() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduler.scheduleWithFixedDelay(new Runnable() {
                    @Override
                    public void run() {
                        // Create your notification
                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                                        .setSmallIcon(R.drawable.ic_launcher_background)
                                        .setContentTitle("Напоминание")
                                        .setDefaults(Notification.DEFAULT_SOUND)
                                        .setAutoCancel(true)
                                        .setOnlyAlertOnce(true)
                                        .setContentText(messages.get(random.nextInt(messages.size())))
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        NotificationManagerCompat notificationManager =
                                NotificationManagerCompat.from(MainActivity.this);
                        notificationManager.cancel(KEEPUS_NOTIFICATION_ID);
                        notificationManager.notify(NOTIFY_ID, builder.build());
                    }
                }, 5, 5, SECONDS);
            }
        });
    }
}