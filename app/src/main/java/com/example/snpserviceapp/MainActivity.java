package com.example.snpserviceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // enable debugging to find out, why menu isn't working
        WebView.setWebContentsDebuggingEnabled(true);

        //find webView element in activity
        myWebView = (WebView) findViewById(R.id.webview);
        //set chrome as webview client In some cases this should help with missing content
        myWebView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings= myWebView.getSettings();
        //Enable javascirpt, needed at least for Microsoft Sing-in
        webSettings.setJavaScriptEnabled(true);

        //In some cases this should help with missing content -> it didn't
        //webSettings.setDomStorageEnabled(true);

        //In other cases this should help with missing content-> it didn't
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setDisplayZoomControls(false);
//        webSettings.setSupportZoom(true);
//        webSettings.setDefaultTextEncodingName("utf-8");

        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("https://snpcom.sharepoint.com/GlobalFunctions/administration/SNP%20Service%20APP/SiteAssets/scripts/index.aspx");
        //myWebView.loadUrl("https://snpcom.sharepoint.com/GlobalFunctions/administration/SNP%20Service%20APP/SitePages/Home.aspx");

    }

    @Override
    public void onBackPressed() {
        if(myWebView.canGoBack()) {
            myWebView.goBack();
        } else{
            super.onBackPressed();
        }
    }
}