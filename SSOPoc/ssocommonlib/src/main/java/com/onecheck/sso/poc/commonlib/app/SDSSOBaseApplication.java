package com.onecheck.sso.poc.commonlib.app;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.widget.Toast;

import com.onecheck.sso.poc.commonlib.utils.Logger;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

import one.sdsso.deepak.com.sdssolib.provider.CustomParseUser;

/**
 * Created by deepak on 15/4/15.
 */
public class SDSSOBaseApplication extends Application {

    //    public final static String SIGNATURE = "3082030d308201f5a00302010202041c1d0345300d06092a864886f70d01010b05003037310b30090603550406130255533110300e060355040a1307416e64726f6964311630140603550403130d416e64726f6964204465627567301e170d3134303732333036343632325a170d3434303731353036343632325a3037310b30090603550406130255533110300e060355040a1307416e64726f6964311630140603550403130d416e64726f696420446562756730820122300d06092a864886f70d01010105000382010f003082010a02820101009dc2b311c7ca8bb902a0789cd440f43cc5ecaec938edbbffc847dd53bf337fa7a246079dd89c604eb7a68b559858cbebd0dbf4893966f23ef308329379928e681ceba14c79fd198212baeba1ad37f57fdd57a23d08472acc3377f32d462ead21478ad90f9311100c54363253f0d5afafadd3c105dff8297e7e5ed5073aef221354786716542ead8b8fa44ac6d6314b6a8ed8662a82313e8a15a37f6ad03edfb7ced0c05df9be4fd1a916761d35d09e1236d3c6cf835f58d8550e23cee827b57bbdabf024c5a5d10cd5ae787e574180f5d7fab0296e5975ab7702f95db5a1ba7deac4ba0b5d168b31b55152430437377efe6603b8b4c3971731aeaf995e80cbd50203010001a321301f301d0603551d0e04160414395aecee91c66f6626d76ad3733dbe6f62d5c758300d06092a864886f70d01010b05000382010100975a412f83c2166954de7cef2fb59fd0898f34db25ba87435cf407c92d4259bfe00aac2544bfbcb33e9046dbb3f99ebdd8d2cd930bd0d6389166dc57ed9139e2bdc4346e3bdc02662f035944a141bbc5392be7a1f8099026037295159fadbfbc7f7216748499dd1254c4f87e560d5d8c51cb2ba1c8e9e722beb31aa5553e9686afeef3a5a40d84d6e3f88c41ba1176b2cb11c25180876215c7db70badc1f495eb61b3483cba7ff2a2f835c05e9c2e0fcd1e00f3b6c1d5bfffca40752751c7a652ddc91988bdc717f0175d88e5888f6a3fae6a38ba2c929d341300a41b8678757499a3e4af1371bf53816371b7d6efba5d3995eebacdba6762fa66d34740ca537";
    public final static String SIGNATURE = "3082030d308201f5a00302010202041c1d0345300d06092a864886f70d01010b05003037310b30090603550406130255533110300e060355040a1307416e64726f6964311630140603550403130d416e64726f6964204465627567301e170d3134303732333036343632325a170d3434303731353036343632325a3037310b30090603550406130255533110300e060355040a1307416e64726f6964311630140603550403130d416e64726f696420446562756730820122300d06092a864886f70d01010105000382010f003082010a02820101009dc2b311c7ca8bb902a0789cd440f43cc5ecaec938edbbffc847dd53bf337fa7a246079dd89c604eb7a68b559858cbebd0dbf4893966f23ef308329379928e681ceba14c79fd198212baeba1ad37f57fdd57a23d08472acc3377f32d462ead21478ad90f9311100c54363253f0d5afafadd3c105dff8297e7e5ed5073aef221354786716542ead8b8fa44ac6d6314b6a8ed8662a82313e8a15a37f6ad03edfb7ced0c05df9be4fd1a916761d35d09e1236d3c6cf835f58d8550e23cee827b57bbdabf024c5a5d10cd5ae787e574180f5d7fab0296e5975ab7702f95db5a1ba7deac4ba0b5d168b31b55152430437377efe6603b8b4c3971731aeaf995e80cbd50203010001a321301f301d0603551d0e04160414395aecee91c66f6626d76ad3733dbe6f62d5c758300d06092a864886f70d01010b05000382010100975a412f83c2166954de7cef2fb59fd0898f34db25ba87435cf407c92d4259bfe00aac2544bfbcb33e9046dbb3f99ebdd8d2cd930bd0d6389166dc57ed9139e2bdc4346e3bdc02662f035944a141bbc5392be7a1f8099026037295159fadbfbc7f7216748499dd1254c4f87e560d5d8c51cb2ba1c8e9e722beb31aa5553e9686afeef3a5a40d84d6e3f88c41ba1176b2cb11c25180876215c7db70badc1f495eb61b3483cba7ff2a2f835c05e9c2e0fcd1e00f3b6c1d5bfffca40752751c7a652ddc91988bdc717f0175d88e5888f6a3fae6a38ba2c929d341300a41b8678757499a3e4af1371bf53816371b7d6efba5d3995eebacdba6762fa66d34740ca537";

    @Override
    public void onCreate() {
        super.onCreate();

        initParseSDK();

        PackageManager pm = getPackageManager();

        try {
            PackageInfo pinf = pm.getPackageInfo(this.getPackageName(), PackageManager.GET_SIGNATURES);
            boolean match = false;
            for (Signature sig : pinf.signatures) {
                if (SIGNATURE.equals(sig.toCharsString())) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                Logger.log("App is not signed using the correct Keystore. Not allowing to continue.");
                Toast.makeText(this, "App is not signed using the correct Keystore. Not allowing to continue.", Toast.LENGTH_LONG).show();
                System.exit(0);
            }
        } catch (PackageManager.NameNotFoundException nnfex) {
            Logger.log("Error in getting the signature of the app.");
        }

    }

    private void initParseSDK() {
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        ParseObject.registerSubclass(CustomParseUser.class);

        // Add your initialization code here
        Parse.initialize(this, "NbEpQiyjZmH8b4OkHN8kpNSjuhpWfyOsKqOVQkV1", "1E2bVxBHPDLX3m2LF8DbMb0qSPB5TqJgHoNLNpxc");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }

}
