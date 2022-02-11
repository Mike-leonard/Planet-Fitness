package com.leonard.healthmanager.general;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
//import com.anjlab.android.iab.p004v3.BillingProcessor;
//import com.anjlab.android.iab.p004v3.BillingProcessor.IBillingHandler;
//import com.anjlab.android.iab.p004v3.TransactionDetails;
/*import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;*/
import com.google.android.gms.ads.MobileAds;
import com.leonard.healthmanager.MainActivity;
import com.leonard.healthmanager.R;
import com.leonard.healthmanager.utils.GlobalFunction;
import com.leonard.healthmanager.utils.SharedPreferenceManager;

import java.util.Calendar;

import static com.leonard.healthmanager.AdConstantControl.adNetworkIntializeRequest;

public class Splash extends Activity  {
    String TAG = getClass().getSimpleName();
    //BillingProcessor billingProcessor;
    GlobalFunction globalFunction;
    SharedPreferenceManager sharedPreferenceManager;
    public static boolean isGoogleAdEnabled = true;

 /*   public void onBillingError(int i, Throwable th) {
    }

    public void onProductPurchased(String str *//*TransactionDetails transactionDetails*//*) {
    }

    public void onPurchaseHistoryRestored() {
    }*/


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.globalFunction = new GlobalFunction(this);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        adNetworkIntializeRequest(Splash.this);
        setContentView(R.layout.splash);

        Calendar rightNow = Calendar.getInstance();
        if ((rightNow.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) ||
                (rightNow.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) ||
                (rightNow.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) ||
                (rightNow.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)||
                (rightNow.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) ||
                (rightNow.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)
        ) {
            isGoogleAdEnabled = true;
        } else {   // google ad disabled else statement
            isGoogleAdEnabled = false;
        }

        if (this.globalFunction.isConnectingToInternet()) {
            //this.billingProcessor = new BillingProcessor(this, getString(R.string.base64), this);
        }
        this.globalFunction.set_locale_language();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Splash.this.startActivity(new Intent(Splash.this.getApplicationContext(), MainActivity.class));
                Splash.this.finish();
            }
        }, (long)2000);
    }

    /*public void onBillingInitialized() {
        if (!this.billingProcessor.loadOwnedPurchasesFromGoogle()) {
            return;
        }
        if (this.billingProcessor.isPurchased("remove_ad")) {
            this.sharedPreferenceManager.set_Remove_Ad(Boolean.valueOf(true));
        } else {
            this.sharedPreferenceManager.set_Remove_Ad(Boolean.valueOf(false));
        }
    }*/
}
