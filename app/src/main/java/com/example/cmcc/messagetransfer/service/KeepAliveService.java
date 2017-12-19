package com.example.cmcc.messagetransfer.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.cmcc.messagetransfer.KeepAliveManager;
import com.example.cmcc.messagetransfer.receiver.KeepAliveReceiver;

/**
 * Created by ADBrian on 19/12/2017.
 */

public class KeepAliveService extends Service {

    public static KeepAliveService keepAliveService;

    private KeepAliveReceiver mScreenReceiver = new KeepAliveReceiver();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        keepAliveService = this;
        startScreenBroadcastReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startService(new Intent(this,InnerService.class));
        return Service.START_STICKY;
    }

    /**
     * 停止screen状态更新
     */
    public void stopScreenStateUpdate() {
        unregisterReceiver(mScreenReceiver);
    }

    /**
     * 启动screen状态广播接收器
     */
    private void startScreenBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(mScreenReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopScreenStateUpdate();
    }

    public static class InnerService extends Service{

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            KeepAliveManager.getInstance().setForeground(keepAliveService,this);
            return super.onStartCommand(intent, flags, startId);
        }
    }
}
