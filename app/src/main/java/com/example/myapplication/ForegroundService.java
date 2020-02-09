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
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;


public class ForegroundService extends Service {
    private static final int NOTIFY_ID = 101;
    Random random;


    private NotificationManager notificationManager;

    List<String> messages = new ArrayList<>();
    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private Timer timer;
    final  int REFRESH=0;
    Context context;
    private PendingIntent pendingIntent;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        messages.add("dash");
        messages.add("koop");
        messages.add("qwerty");
        messages.add("maza");
        messages.add("hello");
        context=this;
        random = new Random();


        //==============================================

        TimerTask refresher;
        // Initialization code in onCreate or similar:
        timer = new Timer();
        refresher = new TimerTask() {
            public void run() {
                handler.sendEmptyMessage(0);
            };
        };
        // first event immediately,  following after 1 seconds each
        timer.scheduleAtFixedRate(refresher, 5,500000);
        //=======================================================

    }

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {

        @RequiresApi(api = Build.VERSION_CODES.M)
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH:
                    scheduler.scheduleWithFixedDelay(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void run() {
                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                    .setSmallIcon(R.drawable.ic_launcher_background)
                                    .setContentTitle("Напоминание")
                                    .setDefaults(Notification.DEFAULT_SOUND)
                                    .setAutoCancel(true)
                                    .setOnlyAlertOnce(true)
                                    .setContentText(messages.get(random.nextInt(messages.size())));

                    notificationManager = getSystemService(NotificationManager.class);
                    notificationManager.notify(NOTIFY_ID, builder.build());
                        }
                    }, 5, 5, SECONDS);
                    break;
                default:
                    break;
            }
        }
    };
}
