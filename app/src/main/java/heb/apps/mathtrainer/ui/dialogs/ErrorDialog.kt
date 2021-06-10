package heb.apps.mathtrainer.ui.dialogs

import android.content.Context
import heb.apps.mathtrainer.R

class ErrorDialog(context: Context, message: String? = null) : BaseDialog.BaseDialogBuilder(context) {

    init {
        setTitle(R.string.error)
        setIcon(R.drawable.ic_action_cancel_red)
        if(message != null) setMessage(message)
    }
}