package heb.apps.mathtrainer.ui.dialogs.quiz

import android.content.Context
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.ui.dialogs.BaseDialog
import heb.apps.mathtrainer.utils.string.format


class ResumeGameDialog(ctx: Context) : BaseDialog.BaseDialogBuilder(ctx) {

    var onContinueBtClicked: () -> Unit = {}
    var onExitBtClicked: () -> Unit = {}

    init {
        setTitle(R.string.resume_game_dialog_title)
        val continueText = ctx.getString(R.string.continue_game)
        setMessage(String.format(ctx, R.string.resume_game_dialog_msg, continueText))
        setCancelable(false)
        setIcon(R.drawable.ic_pause_black_24dp)
        setPositiveButton(continueText) { _,_ -> onContinueBtClicked() }
        setNegativeButton(R.string.exit) { _,_ -> onExitBtClicked() }
    }
}