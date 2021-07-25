package com.leonard.healthmanager.newsapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.leonard.healthmanager.R;

import java.net.URISyntaxException;

import static com.leonard.healthmanager.general.MyApplication.bannerRandomdAdIdsGenerator;
import static com.leonard.healthmanager.newsapi.NewsCast.isInternetAvailable;


public class WebviewNews extends AppCompatActivity {

    private WebView newsContentWeb;
    private String urlText= "";
    private RelativeLayout layoutNoInternet;
    private Button retryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_news);

        newsContentWeb = findViewById(R.id.news_content);
        layoutNoInternet = findViewById(R.id.no_internet_louyt);
        retryButton = findViewById(R.id.retry_btn);
        googleBannerView();

        urlText = getIntent().getStringExtra("url");
        newsContentWeb.getSettings().setJavaScriptEnabled(true);

        newsContentWeb.getSettings().setLoadWithOverviewMode(true);
        newsContentWeb.getSettings().setUseWideViewPort(true);
        newsContentWeb.getSettings().setBuiltInZoomControls(true);
        newsContentWeb.getSettings().setDisplayZoomControls(true);
        newsContentWeb.setWebViewClient(new WebViewClient());

        if(isInternetAvailable()){
            newsContentWeb.loadUrl(urlText);
        } else {
            layoutNoInternet.setVisibility(View.VISIBLE);
            newsContentWeb.setVisibility(View.GONE);
        }


        newsContentWeb.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                if (errorCode == -2) {
                    layoutNoInternet.setVisibility(View.VISIBLE);
                    newsContentWeb.setVisibility(View.GONE);
                } else if (errorCode == -6) {
                    layoutNoInternet.setVisibility(View.VISIBLE);
                    newsContentWeb.setVisibility(View.GONE);
                } else if (isInternetAvailable()) {
                    newsContentWeb.loadUrl(urlText);
                    layoutNoInternet.setVisibility(View.GONE);
                    newsContentWeb.setVisibility(View.VISIBLE);

                } else {
                    layoutNoInternet.setVisibility(View.VISIBLE);
                    newsContentWeb.setVisibility(View.GONE);
                }
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

        });

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInternetAvailable()) {
                    newsContentWeb.loadUrl(urlText);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            layoutNoInternet.setVisibility(View.GONE);
                            newsContentWeb.setVisibility(View.VISIBLE);
                        }
                    }, 5000);

                } else {
                    layoutNoInternet.setVisibility(View.VISIBLE);
                    newsContentWeb.setVisibility(View.GONE);
                }
            }
        });

    }

    private void googleBannerView () {
        LinearLayout adContainer = findViewById(R.id.normal_ad_include);
        com.google.android.gms.ads.AdView adView = new com.google.android.gms.ads.AdView(this);
        adView.setAdSize(com.google.android.gms.ads.AdSize.SMART_BANNER);
        adView.setAdUnitId(bannerRandomdAdIdsGenerator());

        // Initiate a generic request to load it with an ad
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // Place the ad view.
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        adContainer.addView(adView, params);

        adView.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();

            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });

    }


}



/*
    FloatingActionButton fab = findViewById(R.id.fab_share);
        fab.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(shareIntent, "Share link using"));
        }
        });*/
