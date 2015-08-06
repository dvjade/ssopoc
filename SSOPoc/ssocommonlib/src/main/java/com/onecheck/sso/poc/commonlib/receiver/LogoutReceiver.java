package com.onecheck.sso.poc.commonlib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.onecheck.sso.poc.commonlib.db.PreferencesManager;
import com.onecheck.sso.poc.commonlib.utils.Logger;

/**
 * Created by amit.sharma06 on 06-08-2015.
 */
public class LogoutReceiver extends BroadcastReceiver {

    public static String ACTION="com.onecheck.sso.poc.commonlib.receiver.LogoutReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {


        if(intent.getAction().equals(ACTION)){

            Logger.log("Log out interruption");
            PreferencesManager preferencesManager=PreferencesManager.getInstance(context);
            preferencesManager.deleteString(PreferencesManager.ACCESS_TOKEN);

        }


    }


}
