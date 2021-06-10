package heb.apps.mathtrainer.ui.activities.settings

import android.os.Bundle
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.ui.dialogs.settings.ChooseMathDisplayTypeDialog
import heb.apps.mathtrainer.ui.widget.settings.SettListItem

class DisplaySettingsActivity : ListSettingsActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildList()
    }

    override fun onItemClick(settListItem: SettListItem, position: Int) {
        when(position) {
            ListItem.MATH_DISPLAY_TYPE.ordinal -> {
                showChooseMathDisplayTypeDialog()
            }
        }
    }

    override fun buildSettingsListItems() : List<LiUpdateEvent> {
        val listItemsManager = mutableListOf<LiUpdateEvent>()

        listItemsManager.add {
            SettListItem(getString(R.string.math_display_type), getMathDisplayTypeText())
        }

        return listItemsManager
    }

    /** show choose math display type dialog */
    private fun showChooseMathDisplayTypeDialog() {
        ChooseMathDisplayTypeDialog(this@DisplaySettingsActivity,
                                    settingsSp.mathDisplayType).apply {
            onChooseDisplayType = { displayType ->
                settingsSp.mathDisplayType = displayType
                updateSettLI(ListItem.MATH_DISPLAY_TYPE.ordinal)
                showRestartApplyChangesMsg()
            }
            create().show()
        }
    }

    /** get math display type text */
    private fun getMathDisplayTypeText() : String = settingsSp.mathDisplayType.toString(this)


    private enum class ListItem {
        MATH_DISPLAY_TYPE
    }
}
