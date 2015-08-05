package com.onecheck.sso.poc.commonlib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.onecheck.sso.poc.commonlib.db.PreferencesManager;
import com.onecheck.sso.poc.commonlib.utils.Logger;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by deepak on 5/8/15.
 */
public class InstallationReceiver extends BroadcastReceiver {

    public static final String ACTION = "com.onecheck.sso.poc.commonlib.receiver.InstallationReceiver";
    public static final String INTENT_EXTRA_PKG_NAME = "_INTENT_EXTRA_PKG_NAME";

    private final String INSTALLED_PKGS_CLASS_NAME = "InstalledPackages";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (ACTION.equals(intent.getAction())) {
            Logger.log("Action of intent received by pkg is  : " + intent.getAction());

            if (intent.hasExtra(INTENT_EXTRA_PKG_NAME)) {
                String pkgname = intent.getStringExtra(INTENT_EXTRA_PKG_NAME);
                Logger.log("Pkg installed : " + pkgname);
                PreferencesManager pm = PreferencesManager.getInstance(context);

                ParseObject installedPkgs = new ParseObject(INSTALLED_PKGS_CLASS_NAME);
                installedPkgs.put(pkgname, true);
                installedPkgs.pinInBackground();


                ParseQuery<ParseObject> query = ParseQuery.getQuery(INSTALLED_PKGS_CLASS_NAME);
                query.fromLocalDatastore();
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(final List<ParseObject> scoreList, ParseException e) {
                        if (e == null) {
                            // Results were successfully found from the local datastore.
                        } else {
                            Logger.log("Got the list of installed packages");
                        }
                    }
                });


            }
            else {
                Logger.log("PKG NAME NOT SPECIFIED by the caller. Doing nothing");
            }
        }
    }

}
