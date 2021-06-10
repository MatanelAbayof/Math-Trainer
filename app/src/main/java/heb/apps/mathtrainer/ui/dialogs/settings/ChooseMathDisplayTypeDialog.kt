package heb.apps.mathtrainer.ui.dialogs.settings

import android.content.Context
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.data.math.PrintInfo
import heb.apps.mathtrainer.ui.dialogs.BaseDialog

class ChooseMathDisplayTypeDialog(ctx: Context, selectedDisplayItem: PrintInfo.LatexPrintMode) : BaseDialog.BaseDialogBuilder(ctx) {

    private val displayTypes = PrintInfo.LatexPrintMode.values()
    var onChooseDisplayType: (displayType: PrintInfo.LatexPrintMode) -> Unit = {}

    init {
        setTitle(R.string.choose_math_display_type_msg)
        setIcon(R.drawable.ic_math_sum_black_25dp)
        buildDisplayTypesList(ctx, selectedDisplayItem)
    }

    /** build list of display types */
    private fun buildDisplayTypesList(ctx: Context, selectedDisplayItem: PrintInfo.LatexPrintMode) {
        val items = displayTypes.map { it.toString(ctx) }.toTypedArray()
        val selectedDisplayItemIndex = displayTypes.indexOf(selectedDisplayItem)
        setSingleChoiceItems(items, selectedDisplayItemIndex) { dialog, which ->
            val chosenDisplayType = displayTypes[which]
            onChooseDisplayType(chosenDisplayType)
            dialog.dismiss()
        }
    }
}