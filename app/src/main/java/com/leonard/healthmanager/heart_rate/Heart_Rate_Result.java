package com.leonard.healthmanager.heart_rate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.leonard.healthmanager.R;
import com.leonard.healthmanager.general.MyApplication;
import com.leonard.healthmanager.utils.GlobalFunction;
import com.leonard.healthmanager.utils.SharedPreferenceManager;
import com.leonard.healthmanager.utils.TypefaceManager;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.interfaces.NetworkRequestCheckListener;
import java.io.PrintStream;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import static com.leonard.healthmanager.AdConstantControl.adControl;
import static com.leonard.healthmanager.AdConstantControl.bannerAdControl;


public class Heart_Rate_Result extends Activity {
    String TAG = getClass().getSimpleName();
    GlobalFunction globalFunction;
    ImageView iv_close;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_ans_heartrate;
    TextView tv_heartrate_chart;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        //super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
        super.attachBaseContext(ViewPumpContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.popup_heartrate);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        //this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.iv_close = (ImageView) findViewById(R.id.iv_close);
        this.tv_ans_heartrate = (TextView) findViewById(R.id.tv_ans_heartrate);
        this.tv_heartrate_chart = (TextView) findViewById(R.id.tv_heartrate_chart);
        this.tv_ans_heartrate.setTypeface(this.typefaceManager.getLight());
        this.tv_heartrate_chart.setTypeface(this.typefaceManager.getBold());
        // banner ad control
        View rootView = getWindow().getDecorView().getRootView();
        bannerAdControl(this, R.id.normal_ad_include, rootView);

        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        TextView textView = this.tv_ans_heartrate;
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.heart_rate));
        sb.append(String.valueOf(Heart_Rate_Calculator.HRmax));
        sb.append(" bpm");
        textView.setText(sb.toString());
        this.tv_heartrate_chart.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                int random = ((int) (Math.random() * 3.0d)) + 1;
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("random_number==>");
                sb.append(random);
                printStream.println(sb.toString());
                if (random == 2) {
                    adControl(Heart_Rate_Result.this);
                    return;
                }
                Heart_Rate_Result.this.startActivity(new Intent(Heart_Rate_Result.this, Heart_Rate_Chart.class));
            }
        });
        this.iv_close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Heart_Rate_Result.this.onBackPressed();
            }
        });
    }

    public void onResume() {
        super.onResume();
    }
}
