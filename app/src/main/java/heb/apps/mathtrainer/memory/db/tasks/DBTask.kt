package heb.apps.mathtrainer.memory.db.tasks

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AlertDialog
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.ui.dialogs.ProgressDialog
import java.lang.ref.WeakReference

abstract class DBTask<In, Out>(appCtx: Context) : AsyncTask<In, Void, Out>() {
    private val ctxRef: WeakReference<Context> = WeakReference(appCtx)
    val ctx: Context
    get() = ctxRef.get()!!

    private var progressDialog: AlertDialog? = null

    var onFinishListener: (Out?) -> Unit = {} // TODO make this with local typealias

    // start task. may be null if input type is void
    fun start(input: In? = null) {
        //if((input == null) && (In !is Void)) // TODO need to check template type
        //   throw IllegalArgumentException("Cannot pass null to DBTask")


        if(input == null) // here input is void
            execute()
        else
            execute(input)
    }

    // start with dialog
    fun startWithDialog(activityCtx: Context, dialogMsg: String = ctx.getString(R.string.loading),
                        input: In? = null) {
        progressDialog = ProgressDialog(activityCtx, dialogMsg).create().apply { show() }
        start(input)
    }

    override fun onPostExecute(result: Out?) {
        // dismiss progress dialog, if have
        progressDialog?.dismiss()

        onFinishListener(result)
    }
}