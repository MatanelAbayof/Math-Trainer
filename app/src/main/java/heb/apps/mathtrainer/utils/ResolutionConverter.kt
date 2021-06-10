package heb.apps.mathtrainer.utils

import android.content.Context
import android.util.TypedValue

object ResolutionConverter {

    // convert DIP to pixels
    fun dipToPx(ctx: Context, dip: Float) : Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dip,
        ctx.resources.displayMetrics
    )

    // convert DIP to pixels
    fun dipToPx(ctx: Context, dip: Int) : Int = dipToPx(ctx, dip.toFloat()).toInt()
}