package com.example.myapplication;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.app.Service.START_NOT_STICKY;
import static java.util.concurrent.TimeUnit.SECONDS;


public class ForegroundService extends Service {
  /*      Random random;
    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private static final int NOTIFY_ID = 101;

    List<String> messages = new ArrayList<>();

        public static final String CHANNEL_ID = "ForegroundServiceChannel";
        @Override
        public void onCreate() {
            super.onCreate();
            random = new Random();

            messages.add("dash");
            messages.add("koop");
            messages.add("qwerty");
            messages.add("maza");
            messages.add("hello");
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            return flags;
        }*/
  private static final String TAG = ForegroundService.class.getSimpleName();
    public static final int NOTIFICATION_ID = 234;

    private Handler mHandler;

    public ForegroundService(String name) {
        super();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void createNotification(Context context){

        Intent intent = new Intent(context, MainActivity.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addNextIntent(intent);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Sample Notification")
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);

    }
}