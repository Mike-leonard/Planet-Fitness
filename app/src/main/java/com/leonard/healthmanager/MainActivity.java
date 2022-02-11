package com.leonard.healthmanager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.leonard.healthmanager.WalkandStep.utils.StepDetectionServiceHelper;
import com.leonard.healthmanager.fragment.Fragment_Calculate;
import com.leonard.healthmanager.fragment.Fragment_Reminder;
import com.leonard.healthmanager.fragment.Fragment_Walk_and_Step;
import com.leonard.healthmanager.fragment.Fragment_Workout;
import com.leonard.healthmanager.fragment.MainFragment;
import com.leonard.healthmanager.fragment.Workout;

import static com.leonard.healthmanager.AdConstantControl.bannerAdControl;
import static com.leonard.healthmanager.general.Splash.isGoogleAdEnabled;
import static com.leonard.healthmanager.utils.Constants.*;


public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigation;

    DrawerLayout drawer;
    ImageView imageView1;
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            String str = "";
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:


                    toolbar.setTitle(getString(R.string.app_name));
                    MainActivity.this.openFragment(MainFragment.newInstance(str, str,MainActivity.this));
                    return true;

                case R.id.navigation_map:
                    toolbar.setTitle("Workouts");
                    MainActivity.this.openFragment(Fragment_Workout.newInstance(str, str));
                    return true;

                case R.id.navigation_world:
                    toolbar.setTitle("Calculater");
                    MainActivity.this.openFragment(Fragment_Calculate.newInstance(str, str));
                    return true;

                case R.id.navigation_walk:
                    toolbar.setTitle("Walk & Step");
                    MainActivity.this.openFragment(Fragment_Walk_and_Step.newInstance(str, str));
                    return true;

                case R.id.navigation_news:
                    toolbar.setTitle("Reminders");
                    MainActivity.this.openFragment(Fragment_Reminder.newInstance(str, str));
                    return true;

                default:

                    return false;
            }
        }
    };

    NavigationView navigationView;
    Toolbar toolbar;


    @SuppressLint("ResourceType")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (VERSION.SDK_INT > 21) {
            StrictMode.setThreadPolicy(new Builder().permitAll().build());
        }
        setContentView((int) R.layout.activity_main);

//        StepDetectionServiceHelper.startAllIfEnabled(true, MainActivity.this);

        this.navigationView = (NavigationView) findViewById(R.id.nav_views);
//        bottomNavigation.setItemIconTintList(null);
        this.imageView1 = (ImageView) findViewById(R.id.setting);
        this.imageView1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                MainActivity.this.startActivity(new Intent(MainActivity.this, Setting_Activity.class));
            }
        });
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
//            window.setStatusBarColor(Color.parseColor("#EF5050"));
        }
        this.toolbar = initToolbar();
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.drawer = drawerLayout;
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, this.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.drawer.addDrawerListener(actionBarDrawerToggle);
        this.drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            public void onDrawerClosed(View view) {
            }

            public void onDrawerOpened(View view) {
            }
        });
        actionBarDrawerToggle.syncState();
        this.navigationView.setNavigationItemSelectedListener(this);
        String str = "#ffffff";
//        this.toolbar.setTitleTextColor(Color.parseColor(str));
//        this.toolbar.getNavigationIcon().setColorFilter(Color.parseColor(str), Mode.MULTIPLY);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        this.bottomNavigation = bottomNavigationView;

        bottomNavigationView.setOnNavigationItemSelectedListener(this.navigationItemSelectedListener);
        String str2 = "";

