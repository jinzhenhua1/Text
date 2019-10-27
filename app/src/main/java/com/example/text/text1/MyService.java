package com.example.text.text1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.text.MainActivity;

public class MyService extends Service {

    private final static String TAG = MyService.class.getSimpleName();
    // 启动notification的id，两次启动应是同一个id
    private final static int NOTIFICATION_ID = android.os.Process.myPid();
//    private AssistServiceConnection mServiceConnection;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 设置为前台进程，降低oom_adj，提高进程优先级，提高存活机率
        setForeground();

        return START_STICKY;

    }

    // 要注意的是android4.3之后Service.startForeground() 会强制弹出通知栏，解决办法是再
    // 启动一个service和推送共用一个通知栏，然后stop这个service使得通知栏消失。
    private void setForeground() {
//        if (Build.VERSION.SDK_INT < 18)
//        {
            Log.e(TAG,"设置为前台服务");
            startForeground(NOTIFICATION_ID, getNotification());
            return;
//        }

//        if (mServiceConnection == null)
//        {
//            mServiceConnection = new AssistServiceConnection();
//        }
        // 绑定另外一条Service，目的是再启动一个通知，然后马上关闭。以达到通知栏没有相关通知
        // 的效果
//        bindService(new Intent(this, AssistService.class), mServiceConnection, Service.BIND_AUTO_CREATE);
    }


    private Notification getNotification()
    {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager);//安卓8.0以上
        }

        Intent intent = new Intent(this, MainActivity.class);//点击之后跳转到哪
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "")
                .setContentTitle("服务运行于前台")
                .setContentText("service被设为前台进程")
                .setTicker("service正在后台运行...")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setWhen(System.currentTimeMillis())
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("YOUR_CHANNEL_ID001");
        }

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        return notification;
    }


    /**
     * 解决android8.0不能弹出notification的问题
     * @param notificationManager
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel(NotificationManager notificationManager) {
        NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID001","YOUR_CHANNEL_NAME",NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);
    }

}
