package heb.apps.mathtrainer.ui.activities.settings

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.memory.sp.settings.SettingsSp
import heb.apps.mathtrainer.ui.widget.settings.SettListAdapter
import heb.apps.mathtrainer.ui.widget.settings.SettListItem

abstract class ListSettingsActivity
    : SettingsActivity(),
      AdapterView.OnItemClickListener,
      OnItemClickListener {

    private lateinit var layoutHolder: LayoutHolder
    private var onItemClickListener: OnItemClickListener? = null
    protected lateinit var settingsSp: SettingsSp
    // control list items updates
    private var listItemsManager: List<LiUpdateEvent> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsSp = SettingsSp(this)
        setActivityView(R.layout.activity_settings)
        layoutHolder = LayoutHolder()
    }

    // build list
    protected fun buildList() {
        // build list
        listItemsManager = buildSettingsListItems()
        buildSettList()

        // listen list clicks
        setOnItemClickListener(this)
    }

    // build settings list
    private fun buildSettList() {
        // build list items
        val items: List<SettListItem> = listItemsManager.map { it() }
        setSettListItems(items)
    }

    // update all settings list items
    protected fun updateAllSetLI() {
        listItemsManager.forEachIndexed{ index,_ ->
            updateSettLI(index)
        }
    }

    // update list item at position
    protected fun updateSettLI(position: Int) {
       val newLI = listItemsManager[position]()
        getSettListAdapter().setItem(newLI, position)
    }

    // get settings list adapter
    private fun getSettListAdapter() : SettListAdapter =  layoutHolder.lv.adapter as SettListAdapter

    // set items at settings list
    private fun setSettListItems(items: List<SettListItem>) {
        layoutHolder.lv.adapter = SettListAdapter(applicationContext, items)
        // listen click event
        layoutHolder.lv.onItemClickListener = this
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        callOnLIClick(getSettListAdapter().getItem(position)!!, position)
    }

    /** listen list items click event */
    private fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    /** call on list if can */
    private fun callOnLIClick(settListItem: SettListItem, position: Int) {
        onItemClickListener?.onItemClick(settListItem, position)
    }

    /** build settings list items */
    protected abstract fun buildSettingsListItems() : List<LiUpdateEvent>

    /** event that called when settings item clicked */
    abstract override fun onItemClick(settListItem: SettListItem, position: Int)

    private inner class LayoutHolder {
        val lv: ListView = findViewById(R.id.listView_settings)
    }
}

typealias LiUpdateEvent = () -> SettListItem

interface OnItemClickListener {
    fun onItemClick(settListItem: SettListItem, position: Int)
}