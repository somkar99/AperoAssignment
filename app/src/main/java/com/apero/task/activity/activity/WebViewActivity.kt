package com.apero.task.activity.activity

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apero.task.R
import kotlinx.android.synthetic.main.activity_webview.*


class WebViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        init()
    }

    private fun init() {

        var lsWebsite = intent.extras!!.getString("website")


        webview.getSettings().builtInZoomControls = true
        webview.getSettings().useWideViewPort = true
        webview.getSettings().loadWithOverviewMode = true
        webview.getSettings().javaScriptEnabled = true

        webview.loadUrl(Uri.parse(lsWebsite).toString())


    }


}
