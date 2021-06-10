package heb.apps.mathtrainer.ui.dialogs.help

import android.content.Context
import heb.apps.mathtrainer.data.quiz.level.Level


class HelpLevelDialog(context: Context, val level: Level) : BaseHelpDialog(context) {

    init {
        setTitle(level.displayName)
        setHtmlMessage(level.tutorialTextInfo)
        if(level.tutorialLink != null) {
            addLinkBt(level.tutorialLink!!)
        }
    }
}