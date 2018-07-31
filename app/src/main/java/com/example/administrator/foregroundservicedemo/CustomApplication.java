package com.example.administrator.foregroundservicedemo;

import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CustomApplication extends Application {

    public static final String CHANNEL_ID = "examplesServiceChannel";
    public static final String CHANNEL2_ID = "examplesServiceChannel2";
    public static final String CHANNEL3_ID = "examplesServiceChannel3";
    private List<NotificationChannel> notificationChannels;

    @Override
    public void onCreate() {
        super.onCreate();

        notificationChannels = new ArrayList<NotificationChannel>();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {                                      // Build.VERSION.SDK_INT是系统的版本

            /** 获取NotificationManager, 並創建通知渠道 */
            NotificationChannel serviceNotificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Examples Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT                                      // IMPORTANCE_HIGH, 开启通知会弹出, 发出提示音, 状态栏中显示
            );
            NotificationChannel serviceNotificationChannel2 = new NotificationChannel(
                    CHANNEL2_ID,
                    "Examples Service Channel2",
                    NotificationManager.IMPORTANCE_DEFAULT                                      // IMPORTANCE_HIGH, 开启通知会弹出, 发出提示音, 状态栏中显示
            );
            NotificationChannel serviceNotificationChannel3 = new NotificationChannel(
                    CHANNEL3_ID,
                    "Examples Service Channel3",
                    NotificationManager.IMPORTANCE_DEFAULT                                      // IMPORTANCE_HIGH, 开启通知会弹出, 发出提示音, 状态栏中显示
            );

            notificationChannels.add(serviceNotificationChannel);
            notificationChannels.add(serviceNotificationChannel2);
            notificationChannels.add(serviceNotificationChannel3);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannels(notificationChannels);
        }
    }


    /**
     * 判断服务是否启动,context上下文对象 ，className服务的name
     *
     * @param mContext
     * @param className
     * @return
     */
    public static boolean isServiceRunning(Context mContext, String className) {

        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(30);

        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {

            Log.v("more", "getClassName: " + serviceList.get(i).service.getClassName());

            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}
