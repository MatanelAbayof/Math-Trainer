package heb.apps.mathtrainer.ui.activities.settings

import heb.apps.mathtrainer.ui.activities.MathBaseActivity
import heb.apps.mathtrainer.ui.dialogs.settings.RestartAppDialog


abstract class SettingsActivity : MathBaseActivity() {


    /** show restart app apply changes message */
    protected fun showRestartApplyChangesMsg() {
        RestartAppDialog(this@SettingsActivity).apply {
            onRestartClicked = {
                intentsManager.restartApp()
            }
            create().show()
        }
    }
}
