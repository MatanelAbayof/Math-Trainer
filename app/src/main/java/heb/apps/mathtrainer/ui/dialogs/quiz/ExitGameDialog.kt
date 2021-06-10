package heb.apps.mathtrainer.ui.dialogs.quiz

import android.content.Context
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.ui.dialogs.BaseDialog

class ExitGameDialog(ctx: Context) : BaseDialog.BaseDialogBuilder(ctx) {

    var onExit: () -> Unit = {}

    init {
        setTitle(R.string.exit_game_dialog_title)
        setMessage(R.string.exit_game_dialog_msg)
        setIcon(R.drawable.ic_action_exit_black)
        setPositiveButton(android.R.string.ok) { _,_ -> onExit() }
        setNegativeButton(android.R.string.cancel) { _,_ -> }
    }

}