package heb.apps.mathtrainer.ui.dialogs.settings

import android.content.Context
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.ui.dialogs.BaseDialog

class RestartAppDialog(ctx: Context) : BaseDialog.BaseDialogBuilder(ctx) {
    var onRestartClicked: () -> Unit = {}

    init {
        setTitle(R.string.restart_app_dialog_title)
        setMessage(R.string.restart_app_dialog_msg)
        setIcon(R.drawable.ic_refresh_black_24dp)
        setPositiveButton(R.string.restart_app) { _, _ ->
            onRestartClicked()
        }
        setNeutralButton(R.string.later) { _,_ -> }
    }
}