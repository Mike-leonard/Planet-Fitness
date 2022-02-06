package com.leonard.healthmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader.Builder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import org.jetbrains.annotations.NotNull;

import static com.leonard.healthmanager.utils.Constants.dailyAdClicked;
import static com.leonard.healthmanager.utils.Constants.dailyAdClickedEditor;
import static com.leonard.healthmanager.utils.Constants.dailyMaxNativeAds;
import static com.leonard.healthmanager.utils.Constants.dailyNatEditor;
import static com.leonard.healthmanager.utils.Constants.hourNatEditor;
import static com.leonard.healthmanager.utils.Constants.perHoursNativeAds;
import static com.leonard.healthmanager.utils.Constants.prefDailyAdClicked;
import static com.leonard.healthmanager.utils.Constants.prefDailyNatAds;
import static com.leonard.healthmanager.utils.Constants.prefHourNatAds;


public class AdmobAds {
    public String ADMOB_AD_UNIT_ID = "";

    public boolean admobAdLoaded_dialog;
    public Context context;
    public LinearLayout nativeAdContainer;
    public NativeAd unifiedNativeAdObject_dialog;

    public AdmobAds(Context context2, LinearLayout linearLayout, String str) {
        this.context = context2;
        this.nativeAdContainer = linearLayout;
        this.ADMOB_AD_UNIT_ID = str;
        MobileAds.initialize(context2);
    }

    public AdmobAds(Context context2, String str) {
        this.context = context2;
        this.ADMOB_AD_UNIT_ID = str;
        MobileAds.initialize(context2);
    }

    public void displayAdmobAdOnLoad_Dialog(LinearLayout linearLayout) {
        linearLayout.setVisibility(View.VISIBLE);
        NativeAdView unifiedNativeAdView = (NativeAdView) LayoutInflater.from(this.context).inflate(R.layout.ad_unified_dialog, null);
        populateUnifiedNativeAdView_dialog(this.unifiedNativeAdObject_dialog, unifiedNativeAdView);
        linearLayout.addView(unifiedNativeAdView);
    }

