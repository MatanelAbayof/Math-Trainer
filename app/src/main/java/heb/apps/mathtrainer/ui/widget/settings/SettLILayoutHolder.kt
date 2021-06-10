package heb.apps.mathtrainer.ui.widget.settings

import android.view.View
import android.widget.TextView
import heb.apps.mathtrainer.R

class SettLILayoutHolder(val view: View) {

    // views
    private val tvPrimary: TextView = view.findViewById(R.id.textView_primary)
    private val tvSecond: TextView = view.findViewById(R.id.textView_second)

    constructor(view: View, settListItem: SettListItem) : this(view) {
        setPrimaryText(settListItem.primText)
        setSecondText(settListItem.secText)
    }

    // set primary text
    private fun setPrimaryText(text: String) {
        tvPrimary.text = text
    }

    // set second text
    private fun setSecondText(text: String) {
        if (text.isEmpty()) {
            tvSecond.visibility = View.GONE
            tvSecond.text = ""
        }
        else {
            tvSecond.visibility = View.VISIBLE
            tvSecond.text = text
        }
    }
}