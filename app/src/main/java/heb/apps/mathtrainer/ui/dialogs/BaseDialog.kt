package heb.apps.mathtrainer.ui.dialogs

import android.content.Context
import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog

abstract class BaseDialog : AlertDialog {

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, themeResId: Int) : super(ctx, themeResId)
    constructor(
        ctx: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(ctx, cancelable, cancelListener)


    // set view with margin at all sides
    fun setView(view: View, margin: Int) {
        setView(view, margin, margin, margin, margin)
    }

    // set button
    fun setButton(whichButton: Int, textResId: Int, clickListener: () -> Unit) {
        val text = context.getString(textResId)
        setButton(whichButton, text) { _,_ ->
            clickListener()
        }
    }

    abstract class BaseDialogBuilder(ctx: Context) : AlertDialog.Builder(ctx)
}

