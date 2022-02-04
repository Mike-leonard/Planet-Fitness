package com.leonard.healthmanager.general;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlarmManager;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.multidex.MultiDex;

import com.leonard.healthmanager.R;
import com.leonard.healthmanager.notification.NotificationBroadcastReciever;

import com.leonard.healthmanager.utils.SharedPreferenceManager;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.ConnectionBuddyConfiguration;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

/*import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig.Builder;*/


public class MyApplication extends Application implements ActivityLifecycleCallbacks {
    public static Boolean app_status = Boolean.valueOf(true);
    SharedPreferenceManager sharedPreferenceManager;

    public static MyApplication absWomenApplication;
    public TextToSpeech textToSpeech;

    public static String admobAppID = "ca-app-pub-2957577039807154~2089406596";
    public static String unityAppID = "";
    public static String metaAppID = "";

    public static String admobBannerId = "ca-app-pub-2957577039807154/5453936536";
    public static String admobIntersialId = "ca-app-pub-2957577039807154/4041137731";
    public static String admobNativeId = "ca-app-pub-2957577039807154/8372099131";

    public static String metaBannerId = "";
    public static String metaIntersialId = "";
    public static String metaNativeId = "";




    public static MyApplication getInstance() {
        return absWomenApplication;
    }

    public void a() {
        if (this.textToSpeech == null) {
            this.textToSpeech = new TextToSpeech(getInstance(), new com.leonard.healthmanager.b.b(this));
        }
    }

    public void a(int i) {
        if (i == 0) {
            this.textToSpeech.setLanguage(Locale.US);
        }
    }

