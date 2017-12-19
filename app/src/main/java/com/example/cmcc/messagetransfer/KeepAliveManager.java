package com.example.cmcc.messagetransfer;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.os.Build;

/**
 * Created by ADBrian on 19/12/2017.
 */

public class KeepAliveManager {

    private static KeepAliveManager instance;

    private KeepAliveManager(){};

    public static KeepAliveManager getInstance() {
        if (instance == null){
            instance = new KeepAliveManager();
        }
        return instance;
    }

    public void setForeground(final Service keepLiveService,final Service innerService){
        final int foregroundPushId = 1;
        if (keepLiveService != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                keepLiveService.startForeground(foregroundPushId,new Notification());
            } else {
                keepLiveService.startForeground(foregroundPushId,new Notification());
                if (innerService != null) {
                    innerService.startForeground(foregroundPushId,new Notification());
                    innerService.stopSelf();
                }
            }
        }
    }
}
