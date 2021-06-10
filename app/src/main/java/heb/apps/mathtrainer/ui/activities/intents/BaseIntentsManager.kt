package heb.apps.mathtrainer.ui.activities.intents

import android.content.Intent
import heb.apps.mathtrainer.ui.activities.BaseActivity

abstract class BaseIntentsManager(val activity: BaseActivity) {
    /** launch activity of the application */
    protected abstract val launchActivity: Class<*>

    /** create intent */
    protected fun createIntent(targetClass: Class<*>) : Intent = Intent(activity, targetClass)

    /** start activity */
    protected fun startActivity(targetClass: Class<*>, requestCode: Int? = null) {
        val intent = createIntent(targetClass)
        startActivity(intent, requestCode)
    }

    /** start activity with request code */
    protected fun startActivity(intent: Intent, requestCode: Int? = null) {
        when(requestCode) {
            null -> activity.startActivity(intent)
            else -> activity.startActivityForResult(intent, requestCode)
        }
    }

    /** restart application */
    fun restartApp() {
        val intent = createIntent(launchActivity)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        Runtime.getRuntime().exit(0)
    }

}