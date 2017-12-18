package com.example.cmcc.messagetransfer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

import com.example.cmcc.messagetransfer.utils.MessageUtils;
import com.example.cmcc.messagetransfer.utils.SpUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ADBrian on 18/12/2017.
 */

public class TransmitReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String phone = SpUtils.getKeyValue(context, "phone");
        if (TextUtils.isEmpty(phone)) {
            return;
        }
        Bundle bundle = intent.getExtras();
        SmsMessage msg = null;
        if (null != bundle) {
            Object[] smsObj = (Object[]) bundle.get("pdus");
            for (Object object : smsObj) {
                msg = SmsMessage.createFromPdu((byte[]) object);
                Date date = new Date(msg.getTimestampMillis());//时间
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String receiveTime = format.format(date);
                String number = msg.getOriginatingAddress();
                String message = msg.getDisplayMessageBody();
                message = "转发短信来自："+number+"\n"+"转发短信内容：["+message+"\n"+
                        "]转发短信时间："+receiveTime;
                Log.i("noco",message);
                MessageUtils.sendSMS(phone,message,context);
            }
        }
    }
}
