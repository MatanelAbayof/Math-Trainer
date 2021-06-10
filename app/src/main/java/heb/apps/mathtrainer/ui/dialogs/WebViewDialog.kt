package heb.apps.mathtrainer.ui.dialogs

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.RawRes
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.ui.widget.WebViewCompat
import heb.apps.mathtrainer.utils.string.StrHelper
import heb.apps.mathtrainer.utils.view.LayoutInflateHelper

abstract class WebViewDialog(ctx: Context) : BaseDialog(ctx) {

    private lateinit var layoutHolder: LayoutHolder

    init {
        setButton(DialogInterface.BUTTON_POSITIVE,
            context.getString(android.R.string.ok)) { dialog,_ -> dialog.dismiss() }
    }


    /** build web view with resource id of HTML data */
    protected fun buildWebView(@RawRes htmlResId: Int) {
        val htmlData = StrHelper.readStr(context, htmlResId)
        buildWebView(htmlData)
    }

    /** build web view with HTML data */
    protected fun buildWebView(htmlData: String) {
        val mainView = LayoutInflateHelper.inflate(context, R.layout.webview_dialog)
        layoutHolder = LayoutHolder(mainView)

        layoutHolder.wvTutorial.onLoadFinished = {
            layoutHolder.pbLoading.visibility = View.GONE
            layoutHolder.wvTutorial.visibility = View.VISIBLE
        }

        layoutHolder.wvTutorial.loadHtml(htmlData)

        setView(mainView)
    }

    private class LayoutHolder(mainView: View) {
        val wvTutorial: WebViewCompat = mainView.findViewById(R.id.webViewCompat)
        val pbLoading: ProgressBar = mainView.findViewById(R.id.progressBar_loading)
    }
}