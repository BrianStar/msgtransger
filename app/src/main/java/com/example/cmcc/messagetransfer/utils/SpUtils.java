package com.example.cmcc.messagetransfer.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cmcc on 17-12-15.
 */

public class SpUtils {

    public static final String FILE_NAME = "share_data";

    public static void putKeyValue(Context context,String key,String value){
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key,value);
        edit.commit();
    }

    public static String getKeyValue(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }
}
