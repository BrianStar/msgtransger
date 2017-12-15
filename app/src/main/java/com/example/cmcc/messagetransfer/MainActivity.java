package com.example.cmcc.messagetransfer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cmcc.messagetransfer.utils.SpUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etPhone;
    private Button mBtnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPhone = findViewById(R.id.et_phone);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:

                String s = etPhone.getText().toString();
                if (!TextUtils.isEmpty(s) && s.length() == 11) {
                    //手机号
                    SpUtils.putKeyValue(MainActivity.this,"phone",s);
                }
                break;
        }

    }
}
