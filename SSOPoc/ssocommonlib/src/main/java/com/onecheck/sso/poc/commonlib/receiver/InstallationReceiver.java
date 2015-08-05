package com.onecheck.sso.poc.commonlib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.onecheck.sso.poc.commonlib.utils.Logger;

/**
 * Created by deepak on 5/8/15.
 */
public class InstallationReceiver extends BroadcastReceiver {

    public static String ACTION = "com.onecheck.sso.poc.commonlib.receiver.InstallationReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (ACTION.equals(intent.getAction())) {
            Logger.log("Action of intent received by pkg is  : " + intent.getAction());
        }
    }

}
