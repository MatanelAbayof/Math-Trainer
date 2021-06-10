package heb.apps.mathtrainer.ui.dialogs

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.utils.string.format

class RateUsDialog(ctx: Context) : BaseDialog.BaseDialogBuilder(ctx) {

    var onRateUsClicked: () -> Unit = {}
    var onExitClicked: () -> Unit = {}

    init {
        setTitle(R.string.rate_us)
        setIcon(R.drawable.ic_star_orange_24dp)

        val rateUsBtStr = ctx.getString(R.string.rate_us_bt)
        val rateUsBtTxt = SpannableString(rateUsBtStr)
        rateUsBtTxt.setSpan(ForegroundColorSpan(Color.RED), 0, rateUsBtTxt.length, 0)

        setMessage(String.format(ctx, R.string.rate_us_msg, ctx.getString(R.string.app_name)))
        setPositiveButton(rateUsBtTxt) { _, _ ->
            onRateUsClicked()
        }
        setNegativeButton(R.string.exit) { _,_ ->
            onExitClicked()
        }
    }


}