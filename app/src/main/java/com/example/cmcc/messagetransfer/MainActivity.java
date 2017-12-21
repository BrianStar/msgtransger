package com.example.cmcc.messagetransfer;

import android.Manifest;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmcc.messagetransfer.mbinder.SmsBinder;
import com.example.cmcc.messagetransfer.service.KeepAliveService;
import com.example.cmcc.messagetransfer.service.SmsService;
import com.example.cmcc.messagetransfer.utils.SpUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private EditText etPhone;
    private TextView mTvPhone;
    private Button mBtnStart;
    private ServiceConnection connection;
    private SmsBinder smsBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPhone = (EditText) findViewById(R.id.et_phone);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(this);

        initView();
        //initUI();
        checkAppPermission();
        startAliveService();
    }

    private void startAliveService() {
        startService(new Intent(MainActivity.this, KeepAliveService.class));
    }

    private void initView() {
        String phone = SpUtils.getKeyValue(MainActivity.this, "phone");
        if (TextUtils.isEmpty(phone)) {
            bind();
        } else {
            etPhone.setText(phone);
            mTvPhone.setText(phone);
            unbind();
        }
    }


    private void bind(){
        etPhone.setVisibility(View.VISIBLE);
        //etPhone.setSelection(etPhone.getText().length());
        mTvPhone.setVisibility(View.GONE);
        mBtnStart.setText("绑定");
    }

    private void unbind(){
        etPhone.setVisibility(View.GONE);
        mTvPhone.setVisibility(View.VISIBLE);
        mBtnStart.setText("解绑");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
                if (etPhone.getVisibility() == View.VISIBLE) {
                    String s = etPhone.getText().toString();
                    if (!TextUtils.isEmpty(s) && s.length() == 11) {
                        //手机号
                        SpUtils.putKeyValue(MainActivity.this, "phone", s);
                        mTvPhone.setText(s);
                        unbind();
                    }
                } else {
                    SpUtils.putKeyValue(MainActivity.this, "phone", "");
                    bind();
                }

                //testReadSMS();
                break;
        }

    }

    /**
     * 读取短信
     */
    public void testReadSMS() {
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://sms/");
        String[] projection = {"thread_id AS group_id", "address AS contact", "body AS msg_content", "date", "type"};
        Cursor c = resolver.query(uri, projection, null, null, "date DESC");    //查询并按日期倒序
        if (c == null){
            return;
        }

        while (c.moveToNext()) {
            String groupId = "groupId: " + c.getInt(c.getColumnIndex("group_id"));
            String contact = "contact: " + c.getString(c.getColumnIndex("contact"));
            String msgContent = "msgContent: " + c.getString(c.getColumnIndex("msg_content"));
            //String date = "date: " + dateFormat.format(c.getLong(c.getColumnIndex("date")));
            //String type = "type: " + getTypeById(c.getInt(c.getColumnIndex("type")));

            Log.e(TAG,msgContent);

        }
        c.close();
    }


    private void checkAppPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS},
                    100);
        }else {
            Log.i(TAG,"权限已经授予！");
        }

    }

    private void initUI() {
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                smsBinder = (SmsBinder) service;
                Toast.makeText(getApplicationContext(), "服务已经连接!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Toast.makeText(getApplicationContext(), "服务已经断开!", Toast.LENGTH_SHORT).show();
            }
        };
        Intent intent = new Intent(this, SmsService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unbindService(connection);
    }

    public void openIntercept(View view) {
        smsBinder.callOpenIntercept();
    }

    public void closeIntercept(View view) {
        smsBinder.callCloseIntercept();
    }

    public void getInterceptStatus(View view) {
        smsBinder.callGetInterceptStatus();
    }
}
