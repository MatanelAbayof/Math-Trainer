package heb.apps.mathtrainer.utils.time

import android.content.Context
import heb.apps.mathtrainer.R
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.abs

object TimeFormatter {
    private const val SECOND = 1000L
    private const val MINUTE = 60*SECOND
    private const val HOUR = 60*MINUTE
    private const val DAY = 24*HOUR
    private const val YEAR = 365*DAY

    // maximum time to show in millis (not include)
    private const val MAX_TIME_SHOW_IN_MILLIS = 100L

    // format time
    fun format(ctx: Context, time: Long) : String {
        val absTime = abs(time)
        val negativePrefix = if (time < 0) "-" else ""

        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.HALF_DOWN

        return negativePrefix + when {
            absTime < MAX_TIME_SHOW_IN_MILLIS -> "$absTime ${ctx.getString(R.string.millis)}"
            absTime < MINUTE -> {
                val numOfSeconds = absTime.toDouble()/SECOND
                val formatResult = df.format(numOfSeconds)
                "$formatResult ${ctx.getString(R.string.seconds)}"
            }
            absTime < HOUR -> {
                val numOfMinutes = absTime.toDouble()/MINUTE
                val formatResult = df.format(numOfMinutes)
                "$formatResult ${ctx.getString(R.string.minutes)}"
            }
            absTime < DAY -> {
                val numOfHours = absTime.toDouble()/HOUR
                val formatResult = df.format(numOfHours)
                "$formatResult ${ctx.getString(R.string.hours)}"
            }
            absTime < YEAR -> {
                val numOfDays = absTime.toDouble()/DAY
                val formatResult = df.format(numOfDays)
                "$formatResult ${ctx.getString(R.string.days)}"
            }
            else -> {
                val numOfYears = absTime.toDouble()/YEAR
                val formatResult = df.format(numOfYears)
                "$formatResult ${ctx.getString(R.string.years)}"
            }
        }
    }

}