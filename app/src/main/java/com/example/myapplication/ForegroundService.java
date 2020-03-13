package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
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
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.app.Service.START_NOT_STICKY;
import static com.example.myapplication.MainActivity.CHANNEL_ID;

public class ForegroundService extends Service {
    private static final int NOTIFY_ID = 101;
    Random random;
    List<String> messages = new ArrayList<>();
    Timer timer;
    Context context;
    final int REFRESH = 0;
    TimerTask refresher;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("------", "create");
        messages.add("dash");
        messages.add("koop");
        messages.add("qwerty");
        messages.add("maza");
        messages.add("hello");
        random = new Random();

    }


    void mTimer(long delay, long period, Context con) {
        context = con;
        timer = new Timer();
        if (delay != 0) {
            refresher = new TimerTask() {
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            };
            timer.scheduleAtFixedRate(refresher, delay , period);
        }

    }

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH:

                    timer = new Timer();
                    random = new Random();
                    messages.add("dash");
                    messages.add("koop");
                    messages.add("qwerty");
                    messages.add("maza");
                    messages.add("hello");

                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(context, CHANNEL_ID)
                                    .setSmallIcon(R.drawable.ic_launcher_background)
                                    .setContentTitle("Напоминание")
                                    .setDefaults(Notification.DEFAULT_SOUND)
                                    .setAutoCancel(true)
                                    .setOnlyAlertOnce(true)
                                    .setContentText(messages.get(random.nextInt(messages.size())));
                    NotificationManager notificationManager;

                    notificationManager = context.getSystemService(NotificationManager.class);
                    notificationManager.notify(NOTIFY_ID, builder.build());
            }
        }
    };


}