    public boolean isConnectedToInternet() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
                if (allNetworkInfo != null) {
                    for (NetworkInfo state : allNetworkInfo) {
                        if (state.getState() == State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public void populateUnifiedNativeAdView(NativeAd unifiedNativeAd, NativeAdView unifiedNativeAdView) {
        int i;
        View view;

        unifiedNativeAd.getMediaContent().getVideoController()
                .setVideoLifecycleCallbacks(new
                VideoController.VideoLifecycleCallbacks() {
                    @Override
                    public void onVideoEnd() {
                        super.onVideoEnd();
                    }
        });

        int i2 = this.context.getResources().getDisplayMetrics().heightPixels;
        com.google.android.gms.ads.nativead.MediaView mediaView = (com.google.android.gms.ads.nativead.MediaView) unifiedNativeAdView.findViewById(R.id.popup_appinstall_image);

        LayoutParams layoutParams = mediaView.getLayoutParams();
        layoutParams.height = (int) (((float) i2) / 3.0f);
        mediaView.setLayoutParams(layoutParams);
        unifiedNativeAdView.setMediaView(mediaView);
        unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.popup_appinstall_headline));
        unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.popup_appinstall_body));
        unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.popup_appinstall_call_to_action));
        unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.popup_appinstall_app_icon));
        unifiedNativeAdView.findViewById(R.id.close_ad_popup).setVisibility(View.INVISIBLE);
        ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
        ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
        ((Button) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
        if (unifiedNativeAd.getIcon() == null) {
            view = unifiedNativeAdView.getIconView();
            i = 8;
        } else {
            ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
            view = unifiedNativeAdView.getIconView();
            i = 0;
        }
        view.setVisibility(i);
        unifiedNativeAdView.setNativeAd(unifiedNativeAd);
    }

    public void populateUnifiedNativeAdView_dialog(NativeAd unifiedNativeAd, NativeAdView unifiedNativeAdView) {
        int i;
        View view;
        unifiedNativeAd.getMediaContent().getVideoController()
                .setVideoLifecycleCallbacks(new
                VideoController.VideoLifecycleCallbacks() {
                    @Override
                    public void onVideoEnd() {
                        super.onVideoEnd();
                    }
        });
        int i2 = this.context.getResources().getDisplayMetrics().heightPixels;
        com.google.android.gms.ads.nativead.MediaView mediaView = (com.google.android.gms.ads.nativead.MediaView) unifiedNativeAdView.findViewById(R.id.popup_appinstall_image_dialog);
        LayoutParams layoutParams = mediaView.getLayoutParams();
        layoutParams.height = (int) (((float) i2) / 3.0f);
        mediaView.setLayoutParams(layoutParams);
        unifiedNativeAdView.setMediaView(mediaView);
        unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.popup_appinstall_headline_dialog));
        unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.popup_appinstall_body_dialog));
        unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.popup_appinstall_call_to_action_dialog));
        unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.popup_appinstall_app_icon_dialog));
        ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
        ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
        ((Button) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
        if (unifiedNativeAd.getIcon() == null) {
            view = unifiedNativeAdView.getIconView();
            i = 8;
        } else {
            ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
            view = unifiedNativeAdView.getIconView();
            i = 0;
        }
        view.setVisibility(i);
        unifiedNativeAdView.setNativeAd(unifiedNativeAd);
    }

    public void refreshAd() {
        if (isConnectedToInternet()) {
            Builder builder = new Builder(this.context, this.ADMOB_AD_UNIT_ID);
            builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                @Override
                public void onNativeAdLoaded(@NonNull @NotNull NativeAd nativeAd) {
                    NativeAdView unifiedNativeAdView = (NativeAdView) LayoutInflater.from(AdmobAds.this.context).inflate(R.layout.ad_unified, null);
                    AdmobAds.this.populateUnifiedNativeAdView(nativeAd, unifiedNativeAdView);
                    AdmobAds.this.nativeAdContainer.setVisibility(View.VISIBLE);
                    AdmobAds.this.nativeAdContainer.setBackgroundResource(R.drawable.shape_roundedwhite);
                    AdmobAds.this.nativeAdContainer.addView(unifiedNativeAdView);
                }
            });
            builder.withNativeAdOptions(new
                    com.google.android.gms.ads.nativead.NativeAdOptions
                            .Builder().setVideoOptions(new
                    VideoOptions.Builder().setStartMuted(true).build()).build());

            builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull @NotNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    AdmobAds.this.refreshAd();
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
                public void onAdLoaded() {
                    super.onAdLoaded();
                    perHoursNativeAds++;
                    dailyMaxNativeAds++;

                    hourNatEditor = prefHourNatAds.edit();
                    dailyNatEditor = prefDailyNatAds.edit();
                    hourNatEditor.putInt("hourly-nat-ads", perHoursNativeAds);
                    dailyNatEditor.putInt("daily-nat-ads", dailyMaxNativeAds);
                    hourNatEditor.commit();
                    dailyNatEditor.commit();
                }
            }).build().loadAd(new AdRequest.Builder().build());
        }
    }

    public boolean refreshAd_dialog() {
        if (isConnectedToInternet()) {
            Builder builder = new Builder(this.context, this.ADMOB_AD_UNIT_ID);
            builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                @Override
                public void onNativeAdLoaded(@NonNull @NotNull NativeAd nativeAd) {
                    AdmobAds.this.admobAdLoaded_dialog = true;
                    AdmobAds.this.unifiedNativeAdObject_dialog = nativeAd;
                }
            });

            builder.withNativeAdOptions(new
                    com.google.android.gms.ads.nativead.NativeAdOptions
                            .Builder().setVideoOptions(new
                    VideoOptions.Builder().setStartMuted(true).build()).build());
            builder.withAdListener(new AdListener() {
                public void onAdFailedToLoad(int i) {
                    AdmobAds.this.admobAdLoaded_dialog = false;
                    AdmobAds.this.refreshAd_dialog();
                }
            }).build().loadAd(new AdRequest.Builder().build());
        }
        return this.admobAdLoaded_dialog;
    }
}
