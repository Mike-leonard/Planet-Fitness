package com.leonard.healthmanager;

import android.app.Activity;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/*import com.facebook.ads.AbstractAdListener;
import com.facebook.ads.AdError;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;*/
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
/*import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;*/
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.ResponseInfo;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
/*import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;*/
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

import org.jetbrains.annotations.NotNull;

import static com.leonard.healthmanager.general.Splash.isGoogleAdEnabled;
import static com.leonard.healthmanager.utils.Constants.adHourEditor;
import static com.leonard.healthmanager.utils.Constants.appInstallEditor;
import static com.leonard.healthmanager.utils.Constants.appInstallPref;
import static com.leonard.healthmanager.utils.Constants.dailyAdClicked;
import static com.leonard.healthmanager.utils.Constants.dailyAdClickedEditor;
import static com.leonard.healthmanager.utils.Constants.dailyBanEditor;
import static com.leonard.healthmanager.utils.Constants.dailyIntEditor;
import static com.leonard.healthmanager.utils.Constants.dailyMaxBannerAds;
import static com.leonard.healthmanager.utils.Constants.dailyMaxIntersialAds;
import static com.leonard.healthmanager.utils.Constants.dailyMaxNativeAds;
import static com.leonard.healthmanager.utils.Constants.dailyNatEditor;
import static com.leonard.healthmanager.utils.Constants.hourBanEditor;
import static com.leonard.healthmanager.utils.Constants.hourIntEditor;
import static com.leonard.healthmanager.utils.Constants.hourNatEditor;
import static com.leonard.healthmanager.utils.Constants.perHoursBannerAds;
import static com.leonard.healthmanager.utils.Constants.perHoursIntersialAds;
import static com.leonard.healthmanager.utils.Constants.perHoursNativeAds;
import static com.leonard.healthmanager.utils.Constants.prefAdHourControl;
import static com.leonard.healthmanager.utils.Constants.prefDailyAdClicked;
import static com.leonard.healthmanager.utils.Constants.prefDailyBanAds;
import static com.leonard.healthmanager.utils.Constants.prefDailyIntAds;
import static com.leonard.healthmanager.utils.Constants.prefDailyNatAds;
import static com.leonard.healthmanager.utils.Constants.prefHourBanAds;
import static com.leonard.healthmanager.utils.Constants.prefHourIntAds;
import static com.leonard.healthmanager.utils.Constants.prefHourNatAds;
import static com.unity3d.services.core.properties.ClientProperties.getApplicationContext;

public class AdConstantControl {
    private static String intersialUnityPlacement = "Interstitial_Android";
    private static String bannerUnityPlacement = "Banner_Android";
    private static boolean testMode = true;


    public static void adNetworkIntializeRequest (Activity act) {

       /* MobileAds.initialize(act, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull @NotNull InitializationStatus initializationStatus) {

            }
        });*/
        MobileAds.initialize(act);
        /*UnityAds.initialize((Context) act,
                act.getString(R.string.unity_ad_ids),
                true,  unityAdsListener);*/

        //AudienceNetworkAds.initialize(act);

        UnityAds.initialize(act, act.getString(R.string.unity_ad_ids), unityAdsListener, testMode);
        // for banner ads
        UnityAds.initialize(act, act.getString(R.string.unity_ad_ids),
                null, testMode , true);
    }

    // two adnetworks banner ad control
    public static void bannerAdControl (Activity act, int layout_id, View v) {

        if (isGoogleAdEnabled) {
            Long tsLong = System.currentTimeMillis()/1000;
            prefAdHourControl = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            appInstallPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            Long nextHour = prefAdHourControl.getLong("next-hour", 0);
            Long appInstallTime = appInstallPref.getLong("ins-time", 0);

            // next day finder
            if (appInstallTime > tsLong) {
                if (dailyMaxBannerAds < 200) {
                    if (nextHour > tsLong) {
                        if (perHoursBannerAds < 25 ) {
                            if(dailyAdClicked < 2) {
                                googleBannerView(act, layout_id, v);
                            } else {
                                unityBannerAdShower(act, layout_id, v);
                            }
                        } else { //
                            unityBannerAdShower(act, layout_id, v);
                        }
                    } else { // jokon current time boro hoi jaibo

                        Long nextOneHour = tsLong + 3600;
                        adHourEditor = prefAdHourControl.edit();
                        adHourEditor.putLong("next-hour", nextOneHour);
                        adHourEditor.commit();

                        // per hour ad reset
                        perHoursIntersialAds = 0;
                        hourIntEditor = prefHourIntAds.edit();
                        hourIntEditor.putInt("hourly-int-ads", perHoursIntersialAds);
                        hourIntEditor.commit();
                        // update next hour timestamp
                        // reset perHourIntersials
                        Log.d("nextHour", "Else-Next-hour");
                    }
                } else { //
                    unityBannerAdShower(act, layout_id, v);
                }
            } else { // // next day update

                Long nextOneDay = tsLong + 86400;
                appInstallEditor = appInstallPref.edit();
                appInstallEditor.putLong("ins-time", nextOneDay);
                appInstallEditor.commit();

                // daily ad reset
                dailyMaxBannerAds = 0;
                dailyBanEditor = prefDailyBanAds.edit();
                dailyBanEditor.putInt("daily-ban-ads", dailyMaxBannerAds);
                dailyBanEditor.commit();

                // Daily ad Click reset
                dailyAdClicked = 0;
                dailyAdClickedEditor = prefDailyAdClicked.edit();
                dailyAdClickedEditor.putInt("daily-ad-click", dailyAdClicked);
                dailyAdClickedEditor.commit();
            }
        } else { // show unity ads

            unityBannerAdShower(act, layout_id, v);
        }
    }

