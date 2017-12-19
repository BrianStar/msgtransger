package com.example.cmcc.messagetransfer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.cmcc.messagetransfer.SmsApp;

/**
 * Created by ADBrian on 19/12/2017.
 */

public class KeepAliveReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            SmsApp.getInstance().startKeepAliveActivity(context);
        } else if (action.equals(Intent.ACTION_USER_PRESENT)){
            SmsApp.getInstance().finishKeepAliveActivity();
        }
    }
}
