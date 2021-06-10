package heb.apps.mathtrainer.ui.dialogs.tutorial

import android.content.Context
import android.view.View
import android.widget.CheckBox
import android.widget.ProgressBar
import androidx.annotation.RawRes
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.ui.dialogs.BaseDialog
import heb.apps.mathtrainer.ui.widget.WebViewCompat
import heb.apps.mathtrainer.utils.getRawTextFile
import heb.apps.mathtrainer.utils.view.LayoutInflateHelper

abstract class BaseTutorialDialog(ctx: Context,
                                  title: String = ctx.getString(R.string.tutorial_dialog_title),
                                  tutorialData: String)
    : BaseDialog(ctx) {

    private lateinit var layoutHolder: LayoutHolder

    var onClosedListener: (showAgain: Boolean) -> Unit = {}

    init {
        setTitle(title)
        setCancelable(false)

        setIcon(R.drawable.ic_live_help_black_24dp)

        buildView(tutorialData)

        setButton(BUTTON_POSITIVE, android.R.string.ok) {
            val showAgain = !layoutHolder.cbNotShowAgain.isChecked
            onClosedListener(showAgain)
        }
    }

    constructor(ctx: Context, title: String, @RawRes tutorialResId: Int)
            : this(ctx, title, ctx.resources.getRawTextFile(tutorialResId))

    // build view
    private fun buildView(tutorialData: String) {
        val mainView = LayoutInflateHelper.inflate(context, R.layout.base_tutorial_dialog_view)
        layoutHolder = LayoutHolder(mainView)

        layoutHolder.wvTutorial.onLoadFinished = {
            layoutHolder.pbLoading.visibility = View.GONE
            layoutHolder.wvTutorial.visibility = View.VISIBLE
        }

        layoutHolder.wvTutorial.loadHtml(tutorialData)

        setView(mainView)
    }

    private class LayoutHolder(mainView: View) {
        val wvTutorial: WebViewCompat = mainView.findViewById(R.id.webViewCompat_tutorial)
        val pbLoading: ProgressBar = mainView.findViewById(R.id.progressBar_loading)
        val cbNotShowAgain: CheckBox = mainView.findViewById(R.id.checkBox_notShowAgain)
    }

}