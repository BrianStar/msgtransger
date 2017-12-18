package com.example.cmcc.messagetransfer.observer;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Message;
import android.util.Log;

import com.example.cmcc.messagetransfer.entity.SmsBean;
import com.example.cmcc.messagetransfer.handler.SmsHandler;


/**
 * Created by ye on 2017/5/23.
 */

public class SmsObserver extends ContentObserver {
    private final String TAG = SmsObserver.class.getSimpleName();
    private ContentResolver resolver;
    private SmsHandler handler;


    public SmsObserver(ContentResolver resolver, SmsHandler handler) {
        super(handler);
        this.resolver = resolver;
        this.handler = handler;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Log.i(TAG, "短信有改变了！");
        //Cursor mCursor = resolver.query(Uri.parse("content://sms/inbox"),
        //        new String[]{"_id", "address", "read", "body", "thread_id"},
        //        "read=?", new String[]{"0"}, "date desc");

        Cursor mCursor = resolver.query(Uri.parse("content://sms/inbox"),
                new String[] { "_id", "address", "read", "body", "thread_id" },
                "read=?", new String[] { "1" }, "date desc");

        if (mCursor == null) {
            return;
        } else {
            while (mCursor.moveToNext()) {
                SmsBean smsBean = new SmsBean();

                int _inIndex = mCursor.getColumnIndex("_id");
                if (_inIndex != -1) {
                    smsBean.set_id(mCursor.getString(_inIndex));
                }

                int thread_idIndex = mCursor.getColumnIndex("thread_id");
                if (thread_idIndex != -1) {
                    smsBean.setThread_id(mCursor.getString(thread_idIndex));
                }

                int addressIndex = mCursor.getColumnIndex("address");
                if (addressIndex != -1) {
                    smsBean.setSmsAddress(mCursor.getString(addressIndex));
                }

                int bodyIndex = mCursor.getColumnIndex("body");
                if (bodyIndex != -1) {
                    smsBean.setSmsBody(mCursor.getString(bodyIndex));
                }

                int readIndex = mCursor.getColumnIndex("read");
                if (readIndex != -1) {
                    smsBean.setRead(mCursor.getString(readIndex));
                }

                // 根据你的拦截策略，判断是否不对短信进行操作;将短信设置为已读;将短信删除
                // TODO
                Log.i(TAG, "..." + smsBean.toString());
                Message msg = handler.obtainMessage();
                smsBean.setAction(0);// 0不对短信进行操作;1将短信设置为已读;2将短信删除
                msg.obj = smsBean;
                handler.sendMessage(msg);
            }
        }

        if (mCursor != null) {
            mCursor.close();
        }
    }
}
