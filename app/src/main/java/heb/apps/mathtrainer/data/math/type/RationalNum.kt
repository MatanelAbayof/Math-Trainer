package heb.apps.mathtrainer.data.math.type

import heb.apps.mathtrainer.data.math.MathEvaluateAble
import heb.apps.mathtrainer.data.math.PrintInfo

class RationalNum(val numerator: Int, val denominator: Int)
    : MathEvaluateAble<Int?, Int> {

    init {
        validateFields()
    }

    operator fun component1() = numerator
    operator fun component12() = denominator

    override fun eval(input: Int?): Int = numerator/denominator

    override fun toLatex(printInfo: PrintInfo): String {
        return when(printInfo.printMode) {
            PrintInfo.LatexPrintMode.BEGINNER -> "$numerator \\div $denominator"
            PrintInfo.LatexPrintMode.EXPERT ->  "\\frac{$numerator}{$denominator}"
        }
    }

    // validate fields
    private fun validateFields() {
        require(denominator != 0) {
            "Denominator of rational number cannot be 0"
        }
    }

    // TODO: add more operators +,-,*,reduce...
}