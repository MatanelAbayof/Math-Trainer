package heb.apps.mathtrainer.data.math.function.binary

import heb.apps.mathtrainer.data.math.MathEvaluateAble
import heb.apps.mathtrainer.data.math.PrintInfo

class SubOperator(leftExpression: MathEvaluateAble<Int?, Int>,
                  rightExpression: MathEvaluateAble<Int?, Int>)
    : BinaryOperation<Int?, Int>(leftExpression, rightExpression) {

    // build inner LaTex
    override fun buildInnerLatex(leftExLatex: String, rightExLatex: String, printInfo: PrintInfo): String
            = "$leftExLatex - $rightExLatex"

    // evaluate inner values
    override fun evalInnerValues(leftVal: Int, rightVal: Int): Int = leftVal - rightVal
}