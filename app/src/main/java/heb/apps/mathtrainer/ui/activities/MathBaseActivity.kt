package heb.apps.mathtrainer.ui.activities

import android.os.Bundle
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.ui.activities.intents.MathIntentsManager

abstract class MathBaseActivity(displayBackMenuBt: Boolean = true, allowExit: Boolean = true)
    : BaseActivity(displayBackMenuBt, allowExit) {

    override val taskSwitcherIconResId: Int = R.mipmap.ic_launcher_round
    protected lateinit var intentsManager: MathIntentsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intentsManager = MathIntentsManager(this)
    }
}
