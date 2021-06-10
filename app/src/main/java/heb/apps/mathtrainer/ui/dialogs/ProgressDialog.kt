package heb.apps.mathtrainer.ui.dialogs

import android.content.Context
import android.view.View
import android.widget.TextView
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.utils.view.LayoutInflateHelper

class ProgressDialog(ctx: Context,
                     msg: String = ctx.getString(R.string.loading))
    : BaseDialog.BaseDialogBuilder(ctx) {

    private lateinit var layoutHolder: LayoutHolder

    init {
        buildView()
        setCancelable(false)
        fillInfo(msg)
    }

    // build view
    private fun buildView() {
        val view = LayoutInflateHelper.inflate(context, R.layout.progress_dialog_view)
        layoutHolder = LayoutHolder(view)
        setView(view)
    }

    // fill info
    private fun fillInfo(msg: String) {
        layoutHolder.tvMsg.text = msg
    }

    private class LayoutHolder(mainView: View) {
        val tvMsg: TextView = mainView.findViewById(R.id.textView_msg)
    }
}