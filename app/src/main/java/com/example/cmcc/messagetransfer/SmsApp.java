package com.example.cmcc.messagetransfer;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ADBrian on 19/12/2017.
 */

public class SmsApp extends Application {

    public KeepAliveActivity aliveActivity;
    private static SmsApp app;

    public static SmsApp getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
    
    public void startKeepAliveActivity(Context context) {
        Intent intent = new Intent(context, KeepAliveActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(intent);
    }

    public void finishKeepAliveActivity() {
        if (aliveActivity != null) {
            aliveActivity.finish();
        }
    }
}
