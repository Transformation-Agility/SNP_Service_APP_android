package com.snpgroup.snpserviceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find webView element in activity
        myWebView = (WebView) findViewById(R.id.webview);

        //Set webview as client to be used for links and prevent browser from opening
        myWebView.setWebViewClient(new SNPWebViewClient(this));

        //Enable javascirpt, needed at least for Microsoft Sing-in
        myWebView.getSettings().setJavaScriptEnabled(true);

        //Fake user-agent string to appear as a mobile browser and not a webview
        //Otherwise Sharepoint wouldn't render the menu
        myWebView.getSettings().setUserAgentString(
                myWebView.getSettings().getUserAgentString()
                .replaceAll("; wv", "" )
                .replaceAll("Version.[0-9]{1,2}.[0-9]{0,2} ", "" ));

        //Load app landing page
        myWebView.loadUrl("https://snpcom.sharepoint.com/GlobalFunctions/administration/SNP%20Service%20APP/SiteAssets/scripts/index.aspx");

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