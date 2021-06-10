package heb.apps.mathtrainer.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.webkit.WebView
import android.webkit.WebViewClient
import heb.apps.mathtrainer.utils.language.Language

class WebViewCompat @JvmOverloads constructor(ctx: Context,
                     attrs: AttributeSet? = null,
                     defStyleAttr: Int = 0)
    : WebView(getBrilliantContext(ctx)!!, attrs, defStyleAttr) {

    var onLoadFinished: () -> Unit = {}

    // enable java script
    var isJSEnabled: Boolean
    get() = settings.javaScriptEnabled
    @SuppressLint("SetJavaScriptEnabled") set(value) {
        settings.javaScriptEnabled = value
    }

    init {
        isJSEnabled = true
        isVerticalScrollBarEnabled = true
        bindLoadFinishedEvent()
    }

    // load HTML
    fun loadHtml(html: String) {
        loadDataWithBaseURL("", html, "text/html", "UTF-8", "")
    }

    // bind load finished event
    private fun bindLoadFinishedEvent() {
        webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String?) {
                onLoadFinished()
            }

        }
    }

    // prevents text selectable
    override fun performLongClick(): Boolean = true

    companion object {

        // get ctx of webView (that fix bug in older Nougat android versions)
        fun getBrilliantContext(ctx: Context?): Context? =
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N)
                ctx?.createConfigurationContext(Configuration())
            else
                ctx
    }
}

// update gravity of HTML body base on device language
fun WebView.updateDocGravity() {
    when(Language.getDeviceLanguage().getReadingDir()) {
        Language.ReadingDirection.RTL -> injectJS("document.body.style.textAlign = 'right';")
        Language.ReadingDirection.LTR -> injectJS("document.body.style.textAlign = 'left';")
    }
}

// inject java script to URL
fun WebView.injectJS(jsCode: String) {
    loadUrl("javascript: (function() { $jsCode } )();")
}