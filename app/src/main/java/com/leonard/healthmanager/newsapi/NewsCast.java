package com.leonard.healthmanager.newsapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.leonard.healthmanager.R;
import com.leonard.healthmanager.listners.RecyclerItemClickListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class NewsCast extends AppCompatActivity {

    // someone else
   // private static final String NEWS_REQUEST_URL = "http://newsapi.org/v2/top-headlines?country=in&apiKey=7615387a23b04e68b9f2b79719d19786";
    //String url2 ="https://covid19datasl.herokuapp.com/countries";
    //3d5998d023614120acefd255e7017c2a
    //86a1e11d6207491fb9c5fefe2d05d1d7  mine
    
    private String NEWS_REQUEST_URL
            = "http://newsapi.org/v2/top-headlines?country=us&category=health&apiKey=86a1e11d6207491fb9c5fefe2d05d1d7";

    private static JSONArray results = null;
    private static JsonArray resultsLowSdk = null;
    private JSONObject resultObject, titleObj = null;
    private JsonObject resultObjectLowSdk = null;
    private String dataSource, dataTitles, dataDescription, dataUrl, dataUrlToImg, dataPublished;
    //private WebView webLoader;

    private ArrayList<NewsModel> newsArrayList = new ArrayList<>();
    private NewsAdapter mAdapter;
    private RecyclerView newsRecycler;
    private CharSequence ago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_cast);

        //webLoader = findViewById(R.id.web_loader);

        LinearLayout adContainer = findViewById(R.id.web_loader);
        WebView webLoader = new WebView(getApplicationContext());
        webLoader.getSettings().setJavaScriptEnabled(true);
        webLoader.getSettings().setAppCacheEnabled(false);
        webLoader.getSettings().setLoadWithOverviewMode(true);
        webLoader.getSettings().setUseWideViewPort(true);

        // Place the ad view.
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        adContainer.addView(webLoader, params);

        RelativeLayout layout = findViewById(R.id.no_internet_louyt);
        RelativeLayout recyleLayout = findViewById(R.id.recyc_data_shower);
        Button retryButton = findViewById(R.id.retry_btn);

        webLoader.loadUrl(NEWS_REQUEST_URL);
        //googleBannerView();

        newsRecycler = findViewById(R.id.recycle_news_data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        newsRecycler.setLayoutManager(mLayoutManager);
        /*newsRecycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                LinearLayoutManager.VERTICAL));*/
        newsRecycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 0));

        if (isInternetAvailable()) {
            webLoader.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);

                    try {
                        if(Build.VERSION.SDK_INT> 22) {

                            JSONParser jsonParser = new JSONParser();
                            JSONObject object = jsonParser.getJSONFromUrl(NEWS_REQUEST_URL);
                            results = (JSONArray) object.get("articles");

                        } else {
                            HttpClient httpclient = new DefaultHttpClient();
                            HttpGet httpget= null;
                            httpget = new HttpGet(NEWS_REQUEST_URL);
                            HttpResponse response = null;
                            try {
                                response = httpclient.execute(httpget);
                            } catch (IOException e) {
                                Log.e("err rslt", e.getMessage());
                            }

                            if(response.getStatusLine().getStatusCode()==200){
                                String server_response = null;
                                try {
                                    server_response = EntityUtils.toString(response.getEntity());
                                    //Log.e("respon", String.valueOf(server_response));
                                    JsonObject jsonObject = new JsonParser().parse(server_response).getAsJsonObject();
                                    //Log.e("respon json", String.valueOf(jsonObject));
                                    resultsLowSdk =  jsonObject.get("articles").getAsJsonArray();
                                    //Log.e("respon rstArr", String.valueOf(resultsLowSdk));

                                } catch (IOException e) {
                                    Log.e("err respon", e.getMessage());
                                }
                                //Log.i("Server response", server_response );

                            } else {
                                Log.i("Server response", "Failed to get server response" );
                            }


                        }
                    } catch (JSONException e) {
                        Log.e("err1", e.getMessage());
                    }

                    if(Build.VERSION.SDK_INT> 22) {
                        for (int i = 0; i < results.length(); i++) {
                            try {
                                resultObject = (JSONObject) results.get(i);
                                try {
                                    dataTitles = resultObject.getString("title");
                                    //dataDescription = resultObject.getString("description");
                                    dataUrl = resultObject.getString("url");
                                    dataUrlToImg = resultObject.getString("urlToImage");
                                    dataPublished = resultObject.getString("publishedAt");

                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                                    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                                    try {
                                        long time = sdf.parse(dataPublished).getTime();
                                        long now = System.currentTimeMillis();
                                        ago = DateUtils
                                                .getRelativeTimeSpanString(time,
                                                        now, DateUtils.MINUTE_IN_MILLIS);

                                        if (dataUrlToImg.equals("null")) {
                                            newsArrayList.add(new NewsModel(dataTitles,dataUrl,
                                                    dataUrlToImg, (String) ago));
                                            Log.e("NewsCast", "dukce");
                                        } else {
                                            newsArrayList.add(new NewsModel(dataTitles,dataUrl,
                                                    dataUrlToImg, (String) ago));
                                            Log.e("NewsCast", "dukse na");
                                        }
                                        //Log.e("ago", String.valueOf(ago));
                                    } catch (ParseException e) {
                                        Log.e("ParseEXP", e.getMessage());
                                    }



                  /*  Log.e("titl", dataTitles);
                    Log.e("titl1", dataDescription);
                    Log.e("tit2l", dataUrl);*/
                                    //Log.e("tit3l", dataUrlToImg);
                                } catch (JSONException e) {
                                    Log.e("err3", e.getMessage());
                                }

                            } catch (JSONException e) {
                                Log.e("err2", e.getMessage());
                            }
                        }

                    } else {
                        for (int i = 0; i < resultsLowSdk.size(); i++) {

                            resultObjectLowSdk = (JsonObject) resultsLowSdk.get(i);

                            try {
                                dataTitles = resultObjectLowSdk.get("title").getAsString();
                                //dataDescription = resultObjectLowSdk.get("description").getAsString();
                                dataUrl = resultObjectLowSdk.get("url").getAsString();
                                dataUrlToImg = resultObjectLowSdk.get("urlToImage").getAsString();
                                dataPublished = resultObjectLowSdk.get("publishedAt").getAsString();
                            } catch (Exception e){
                                Log.e("Exp", e.getMessage());
                            }
                            
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                            try {
                                long time = sdf.parse(dataPublished).getTime();
                                long now = System.currentTimeMillis();
                                ago = DateUtils
                                        .getRelativeTimeSpanString(time,
                                                now, DateUtils.MINUTE_IN_MILLIS);

                                if (dataUrlToImg.equals("null")) {
                                    newsArrayList.add(new NewsModel(dataTitles,dataUrl,
                                            dataUrlToImg, (String) ago));
                                    Log.e("NewsCast", "dukce");
                                } else {
                                    newsArrayList.add(new NewsModel(dataTitles,dataUrl,
                                            dataUrlToImg, (String) ago));
                                    Log.e("NewsCast", "dukse na");
                                }
                                //Log.e("ago", String.valueOf(ago));
                            } catch (ParseException e) {
                                Log.e("ParseEXP", e.getMessage());
                            }



                  /*  Log.e("titl", dataTitles);
                    Log.e("titl1", dataDescription);
                    Log.e("tit2l", dataUrl);*/
                            //Log.e("tit3l", dataUrlToImg);

                        }
                    }

                    mAdapter = new NewsAdapter(newsArrayList);
                    newsRecycler.setAdapter(mAdapter);
                }
            });
        } else {

            layout.setVisibility(View.VISIBLE);
            recyleLayout.setVisibility(View.GONE);
        }

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInternetAvailable()) {
                    layout.setVisibility(View.GONE);
                    recyleLayout.setVisibility(View.VISIBLE);
                } else {
                    layout.setVisibility(View.VISIBLE);
                    recyleLayout.setVisibility(View.GONE);
                }
            }
        });



        newsRecycler.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.onItemClickListener() {
            @Override
            public void OnItem(View view, int i) {
                NewsModel newsModel = newsArrayList.get(i);
                Intent newsWebIntent = new Intent(getApplicationContext(),
                        WebviewNews.class);
                newsWebIntent.putExtra("url", newsModel.getUrl());
                startActivity(newsWebIntent);
            }

        }));

    }

    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e1) {
            try {
                Process p1 = java.lang.Runtime.getRuntime().exec("/system/bin/ping  -W 1 -c 1 www.google.com");
                int returnVal = 0;
                returnVal = p1.waitFor();
                boolean reachable = (returnVal==0);
                return reachable;
            } catch (Exception e2) {
                e2.printStackTrace();
                return false;
            }
        }
    }

    private void googleBannerView () {
        LinearLayout adContainer = findViewById(R.id.normal_ad_include);
        com.google.android.gms.ads.AdView adView = new com.google.android.gms.ads.AdView(this);
        adView.setAdSize(com.google.android.gms.ads.AdSize.SMART_BANNER);
        adView.setAdUnitId(getString(R.string.banner_ad_eight));

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

    /*private void webViewSetter () {

        LinearLayout adContainer = findViewById(R.id.web_loader);
        WebView webView = new WebView(getApplicationContext());

        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl("file:///android_asset/girls_normal.gif");

        // Place the ad view.
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        adContainer.addView(webView, params);
    }*/
}
/**/

// semi Inside
       /* try {
            titleObj = (JSONObject) resultObject.get("location");
            String lat = titleObj.get("lat").toString();
            String lng = titleObj.get("lng").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // direct call
        try {
            dataSource = object.getString("articles");
            Log.e("res", dataSource);
        } catch (JSONException e) {
            Log.e("err", e.getMessage());
        }*/


//https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
//https://github.com/MohammedAbidNafi/News
//https://stackoverflow.com/questions/66656885/getting-error-in-fetching-news-api-from-newsapi-org
////https://newsapi.org/s/us-health-news-api
//https://stackoverflow.com/questions/24116275/java-jsonobject-get-children

//https://github.com/MohammedAbidNafi/News/tree/master/app/src/main/java/com/example/prans/news