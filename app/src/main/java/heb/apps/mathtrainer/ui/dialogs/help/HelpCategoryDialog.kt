package heb.apps.mathtrainer.ui.dialogs.help

import android.content.Context
import heb.apps.mathtrainer.data.quiz.category.Category

class HelpCategoryDialog(context: Context, val category: Category) : BaseHelpDialog(context) {

    init {
        setTitle(category.displayName)
        setHtmlMessage(category.description)
        if(category.descriptionLink != null) {
            addLinkBt(category.descriptionLink!!)
        }
    }
}