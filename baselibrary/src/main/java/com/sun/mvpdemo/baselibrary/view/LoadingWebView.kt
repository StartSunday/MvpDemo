package com.sun.mvpdemo.baselibrary.view

import android.annotation.SuppressLint
import android.content.Context
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.github.lzyzsd.jsbridge.BridgeWebView
import com.sun.mvpdemo.baselibrary.R

/**
 * @author  sun
 * @data 2019/3/31
 * @Explain
 */
class LoadingWebView (context: Context, attrs: AttributeSet?) : androidx.swiperefreshlayout.widget.SwipeRefreshLayout(context, attrs) {

    private val mWebView: BridgeWebView

    init {
        val v = LayoutInflater.from(context).inflate(R.layout.view_webview, this, true)
        //        BaseHelper.initSwipeRefreshLayoutColor(this);
        mWebView = v.findViewById(R.id.bridge_webView)
        init(context)
        addLister()
    }

    fun loadUrl(url: String) {
        post {
            mWebView.loadUrl(url)
            isRefreshing = true
        }
    }

    fun loadBodyHtml(bodyHtml: String) {
        post {
            mWebView.loadDataWithBaseURL(null, getHtmlData(bodyHtml), "text/html", "utf-8", null)
            isRefreshing = false
        }
    }

    private fun addLister() {
        setOnRefreshListener { mWebView.reload() }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init(context: Context) {
        val webSettings = mWebView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.setAppCacheMaxSize((1024 * 1024 * 8).toLong())
        val appCacheDir = this.context.getDir("cache", Context.MODE_PRIVATE).path
        webSettings.setAppCachePath(appCacheDir)
        webSettings.allowFileAccess = true
        webSettings.setSupportZoom(true)
        webSettings.setAppCacheEnabled(true)
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT

        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        mWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                //                view.loadUrl(url);

                return true
            }
        }
        mWebView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress == 100) {
                    post { isRefreshing = false }
                }
                super.onProgressChanged(view, newProgress)
            }
        }
        mWebView.setBackgroundColor(0)
        mWebView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY //滚动条样式
        mWebView.isVerticalScrollBarEnabled = false //显示滚动条
    }

    /**
     * 加载html标签
     *
     * @param bodyHTML
     * @return
     */
    private fun getHtmlData(bodyHTML: String): String {
        return "<style>\n" +
                "\t\tp,span,b{\n" +
                "\t\t\tfont: normal 18px \"Microsoft YaHei\" !important;\n" +
                "\t\t\tline-height: 50px !important;\n" +
                "\t\t\tfont-size:45px !important;\n" +
                "\t\t\tcolor: #000 !important;\n" +
                "\t\t}\n" +
                "\t\th1,h2,h3,h4,h5,h6{\n" +
                "\t\t\tfont:normal 30px \"Microsoft YaHei\" !important;\n" +
                "\t\t\tline-height: 46px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "\t</style>" + bodyHTML
    }
}