package heb.apps.mathtrainer.data.math.`var`

import heb.apps.mathtrainer.data.math.MathEvaluateAble
import heb.apps.mathtrainer.data.math.PrintInfo

abstract class IntSingleVar(val varSymbol: String) : MathEvaluateAble<Int?, Int> {

    // evaluate
    override fun eval(input: Int?): Int {
        require(input != null) {
            "Input must have value for '$varSymbol'"
        }
        return input
    }

    // convert Math expression to LaTeX
    override fun toLatex(printInfo: PrintInfo): String = varSymbol

}