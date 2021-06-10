package heb.apps.mathtrainer.ui.dialogs

import android.content.Context
import heb.apps.mathtrainer.R

class WhatsNewDialog(ctx: Context) : WebViewDialog(ctx) {

    init {
        buildWebView(R.raw.whats_new)
        setTitle(R.string.whats_new_dialog_title)
        setIcon(R.drawable.ic_fiber_new_black_24dp)
    }

}