package com.leonard.healthmanager.blood_donation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.leonard.healthmanager.R;
import com.leonard.healthmanager.general.MyApplication;
import com.leonard.healthmanager.utils.DateUtil;
import com.leonard.healthmanager.utils.GlobalFunction;
import com.leonard.healthmanager.utils.SharedPreferenceManager;
import com.leonard.healthmanager.utils.TypefaceManager;
import com.leonard.healthmanager.shashikant.calendar.SNPCalendarView;
import com.leonard.healthmanager.shashikant.calendar.onSNPCalendarViewListener;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.interfaces.NetworkRequestCheckListener;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import static com.leonard.healthmanager.AdConstantControl.adControl;
import static com.leonard.healthmanager.AdConstantControl.bannerAdControl;



public class Blood_Donation_Calculator extends Activity {
    String TAG = getClass().getSimpleName();
    String eligieble_date;
    GlobalFunction globalFunction;
    ImageView iv_back;
    //SNPCalendarView mFCalendarView;
    String prev_date;
    SharedPreferenceManager sharedPreferenceManager;
    String todays_date;
    TextView tv_blood_donation;
    TextView tv_date;
    TextView tv_search_date;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        //super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
        super.attachBaseContext(ViewPumpContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.blood_donation_calculator_new);
        this.globalFunction = new GlobalFunction(this);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.set_locale_language();
        //this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        //this.mFCalendarView = (SNPCalendarView) findViewById(R.id.mFCalendarView);
        this.tv_search_date = (TextView) findViewById(R.id.tv_search_date);
        this.tv_date = (TextView) findViewById(R.id.tv_date);
        this.tv_blood_donation = (TextView) findViewById(R.id.tv_blood_donation);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);

        this.tv_blood_donation.setTypeface(this.typefaceManager.getBold());
        this.tv_search_date.setTypeface(this.typefaceManager.getBold());
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        this.tv_date.setFocusable(true);
        this.tv_date.setFocusableInTouchMode(true);
        this.tv_date.requestFocus();
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Blood_Donation_Calculator.this.onBackPressed();
            }
        });
        this.todays_date = getDateTime();
        get_eligieble_date(this.todays_date);
        this.tv_search_date.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                int random = ((int) (Math.random() * 3.0d)) + 1;
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("random_number==>");
                sb.append(random);
                printStream.println(sb.toString());
                if (random == 2) {
                    adControl(Blood_Donation_Calculator.this);
                    return;
                }
                Intent intent = new Intent(Blood_Donation_Calculator.this, Blood_Donation_Result.class);
                intent.putExtra("prevdate", Blood_Donation_Calculator.this.prev_date);
                intent.putExtra("nextdate", Blood_Donation_Calculator.this.eligieble_date);
                intent.putExtra("flag", "0");
                Blood_Donation_Calculator.this.startActivity(intent);
            }
        });
        /*this.mFCalendarView.setOnCalendarViewListener(new onSNPCalendarViewListener() {
            public void onDisplayedMonthChanged(int i, int i2, String str) {
            }

            public void onDateChanged(String str) {
                String str2 = "date";
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("date->");
                    sb.append(str);
                    Log.d(str2, sb.toString());
                    Blood_Donation_Calculator.this.get_eligieble_date(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/
        calendarViewEvent();
        //googleBannerView();
        // banner ad control
        View rootView = getWindow().getDecorView().getRootView();
        bannerAdControl(this, R.id.normal_ad_include, rootView);
    }

    private String getDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    public void get_eligieble_date(String str) {
        try {
            Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(str);
            Date addDays = DateUtil.addDays(parse, 56);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd MMM yyyy");
            this.eligieble_date = simpleDateFormat.format(addDays);
            this.prev_date = simpleDateFormat2.format(parse);
            StringBuilder sb = new StringBuilder();
            sb.append("eligieble_date->");
            sb.append(this.eligieble_date);
            Log.d("eligieble_date", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("prev_date->");
            sb2.append(this.prev_date);
            Log.d("prev_date", sb2.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }


    public void onResume() {
        super.onResume();
    }

    private void calendarViewEvent () {
        LinearLayout adContainer = findViewById(R.id.calenderShow);
        SNPCalendarView snpCalendarViewer = new SNPCalendarView(this);

        // Place the ad view.
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);


        snpCalendarViewer.setOnCalendarViewListener(new onSNPCalendarViewListener() {
            public void onDisplayedMonthChanged(int i, int i2, String str) {
            }

            public void onDateChanged(String str) {
                String str2 = "date";
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("date->");
                    sb.append(str);
                    Log.d(str2, sb.toString());
                    Blood_Donation_Calculator.this.get_eligieble_date(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        adContainer.addView(snpCalendarViewer, params);
    }
}