    // two adnetworks Intersial ad control
    public static void adControl (Activity act) {
        if (isGoogleAdEnabled) {

            Long tsLong = System.currentTimeMillis()/1000;
            prefAdHourControl = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            appInstallPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            Long nextHour = prefAdHourControl.getLong("next-hour", 0);
            Long appInstallTime = appInstallPref.getLong("ins-time", 0);

            // next day finder
            if (appInstallTime > tsLong) {
                if (dailyMaxIntersialAds < 50) {
                    // nextHour finder
                    if (nextHour > tsLong) {
                        if (perHoursIntersialAds < 10 ) {
                            // daily ad clicked
                            if(dailyAdClicked < 2) {
                                //googleIntersialAds(act);
                            } else { // ad Clicked
                                unityAdDisplay(act);
                            }
                        } else { // 10 ta taki beshi oile
                            unityAdDisplay(act);
                        }
                    } else { // jokon current time boro hoi jaibo

                        /*Long nextOneHour = tsLong + 3600;
                        adHourEditor = prefAdHourControl.edit();
                        adHourEditor.putLong("next-hour", nextOneHour);
                        adHourEditor.commit();*/

                        // per hour ad reset
                        perHoursIntersialAds = 0;
                        hourIntEditor = prefHourIntAds.edit();
                        hourIntEditor.putInt("hourly-int-ads", perHoursIntersialAds);
                        hourIntEditor.commit();
                        // update next hour timestamp
                        // reset perHourIntersials
                    }

                } else { // if 50+ start showing unity
                    unityAdDisplay(act);
                }
            } else { // next day update

                /*Long nextOneDay = tsLong + 86400;
                appInstallEditor = appInstallPref.edit();
                appInstallEditor.putLong("ins-time", nextOneDay);
                appInstallEditor.commit();*/

                // daily ad reset
                dailyMaxIntersialAds = 0;
                dailyIntEditor = prefDailyIntAds.edit();
                dailyIntEditor.putInt("daily-int-ads", dailyMaxIntersialAds);
                dailyIntEditor.commit();

                // Daily ad Click reset
               /* dailyAdClicked = 0;
                dailyAdClickedEditor = prefDailyAdClicked.edit();
                dailyAdClickedEditor.putInt("daily-ad-click", dailyAdClicked);
                dailyAdClickedEditor.commit();*/
            }

        } else {   // google ad  disabled statement
            unityAdDisplay(act);
        }
    }

    // Native ad control
    public static void nativeAdControl (Activity act, int layout_id, View view) {
        if (isGoogleAdEnabled) {
            Long tsLong = System.currentTimeMillis()/1000;
            prefAdHourControl = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            appInstallPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            Long nextHour = prefAdHourControl.getLong("next-hour", 0);
            Long appInstallTime = appInstallPref.getLong("ins-time", 0);

            // next day finder
            if (appInstallTime > tsLong) {
                if (dailyMaxNativeAds < 120) {
                    if (nextHour > tsLong) {
                        if (perHoursNativeAds < 15 ) {
                            if(dailyAdClicked < 2) {
                                // naive ad show
                                a(act, layout_id, view);
                            } else {/*Unity donnt have native ads*/}
                        } else {/*Unity donnt have native ads*/}
                    } else {
                        // per hour ad reset
                        perHoursNativeAds = 0;
                        hourNatEditor = prefHourNatAds.edit();
                        hourNatEditor.putInt("hourly-nat-ads", perHoursNativeAds);
                        hourNatEditor.commit();
                    }
                } else {/*Unity donnt have native ads*/}
            } else {
                // daily ad reset
                dailyMaxNativeAds = 0;
                dailyNatEditor = prefDailyNatAds.edit();
                dailyNatEditor.putInt("daily-nat-ads", dailyMaxNativeAds);
                dailyNatEditor.commit();
            }
        } else {/*Unity donnt have native ads*/}
        //https://developers.facebook.com/docs/audience-network/setting-up/ad-setup/android/native/
    }


