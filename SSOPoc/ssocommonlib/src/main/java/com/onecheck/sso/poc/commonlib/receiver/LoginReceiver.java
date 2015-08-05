package com.onecheck.sso.poc.commonlib.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.onecheck.sso.poc.commonlib.utils.Logger;

public class LoginReceiver extends BroadcastReceiver{

    public static String ACTION = "com.onecheck.sso.poc.commonlib.receiver.LoginReceiver";
    public static String INTENT_PACKAGENAME_ARG="INTENT_PACKAGE_NAME";

    @Override
    public void onReceive(Context context, Intent intent) {

        if(ACTION.equals(intent.getAction())){

            Logger.log("Login interrupt by"+intent.getStringExtra(INTENT_PACKAGENAME_ARG));
            //start service
        }
            else {
              Logger.log("Interrupt not registered");
        }

    }

}