    public void addEarCorn() {
        try {
            if (this.textToSpeech != null) {
                this.textToSpeech.addEarcon("tick", "com.leonard.healthmanager", R.raw.clocktick_trim);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean isSpeaking() {
        return Boolean.valueOf(this.textToSpeech.isSpeaking());
    }

    class ForegroundCheckTask extends AsyncTask<Context, Void, Boolean> {
        ForegroundCheckTask() {
        }


        public Boolean doInBackground(Context... contextArr) {
            return Boolean.valueOf(isAppOnForeground(contextArr[0]));
        }

        private boolean isAppOnForeground(Context context) {
            List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
            if (runningAppProcesses == null) {
                return false;
            }
            String packageName = context.getPackageName();
            for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.importance == 100 && runningAppProcessInfo.processName.equals(packageName)) {
                    return true;
                }
            }
            return false;
        }
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }


    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    public void onCreate() {
        super.onCreate();
        /*CalligraphyConfig.initDefault(new Builder()
                .setDefaultFontPath(getString(R.string.font_light))
                .setFontAttrId(R.attr.fontPath).build());*/
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath(getString(R.string.font_light))
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        registerActivityLifecycleCallbacks(this);
        // ad off
        //initInterstitialAd();
        ConnectionBuddy.getInstance().init(new ConnectionBuddyConfiguration.Builder(this).build());
        absWomenApplication = this;
        new Thread(new com.leonard.healthmanager.b.a(this)).start();
    }
    public void playEarCorn() {
        try {
            if (this.textToSpeech != null) {
                String str = "tick";
                if (VERSION.SDK_INT >= 21) {
                    this.textToSpeech.playEarcon(str, 0, null, "com.outthinking.abs");
                } else {
                    this.textToSpeech.playEarcon(str, 0, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            if (this.textToSpeech != null) {
                this.textToSpeech.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void speak(String str) {
        try {
            if (this.textToSpeech != null) {
                this.textToSpeech.setSpeechRate(1.0f);
                this.textToSpeech.speak(str, 1, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            if (this.textToSpeech != null) {
                this.textToSpeech.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onActivityStarted(Activity activity) {
        try {
            if (((Boolean) new ForegroundCheckTask().execute(new Context[]{getApplicationContext()}).get()).booleanValue()) {
                stopService(new Intent(this, NotificationBroadcastReciever.class));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e2) {
            e2.printStackTrace();
        }
    }

    public void onActivityStopped(Activity activity) {
        try {
            boolean booleanValue = ((Boolean) new ForegroundCheckTask().execute(new Context[]{getApplicationContext()}).get()).booleanValue();
            PrintStream printStream = System.out;
            StringBuilder sb = new StringBuilder();
            sb.append("forground===");
            sb.append(booleanValue);
            printStream.println(sb.toString());
            if (!booleanValue) {
                PendingIntent broadcast = PendingIntent.getBroadcast(this, 1, new Intent(this, NotificationBroadcastReciever.class), 268435456);
                AlarmManager alarmManager = (AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM);
                Calendar instance = Calendar.getInstance();
                if (VERSION.SDK_INT >= 23) {
                    alarmManager.setExactAndAllowWhileIdle(0, instance.getTimeInMillis() + 604800000, broadcast);
                } else if (VERSION.SDK_INT >= 19) {
                    alarmManager.setExact(0, instance.getTimeInMillis() + 604800000, broadcast);
                } else if (VERSION.SDK_INT >= 16) {
                    alarmManager.setRepeating(0, instance.getTimeInMillis() + 604800000, instance.getTimeInMillis() + 604800000, broadcast);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e2) {
            e2.printStackTrace();
        }
    }

  /*  public void initInterstitialAd() {
        if (!this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
            interstitial = new InterstitialAd(this);
            //interstitial.setAdUnitId(getResources().getString(R.string.interstitial_key));
            interstitial.setAdUnitId(intersialsRandomdAdIdsGenerator());
            interstitial.loadAd(new AdRequest.Builder().build());
        }
    }
     public static String bannerRandomdAdIdsGenerator () {
        Random randomGenerator = new Random();
        ArrayList sample = new ArrayList() {{
          add("ca-app-pub-8329902046519331/4561192113");
            add("ca-app-pub-8329902046519331/2438711219");
            add("ca-app-pub-8329902046519331/4232699684");
            add("ca-app-pub-8329902046519331/4944335491");
            add("ca-app-pub-8329902046519331/4725963208");

            add("ca-app-pub-2957577039807154/5453936536");
            add("ca-app-pub-2957577039807154/7696956496");
            add("ca-app-pub-2957577039807154/5214904011");
            add("ca-app-pub-2957577039807154/8818466471");
            add("ca-app-pub-2957577039807154/2253058123");

            // Test ids
            add("ca-app-pub-3940256099942544/6300978111");

        }};
        return (String) sample.get(randomGenerator.nextInt(sample.size()));
    }

    public static String nativeRandomdAdIdGenerator () {
        Random randomGenerator = new Random();
        ArrayList sample = new ArrayList() {{
            add("ca-app-pub-8329902046519331/9293454676");
            add("ca-app-pub-8329902046519331/6667291339");
            add("ca-app-pub-8329902046519331/7078710098");

           add("ca-app-pub-2957577039807154/8372099131");
            add("ca-app-pub-2957577039807154/3518678961");
            add("ca-app-pub-2957577039807154/6867445777");
            add("ca-app-pub-2957577039807154/6484302390");
            add("ca-app-pub-2957577039807154/8727322355");
            add("ca-app-pub-3940256099942544/2247696110");
        }};
        return (String) sample.get(randomGenerator.nextInt(sample.size()));
    }

    public static String intersialsRandomdAdIdsGenerator () {
        Random randomGenerator = new Random();
        ArrayList sample = new ArrayList() {{
           add("ca-app-pub-8329902046519331/1551885397");
            add("ca-app-pub-8329902046519331/7597229625");
            add("ca-app-pub-8329902046519331/2536474631");
            add("ca-app-pub-8329902046519331/9513301742");
            add("ca-app-pub-8329902046519331/3177077811");

            add("ca-app-pub-2957577039807154/7457923978");
            add("ca-app-pub-2957577039807154/7860608026");
            add("ca-app-pub-2957577039807154/1295199672");
            add("ca-app-pub-2957577039807154/2416709659");
            add("ca-app-pub-3940256099942544/1033173712");
        }};
        return (String) sample.get(randomGenerator.nextInt(sample.size()));
    }*/


}
