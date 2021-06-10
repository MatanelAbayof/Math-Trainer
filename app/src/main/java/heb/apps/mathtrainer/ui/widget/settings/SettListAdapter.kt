package heb.apps.mathtrainer.ui.widget.settings

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class SettListAdapter(context: Context, items: List<SettListItem>)
    : ArrayAdapter<SettListItem>(context, 0, items) { // no need recourse file

    // settings list items
    private var settListItems: MutableList<SettListItem> = mutableListOf()

    init {
        settListItems = items.toMutableList()
    }

    override fun getItem(position: Int): SettListItem? {
        return settListItems[position]
    }

    // set item at position
    fun setItem(settListItem: SettListItem, position: Int) {
        settListItems[position] = settListItem
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view = convertView

        // get settings list item info
        val settLI = getItem(position)

        // check if list item not built already
        if (settLI != null) {
            val settLILayout = settLI.buildView(context)
            view = settLILayout.view
        }

        return view!!
    }

    companion object {
        private const val LOG_TAG = "SettListAdapter"
    }


}