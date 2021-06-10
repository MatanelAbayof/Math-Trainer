package heb.apps.mathtrainer.ui.widget.settings

import android.content.Context
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.utils.view.LayoutInflateHelper

class SettListItem(val primText: String = "", val secText: String = "") {

    // build view
    fun buildView(context: Context) : SettLILayoutHolder {
        val mainView = LayoutInflateHelper.inflate(context, R.layout.settings_list_item)
        // return layoutHolder
        return SettLILayoutHolder(mainView, this)
    }
}