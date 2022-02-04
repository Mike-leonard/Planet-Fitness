package com.leonard.healthmanager.chest_hip_ratio;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.leonard.healthmanager.R;
import com.leonard.healthmanager.utils.GlobalFunction;
import com.leonard.healthmanager.utils.SharedPreferenceManager;
import com.leonard.healthmanager.utils.TypefaceManager;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import static com.leonard.healthmanager.AdConstantControl.bannerAdControl;


public class Chest_Hip_Ratio_Result extends Activity {
    String TAG = getClass().getSimpleName();
    String chr;
    String chr_percentage;
    Bundle extras;
    GlobalFunction globalFunction;
    ImageView iv_close;
    RelativeLayout rl_main;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_ans_bmr;
    TextView tv_ans_healthrisk;
    TextView tv_whr_chart;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        //super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
        super.attachBaseContext(ViewPumpContextWrapper.wrap(context));
    }


    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.popup_whr);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.rl_main = (RelativeLayout) findViewById(R.id.rl_main);
        this.iv_close = (ImageView) findViewById(R.id.iv_close);
        //this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.extras = getIntent().getExtras();
        this.chr = this.extras.getString("chr");
        this.chr_percentage = this.extras.getString("chr_percentage");
        this.tv_ans_bmr = (TextView) findViewById(R.id.tv_ans_bmr);
        this.tv_ans_healthrisk = (TextView) findViewById(R.id.tv_ans_healthrisk);
        this.tv_whr_chart = (TextView) findViewById(R.id.tv_whr_chart);
        this.tv_ans_bmr.setTypeface(this.typefaceManager.getLight());
        this.tv_ans_healthrisk.setTypeface(this.typefaceManager.getLight());
        this.tv_whr_chart.setVisibility(8);
//        this.rl_main.setBackgroundResource(R.drawable.popup_background_gradient5);
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }

        // banner ad control
        View rootView = getWindow().getDecorView().getRootView();
        bannerAdControl(this, R.id.normal_ad_include, rootView);

        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(this.chr);
        Log.d("chr->", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("");
        sb2.append(this.chr_percentage);
        Log.d("chr_percentage->", sb2.toString());
        TextView textView = this.tv_ans_bmr;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(getString(R.string.your_chr));
        sb3.append(" ");
        sb3.append(this.chr);
        textView.setText(sb3.toString());
        TextView textView2 = this.tv_ans_healthrisk;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(getString(R.string.chr_percentage));
        sb4.append(" ");
        sb4.append(this.chr_percentage);
        textView2.setText(sb4.toString());
        this.iv_close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Chest_Hip_Ratio_Result.this.onBackPressed();
            }
        });
    }


    public void onResume() {
        super.onResume();
    }
}
