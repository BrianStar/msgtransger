package com.example.cmcc.messagetransfer;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by ADBrian on 19/12/2017.
 */

public class KeepAliveActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        Log.e("TAG","KeepAliveActivity");

        Window window = getWindow();
        window.setGravity(Gravity.LEFT|Gravity.TOP);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = 0;
        lp.height = 1;
        lp.width = 1;
        window.setAttributes(lp);

        SmsApp.getInstance().aliveActivity = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SmsApp.getInstance().aliveActivity = null;
    }
}
