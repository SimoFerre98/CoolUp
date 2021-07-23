package com.Simo_Elia.CoolUp.Settings

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.coolup_webview.*


class coolupWabView() : Activity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(com.Simo_Elia.CoolUp.R.layout.coolup_webview)

        WebView()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun WebView()
    {
        webview.webViewClient = WebViewClient()
        webview.apply {

            loadUrl("http://coolup.educationhost.cloud")
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
        }
    }
}
