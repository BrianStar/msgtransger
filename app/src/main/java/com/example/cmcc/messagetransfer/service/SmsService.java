package com.example.cmcc.messagetransfer.service;

import android.app.Notification;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.cmcc.messagetransfer.R;
import com.example.cmcc.messagetransfer.handler.SmsHandler;
import com.example.cmcc.messagetransfer.mbinder.SmsBinder;
import com.example.cmcc.messagetransfer.observer.SmsObserver;

/**
 * 开启服务监听短信数据库
 * Created by ye on 2017/5/23.
 */
public class SmsService extends Service {
    private final String TAG = SmsService.class.getSimpleName();

    private SmsObserver smsObserver;

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            startForeground(250, builder.build());
        } else {
            startForeground(250, new Notification());
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new smsAgent();
    }

    private void openInter() {
        ContentResolver resolver = getContentResolver();
        smsObserver = new SmsObserver(resolver, new SmsHandler(this));
        resolver.registerContentObserver(Uri.parse("content://sms"), true, smsObserver);
        Toast.makeText(getApplicationContext(), "短信监听服务已经启动！", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "短信监听服务已经启动");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.getContentResolver().unregisterContentObserver(smsObserver);
        Toast.makeText(getApplicationContext(), "短信监听服务已经关闭！", Toast.LENGTH_SHORT).show();
        Process.killProcess(Process.myPid());
    }

    private void closeInter() {
    }

    private void getInterStatus() {
        Log.i(TAG, "getInterStatus...");
    }


    public class smsAgent extends Binder implements SmsBinder {

        @Override
        public void callOpenIntercept() {
            openInter();
        }

        @Override
        public void callCloseIntercept() {
            closeInter();
        }

        @Override
        public void callGetInterceptStatus() {
            getInterStatus();
        }
    }
}
