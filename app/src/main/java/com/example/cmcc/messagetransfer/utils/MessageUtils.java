package com.example.cmcc.messagetransfer.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.List;

/**
 * Created by cmcc on 17-12-15.
 */

public class MessageUtils {
    /**
     * 直接调用短信接口发短信
     * @param phoneNumber
     * @param message
     */
    public static void sendSMS(String phoneNumber, String message, Context context){
        //构建一个SMSManager,用来发送短信
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        //自定一两个intent,发送两个隐式意图,这两个隐式意图自己定义
        Intent intent1 =  new Intent("com.example.SENT");;
        Intent intent2 = new Intent("com.example.DELIVERY");
        /*延迟意图 sendTextMessage()方法后边两个参数pi1和pi2
        为pendingIntent 延迟意图这两个延迟意图用来查看短信是否
        发出和接收pi1在发送者发送信息送达基站时,会调用,标志着短信
        已经发出pi2是从基站送达接收者手机时会被调用,标志着短信已
        经由接收者接收,因为发送出去的信息不一定会被接收到,所以pi2
        不一定会被调用,这里的pi1和pi2通过广播的方式,通知短信发送的
        相关信息*/
        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, intent1 , 0);//延迟意图
        PendingIntent deliverPI = PendingIntent.getBroadcast(context, 0, intent2 , 0);
        //拆分短信内容（手机短信长度限制）
        List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {
            smsManager.sendTextMessage(phoneNumber, null, text, sentPI, deliverPI);
        }
    }
}
