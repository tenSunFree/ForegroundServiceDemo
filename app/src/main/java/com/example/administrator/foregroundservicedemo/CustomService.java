package com.example.administrator.foregroundservicedemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.administrator.foregroundservicedemo.CustomApplication.CHANNEL2_ID;
import static com.example.administrator.foregroundservicedemo.CustomApplication.CHANNEL3_ID;
import static com.example.administrator.foregroundservicedemo.CustomApplication.CHANNEL_ID;

public class CustomService extends Service {

    public static CustomService customService;
    public static Timer timer;

    private String
            webViewUrl = "https://zh.wikipedia.org/wiki/%E5%93%88%E5%B0%94%E7%9A%84%E7%A7%BB%E5%8A%A8%E5%9F%8E%E5%A0%A1",
            webViewUrl2 = "https://zh.wikipedia.org/wiki/%E5%9C%B0%E6%B5%B7%E5%82%B3%E8%AA%AA",
            webViewUrl3 = "https://zh.wikipedia.org/wiki/%E5%8D%83%E4%B8%8E%E5%8D%83%E5%AF%BB";
    private int currentInt;
    private Notification notification, notification2, notification3;
    private Bitmap bitmap1, bitmap2, bitmap3;
    private TimerTask timerTask;
    private Resources resources;

    public CustomService() {
        customService = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        /** 將資源中的圖片轉化成所需的Bitmap格式 */
        resources = getResources();
        bitmap1 = BitmapFactory.decodeResource(resources, R.drawable.hauru_no_ugoku_shiro2);
        bitmap2 = BitmapFactory.decodeResource(resources, R.drawable.tales_from_earthsea3);
        bitmap3 = BitmapFactory.decodeResource(resources, R.drawable.sen_to_chihiro_no_kamikakushi2);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        /** 創建3組PendingIntent, 分別包含著不同的webViewUrl */
        Intent mainIntent = new Intent(this, Main2Activity.class);
        mainIntent.putExtra("webViewUrl", webViewUrl);
        PendingIntent pendingIntent = PendingIntent.getActivity(                                       // PendingIntent可以看作是对Intent的包装, 当点击消息时就会向系统发送mainIntent意图, 就算在执行时当前Application已经不存在了, 也能通过存在PendingIntent里的Application的Context 照样执行Intent
                this, 1, mainIntent, 0);
        Intent mainIntent2 = new Intent(this, Main2Activity.class);
        mainIntent2.putExtra("webViewUrl", webViewUrl2);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(                                       // PendingIntent可以看作是对Intent的包装, 当点击消息时就会向系统发送mainIntent意图, 就算在执行时当前Application已经不存在了, 也能通过存在PendingIntent里的Application的Context 照样执行Intent
                this, 2, mainIntent2, 0);
        Intent mainIntent3 = new Intent(this, Main2Activity.class);
        mainIntent3.putExtra("webViewUrl", webViewUrl3);
        PendingIntent pendingIntent3 = PendingIntent.getActivity(                                       // PendingIntent可以看作是对Intent的包装, 当点击消息时就会向系统发送mainIntent意图, 就算在执行时当前Application已经不存在了, 也能通过存在PendingIntent里的Application的Context 照样执行Intent
                this, 3, mainIntent3, 0);

        /** 实例化3個NotificationCompat.Builder对象 */
        notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("霍爾的移動城堡")
                .setContentText("故事內容改編自英國奇幻文學作家黛安娜·韋恩·瓊斯在1986年的著作《魔幻城堡》")
                .setSmallIcon(R.drawable.ic_chat_black_24dp)
                .setLargeIcon(bitmap1)
                .setContentIntent(pendingIntent)
                .build();
        notification2 = new NotificationCompat.Builder(this, CHANNEL2_ID)
                .setContentTitle("地海戰記")
                .setContentText("在多島海世界「地海」裡，「龍」居住於西方，「人」居住於東方。")
                .setSmallIcon(R.drawable.ic_chat_black_24dp)
                .setLargeIcon(bitmap2)
                .setContentIntent(pendingIntent2)
                .build();
        notification3 = new NotificationCompat.Builder(this, CHANNEL3_ID)
                .setContentTitle("神隱少女")
                .setContentText("內容講述一個小女孩誤闖了神祕世界，之後經歷成長的故事。")
                .setSmallIcon(R.drawable.ic_chat_black_24dp)
                .setLargeIcon(bitmap3)
                .setContentIntent(pendingIntent3)
                .build();

        /** 每隔5秒 切換開啟不同的notification的前台服務 */
        startForeground(1, notification);                                                        // 开始前台服务, 参数1 唯一的通知标识, 参数2 通知消息
        currentInt = 1;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                switch (currentInt) {
                    case 1:
                        startForeground(2, notification2);
                        currentInt = 2;
                        break;
                    case 2:
                        startForeground(3, notification3);
                        currentInt = 3;
                        break;
                    case 3:
                        startForeground(1, notification);
                        currentInt = 1;
                        break;
                }
            }
        };
        timer = new Timer();
        timer.schedule(timerTask,
                1000 * 5,                                                                     // 延迟5秒执行
                1000 * 5);                                                                   // 周期时间

        return START_NOT_STICKY;                                                                  // 如果在执行完onStartCommand后, 服务被异常kill掉, 系统不会自动重启该服务
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
