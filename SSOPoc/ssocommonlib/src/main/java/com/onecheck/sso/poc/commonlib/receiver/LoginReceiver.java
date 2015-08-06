package com.onecheck.sso.poc.commonlib.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.onecheck.sso.poc.commonlib.db.PreferencesManager;
import com.onecheck.sso.poc.commonlib.utils.Logger;

public class LoginReceiver extends BroadcastReceiver{

    public static String ACTION = "com.onecheck.sso.poc.commonlib.receiver.LoginReceiver";
    public static String INTENT_PACKAGENAME_ARG="INTENT_PACKAGE_NAME";

    @Override
    public void onReceive(Context context, Intent intent) {

        if(ACTION.equals(intent.getAction())){

            //check for null
            //Todo check receiving from known packages
            if(intent.hasExtra(INTENT_PACKAGENAME_ARG)){

                Logger.log("Login interrupt by"+intent.getStringExtra(INTENT_PACKAGENAME_ARG));
                PreferencesManager preferencesManager=PreferencesManager.getInstance(context);
                preferencesManager.writeToPrefs(PreferencesManager.LOGIN_DATA_PROV_PACKAGE_NAME,
                        intent.getStringExtra(INTENT_PACKAGENAME_ARG));

            }
        }
            else {
              Logger.log("Interrupt not registered");
        }

    }

}
