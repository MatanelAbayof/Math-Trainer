package heb.apps.mathtrainer.ui.dialogs.help

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.ProgressBar
import androidx.core.widget.NestedScrollView
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.ui.dialogs.BaseDialog
import heb.apps.mathtrainer.ui.widget.EquationTV
import heb.apps.mathtrainer.utils.LinkHelper
import heb.apps.mathtrainer.utils.view.LayoutInflateHelper

abstract class BaseHelpDialog(ctx: Context) : BaseDialog(ctx) {

    init {
        setIcon(R.drawable.ic_help_outline_black_24dp)
        setButton(DialogInterface.BUTTON_NEUTRAL, context.getString(R.string.close)) { _,_ -> }
    }

    @Deprecated("this is old function",
        ReplaceWith("setHtmlMessage(string)"))
    override fun setMessage(message: CharSequence?) {
        super.setMessage(message)
    }

    // add link button
    protected fun addLinkBt(link: String) {
        setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.more_info)) { _,_ ->
            LinkHelper.openLink(context, link, context.getString(R.string.open_link_err_msg))
        }
    }

    // set equation message
    fun setHtmlMessage(msg: String?) {
        if(msg == null)
            return

        // build view
        val view = LayoutInflateHelper.inflate(context, R.layout.base_help_dialog_view)
        val layoutHolder = LayoutHolder(view)

        layoutHolder.etvHelpMsg.onLoadFinished = {
            // show message
            layoutHolder.pbLoading.visibility = View.GONE
            layoutHolder.nsvEquation.visibility = View.VISIBLE
        }

        // load message
        layoutHolder.etvHelpMsg.text = msg

        setView(view)
    }

    private class LayoutHolder(mainView: View) {
        val etvHelpMsg: EquationTV = mainView.findViewById(R.id.equationTV_helpMsg)
        val nsvEquation: NestedScrollView = mainView.findViewById(R.id.nested_scrollView_eq)
        val pbLoading: ProgressBar = mainView.findViewById(R.id.progressBar_loading)
    }
}