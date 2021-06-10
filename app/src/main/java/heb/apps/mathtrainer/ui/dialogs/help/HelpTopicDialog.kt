package heb.apps.mathtrainer.ui.dialogs.help

import android.content.Context
import heb.apps.mathtrainer.data.quiz.topic.Topic

class HelpTopicDialog(context: Context, val topic: Topic) : BaseHelpDialog(context) {

    init {
        setTitle(topic.displayName)
        setHtmlMessage(topic.description)
        if(topic.tutorialLink != null) {
            addLinkBt(topic.tutorialLink!!)
        }
    }

}