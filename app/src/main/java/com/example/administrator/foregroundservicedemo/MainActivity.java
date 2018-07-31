package com.example.administrator.foregroundservicedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText inputEditText;
    private boolean whetherTheCustomServiceHasStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 先判斷CustomService 是否已經啟動, 如果尚未啟動 才會進行啟動
     */
    public void startService(View view) {
        whetherTheCustomServiceHasStarted = CustomApplication
                .isServiceRunning(this,
                        "com.example.administrator.foregroundservicedemo.CustomService");
        if (whetherTheCustomServiceHasStarted == false) {
            Intent serviceIntent = new Intent(this, CustomService.class);
            startService(serviceIntent);
        }
    }

    /** 結束CustomService */
    public void stopService(View view) {
        Intent serviceIntent = new Intent(this, CustomService.class);
        stopService(serviceIntent);
    }
}