//        MainActivity mainActivity = null;
        openFragment(MainFragment.newInstance(str2 ,str2 ,this ));




        //hsn
        onNewIntent(getIntent());

        // Ad status to 1 hour
        prefAdHourControl = getSharedPreferences("next-hour", MODE_PRIVATE);
        // Hourly Banner ads showing
        prefHourBanAds = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        perHoursBannerAds = prefHourBanAds.getInt("hourly-ban-ads", 0);
        // Hourly Intersial ads showing
        prefHourIntAds = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        perHoursIntersialAds = prefHourIntAds.getInt("hourly-int-ads", 0);
        // Hourly Native ads showing
        prefHourNatAds = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        perHoursNativeAds = prefHourNatAds.getInt("hourly-nat-ads", 0);

        // Daily banner ads
        prefDailyBanAds = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        dailyMaxBannerAds = prefDailyBanAds.getInt("daily-ban-ads", 0);
        // Daily intersial ads
        prefDailyIntAds = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        dailyMaxIntersialAds = prefDailyIntAds.getInt("daily-int-ads", 0);
        // Daily native ads
        prefDailyNatAds = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        dailyMaxNativeAds = prefDailyNatAds.getInt("daily-nat-ads", 0);
        // Daily Ad Clicked
        //prefDailyAdClicked = getSharedPreferences("daily-ad-click", MODE_PRIVATE);
        prefDailyAdClicked = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        dailyAdClicked = prefDailyAdClicked.getInt("daily-ad-click", 0);

        // default app install time
        //appInstallPref = getSharedPreferences("ins-time", MODE_PRIVATE);
        appInstallPref = getSharedPreferences("ins-time", 0);




        //((AdView) findViewById(R.id.adView)).loadAd(new AdRequest.Builder().build());
        // banner ad control
        View rootView = getWindow().getDecorView().getRootView();
        bannerAdControl(MainActivity.this, R.id.banner_ads_play, rootView);
    }

    // new line of code hsn
    @Override
    protected void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if(extras != null){
            if(extras.containsKey("notify")) {
                loadFragmentworkout(new Workout());
            } else if (extras.containsKey("widgets")) {
                loadFragment_water(new Fragment_Walk_and_Step());
            } else if (extras.containsKey("water")) {
                openFragment(new MainFragment(this));
            }
        }
        super.onNewIntent(intent);
    }
    // end of new line
   /* https://stackoverflow.com/questions/8610880/how-do-i-create-an-android-intent-that-carries-data/8610916#8610916
    https://stackoverflow.com/questions/40780144/how-to-open-fragment-on-click-of-push-notification
    https://stackoverflow.com/questions/39383157/how-to-open-particular-fragment-on-the-click-of-the-push-notification-message/39383447*/


    public void openFragment(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.nav_host_fragment, fragment);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
    }

    public void loadFragmentworkout(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.nav_host_fragment, fragment);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
        toolbar.setTitle("workout");
        bottomNavigation.setSelectedItemId(R.id.navigation_map);
    }

    public void loadFragment_water(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.nav_host_fragment, fragment);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
        toolbar.setTitle("Walk & Step");
        bottomNavigation.setSelectedItemId(R.id.navigation_walk);
    }

    private Toolbar initToolbar() {
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar2);


        return toolbar2;
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();

        String str = "android.intent.extra.TEXT";
        String str2 = "android.intent.extra.SUBJECT";


        if (itemId == R.id.nav_rateus) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        } else if (itemId == R.id.nav_share) {

            Intent intent2 = new Intent("android.intent.action.SEND");
            intent2.setType("text/plain");
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Best Planet Fitness app download now.\n Thnak You!\n  https://play.google.com/store/apps/details?id=");
            sb3.append(getApplicationContext().getPackageName());
            String sb4 = sb3.toString();
            intent2.putExtra(str2, "Share App");
            intent2.putExtra(str, sb4);
            startActivity(Intent.createChooser(intent2, "Share via"));
        }else  if(itemId == R.id.nav_privacy)
        {

            Uri uri = Uri.parse("https://www.app-privacy-policy.com/live.php?token=b3avSa6fIv4fynj1I0bChr2fHJ66vBlx");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        }

        this.drawer.closeDrawer((int) GravityCompat.START);
        return true;
    }


    public void onBackPressed() {
        StepDetectionServiceHelper.stopAllIfNotRequired(this.getApplicationContext());
//        StepDetectionServiceHelper.startAllIfEnabled(true, MainActivity.this);

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.adview_layout_exit);
        ((GifImageView) dialog.findViewById(R.id.GifImageView)).setGifImageResource(R.drawable.rate);
        ((Button) dialog.findViewById(R.id.btnno)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ((Button) dialog.findViewById(R.id.btnrate)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    MainActivity mainActivity = MainActivity.this;
                    mainActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + MainActivity.this.getPackageName())));
                } catch (ActivityNotFoundException unused) {
                    MainActivity mainActivity2 = MainActivity.this;
                    mainActivity2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName())));
                }
            }
        });
        ((Button) dialog.findViewById(R.id.btnyes)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                try {
                    dialog.dismiss();
                    MainActivity.this.finish();
                    System.exit(1);
                    //android.os.Process.killProcess(android.os.Process.myPid());
                } catch (Exception e){
                    Log.e("exit_exp", e.getMessage());
                }
            }
        });
        dialog.show();
    }
}
