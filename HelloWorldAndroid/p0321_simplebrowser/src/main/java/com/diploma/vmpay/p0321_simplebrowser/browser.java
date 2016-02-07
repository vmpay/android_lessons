package com.diploma.vmpay.p0321_simplebrowser;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class browser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        WebView webView = (WebView) findViewById(R.id.webView);
        Uri data = getIntent().getData();
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(data.toString());
    }
}
