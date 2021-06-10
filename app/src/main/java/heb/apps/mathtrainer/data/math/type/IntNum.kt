package heb.apps.mathtrainer.data.math.type

import heb.apps.mathtrainer.data.math.MathEvaluateAble
import heb.apps.mathtrainer.data.math.PrintInfo

class IntNum(var value: Int) : MathEvaluateAble<Int?, Int> {

    // evaluate
    override fun eval(input: Int?) : Int = value

    // convert Math expression to LaTeX
    override fun toLatex(printInfo: PrintInfo) : String = value.toString()
}