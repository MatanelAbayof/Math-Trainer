package heb.apps.mathtrainer.data.math

import android.content.Context
import heb.apps.mathtrainer.R

interface MathExpression {

    /** convert Math expression to LaTeX */
    fun toLatex(printInfo: PrintInfo = PrintInfo()) : String

    /** convert to question */
    fun asQuestion(printInfo: PrintInfo = PrintInfo()) : String = "${toLatex(printInfo)} ={}?"

}

/** print info */
data class PrintInfo(var printMode: LatexPrintMode = LatexPrintMode.BEGINNER,
                     var isParenthesesUsed: Boolean = true,
                     var isRootExpression: Boolean = true) {

    /** print mode of LaTex */
    enum class LatexPrintMode {
        BEGINNER, EXPERT;

        /** convert to string */
        fun toString(ctx: Context): String {
            val displayTypes = ctx.resources.getStringArray(R.array.math_display_types)
            return displayTypes[ordinal]
        }
    }

}

