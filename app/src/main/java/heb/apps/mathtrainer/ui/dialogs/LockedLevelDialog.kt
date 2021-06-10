package heb.apps.mathtrainer.ui.dialogs

import android.content.Context
import android.content.DialogInterface
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.data.quiz.level.UserLevelFullInf
import heb.apps.mathtrainer.ui.widget.StarsView

class LockedLevelDialog(ctx: Context,
                        lastUlFullInf: UserLevelFullInf,
                        currUlFullInf: UserLevelFullInf)
    : BaseDialog(ctx)
{
    init {
        setTitle(R.string.level_locked_dialog_title)
        setIcon(R.drawable.ic_lock_black_24dp)
        setMessage(String.format(ctx.getString(R.string.level_locked_dialog_msg),
            currUlFullInf.level.displayName, lastUlFullInf.level.displayName,
            StarsView.MIN_STARS_FOR_UNLOCK_LEVEL))
        setButton(DialogInterface.BUTTON_POSITIVE, ctx.getString(android.R.string.ok)) {_,_->}
    }

}