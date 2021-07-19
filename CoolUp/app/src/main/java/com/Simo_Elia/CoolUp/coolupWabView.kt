package com.Simo_Elia.CoolUp

import android.R
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
