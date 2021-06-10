package heb.apps.mathtrainer.utils

import android.content.Context
import heb.apps.mathtrainer.R

object MarketHelper {

    // launch Google play market app by app package
    fun launchMarket(ctx: Context, appPackage: String) {
        val link = ctx.getString(R.string.market_pre_app_url) + appPackage
        LinkHelper.openLink(ctx, link, ctx.getString(R.string.open_market_err_msg))
    }
}