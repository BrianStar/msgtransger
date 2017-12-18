package com.example.cmcc.messagetransfer.mbinder;

/**
 * Created by ye on 2017/5/23.
 */

public interface SmsBinder {

    public void callOpenIntercept();

    public void callCloseIntercept();

    public void callGetInterceptStatus();
}
