package heb.apps.mathtrainer.data.math.function.binary

import heb.apps.mathtrainer.data.math.MathEvaluateAble
import heb.apps.mathtrainer.data.math.PrintInfo

class MultOperator(leftExpression: MathEvaluateAble<Int?, Int>,
                   rightExpression: MathEvaluateAble<Int?, Int>)
    : BinaryOperation<Int?, Int>(leftExpression, rightExpression) {

    // build inner LaTex
    override fun buildInnerLatex(leftExLatex: String, rightExLatex: String, printInfo: PrintInfo): String {
      return when(printInfo.printMode) {
          PrintInfo.LatexPrintMode.BEGINNER -> "$leftExLatex \\times $rightExLatex"
          PrintInfo.LatexPrintMode.EXPERT -> "$leftExLatex \\cdot $rightExLatex"
      }
    }

    // evaluate inner values
    override fun evalInnerValues(leftVal: Int, rightVal: Int): Int = leftVal * rightVal
}