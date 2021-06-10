package heb.apps.mathtrainer.utils.view

import android.view.View
import android.view.ViewGroup

// add view with match parent at width
fun ViewGroup.addMatchPrntView(view: View) {
    addView(view, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT))
}