    // Google normal banner view
    private static void googleBannerView (Activity act, int layout_id, View v) {

        LinearLayout adContainer = v.findViewById(layout_id);

        com.google.android.gms.ads.AdView adView = new
                com.google.android.gms.ads.AdView(getApplicationContext());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getApplicationContext().getString(R.string.google_banner_id));

        // Initiate a generic request to load it with an ad
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // Place the ad view.
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        adContainer.addView(adView, params);

        adView.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                unityBannerAdShower(act, layout_id, v);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                perHoursBannerAds++;
                dailyMaxBannerAds++;

                hourBanEditor = prefHourBanAds.edit();
                dailyBanEditor = prefDailyBanAds.edit();
                hourBanEditor.putInt("hourly-ban-ads", perHoursBannerAds);
                dailyBanEditor.putInt("daily-ban-ads", dailyMaxBannerAds);
                hourBanEditor.commit();
                dailyBanEditor.commit();

            }
            @Override
            public void onAdClicked() {
                super.onAdClicked();
                dailyAdClicked++;
                dailyAdClickedEditor = prefDailyAdClicked.edit();
                dailyAdClickedEditor.putInt("daily-ad-click", dailyAdClicked);
                dailyAdClickedEditor.commit();
            }
            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });

    }

    // Google Intersial ads
   /* private static void googleIntersialAds (Activity act) {

        AdRequest adRequest = new AdRequest.Builder().build();

        final InterstitialAd[] mInterGoogle = {null};
        mInterGoogle[0].load(act, getApplicationContext().getString(R.string.gog_Inter_id),
                adRequest, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull @NotNull InterstitialAd interstitialAd) {
                        super.onAdLoaded(interstitialAd);
                        if (mInterGoogle[0] != null) {
                            mInterGoogle[0].show(act);
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull @NotNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        unityAdDisplay(act);
                    }
                });


        mInterGoogle[0].setFullScreenContentCallback(new FullScreenContentCallback(){
            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when fullscreen content is dismissed.
                Log.d("TAG", "The ad was dismissed.");
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull @NotNull com.google.android.gms.ads.AdError adError) {
                super.onAdFailedToShowFullScreenContent(adError);
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }

            @Override
            public void onAdShowedFullScreenContent() {
                // Called when fullscreen content is shown.
                // Make sure to set your reference to null so you don't
                // show it a second time.
                mInterGoogle[0] = null;
                Log.d("TAG", "The ad was shown.");
            }
        });
        *//*InterstitialAd mInterGoogle;
        mInterGoogle = new InterstitialAd() {
            @NonNull
            @NotNull
            @Override
            public String getAdUnitId() {
                return getApplicationContext().getString(R.string.gog_Inter_id);
            }

            @Override
            public void show(@NonNull @NotNull Activity activity) {

            }

            @Override
            public void setFullScreenContentCallback(@Nullable @org.jetbrains.annotations.Nullable FullScreenContentCallback fullScreenContentCallback) {

            }

            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            public FullScreenContentCallback getFullScreenContentCallback() {
                return null;
            }

            @Override
            public void setImmersiveMode(boolean b) {

            }

            @NonNull
            @NotNull
            @Override
            public ResponseInfo getResponseInfo() {
                return null;
            }

            @Override
            public void setOnPaidEventListener(@Nullable @org.jetbrains.annotations.Nullable OnPaidEventListener onPaidEventListener) {

            }

            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            public OnPaidEventListener getOnPaidEventListener() {
                return null;
            }
        };*//*


        *//*mInterGoogle.setAdUnitId(getApplicationContext().getString(R.string.gog_Inter_id));
        mInterGoogle.loadAd(adRequest);
        mInterGoogle.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                unityAdDisplay(act);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (mInterGoogle.isLoaded()) {
                    mInterGoogle.show();
                    perHoursIntersialAds++;
                    dailyMaxIntersialAds++;

                    hourIntEditor = prefHourIntAds.edit();
                    dailyIntEditor = prefDailyIntAds.edit();
                    hourIntEditor.putInt("hourly-int-ads", perHoursIntersialAds);
                    dailyIntEditor.putInt("daily-int-ads", dailyMaxIntersialAds);
                    hourIntEditor.commit();
                    dailyIntEditor.commit();

                }
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                dailyAdClicked++;

                // then turn off google ads
                // set a click timestamp
                dailyAdClickedEditor = prefDailyAdClicked.edit();
                dailyAdClickedEditor.putInt("daily-ad-click", dailyAdClicked);
                dailyAdClickedEditor.commit();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });*//*
    }*/


    /*private static void fbBannerView (Activity act, int layout_id, View v) {
        LinearLayout adContainer = v.findViewById(layout_id);
       *//* AdView adView = new AdView(Torpito.this,
                "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50);*//*
        *//*AdView adView = new AdView(FocBanBook.this,
                "864858330777025_870780956851429", AdSize.BANNER_HEIGHT_50); *//*
        AdView adView = new AdView(act,
                getApplicationContext().getString(R.string.meta_banner_test),
                com.facebook.ads.AdSize.BANNER_HEIGHT_50);

        // Place the ad view.
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        adContainer.addView(adView, params);

        com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
            @Override
            public void onError(com.facebook.ads.Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(com.facebook.ads.Ad ad) {
            }

            @Override
            public void onAdClicked(com.facebook.ads.Ad ad) {
            }

            @Override
            public void onLoggingImpression(com.facebook.ads.Ad ad) {

            }
        };
        AdView.AdViewLoadConfig loadAdConfig = adView.buildLoadAdConfig()
                .withAdListener(adListener)
                .build();
        adView.loadAd(loadAdConfig);
    }
    private static void facebookInterstitial (Activity act) {
      *//*  InterstitialAd interstitialAd = new InterstitialAd(FocImpBook.this,
                "864858330777025_870781660184692");*//*
        com.facebook.ads.InterstitialAd interstitialAd
                = new com.facebook.ads.InterstitialAd(act,
                getApplicationContext().getString(R.string.meta_intersial_test));

        *//*InterstitialAd interstitialAd = new InterstitialAd(FocImpBook.this,
                "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");*//*

        AbstractAdListener adListener = new AbstractAdListener() {
            @Override
            public void onError(com.facebook.ads.Ad ad, AdError error) {
                Log.e("aderr", String.valueOf(error.getErrorMessage()));
                super.onError(ad, error);
            }

            @Override
            public void onAdLoaded(com.facebook.ads.Ad ad) {
                super.onAdLoaded(ad);
                if (interstitialAd.isAdLoaded()){
                    interstitialAd.show();
                }
            }

            @Override
            public void onAdClicked(com.facebook.ads.Ad ad) {
                super.onAdClicked(ad);
            }

            @Override
            public void onInterstitialDisplayed(com.facebook.ads.Ad ad) {
                super.onInterstitialDisplayed(ad);
            }

            @Override
            public void onInterstitialDismissed(com.facebook.ads.Ad ad) {
                super.onInterstitialDismissed(ad);
            }
        };
        com.facebook.ads.InterstitialAd.InterstitialLoadAdConfig interstitialLoadAdConfig = interstitialAd.buildLoadAdConfig()
                .withAdListener(adListener)
                .build();
        interstitialAd.loadAd(interstitialLoadAdConfig);
    }*/



    // Unity Banner Ads
    private static void unityBannerAdShower (Activity act, int layout_id, View v) {

        LinearLayout topBannerView = v.findViewById(layout_id);
        com.unity3d.services.banners.BannerView topBanner =
                new com.unity3d.services.banners.BannerView(act, bannerUnityPlacement,
                        new UnityBannerSize(320, 50));
        topBanner.load();
        // Place the ad view.
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        topBannerView.addView(topBanner, params);

        topBanner.setListener(new com.unity3d.services.banners.BannerView.IListener() {
            @Override
            public void onBannerLoaded(com.unity3d.services.banners.BannerView bannerView) {
            }

            @Override
            public void onBannerClick(com.unity3d.services.banners.BannerView bannerView) {
            }

            @Override
            public void onBannerFailedToLoad(BannerView bannerView, BannerErrorInfo bannerErrorInfo) {
                Log.e("lad", bannerErrorInfo.errorMessage);
            }

            @Override
            public void onBannerLeftApplication(com.unity3d.services.banners.BannerView bannerView) {

            }
        });
    }

    // Unity Intersial ads
    private static void unityIntersialAds () {
        UnityAds.load(intersialUnityPlacement);
    }
    private static void unityAdDisplay (Activity act) {
        if (UnityAds.isReady(intersialUnityPlacement)){
            Log.e("adsUNDius", "rdy");
            UnityAds.show(act, intersialUnityPlacement);
        }
    }
    // unity intersila listener
    private static IUnityAdsListener unityAdsListener = new IUnityAdsListener() {
        @Override
        public void onUnityAdsReady(String s) {
            unityIntersialAds();
        }

        @Override
        public void onUnityAdsStart(String s) {
        }

        @Override
        public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {
        }

        @Override
        public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {
            Log.e("adsUni", unityAdsError.toString());
        }
    };



    public static void a(Activity act, int layout_id, View view) {
        LinearLayout nativeAdContainer = view.findViewById(layout_id);

        AdmobAds admobAdsObject = null;
        admobAdsObject = new AdmobAds(act, nativeAdContainer,
                getApplicationContext().getString(R.string.google_native_id));
        admobAdsObject.refreshAd();
    }
}
