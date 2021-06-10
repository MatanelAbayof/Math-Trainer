package heb.apps.mathtrainer.data.math.function.binary

import heb.apps.mathtrainer.data.math.MathEvaluateAble
import heb.apps.mathtrainer.data.math.PrintInfo

class ModOperator(leftExpression: MathEvaluateAble<Int?, Int>,
                  rightExpression: MathEvaluateAble<Int?, Int>)
    : BinaryOperation<Int?, Int>(leftExpression, rightExpression) {

    /** build inner LaTex */
    override fun buildInnerLatex(leftExLatex: String, rightExLatex: String, printInfo: PrintInfo): String
            = "$leftExLatex\\ \\ mod\\ \\ $rightExLatex" // TODO: better way it's to use '\bmod'

    /** evaluate inner values */
    override fun evalInnerValues(leftVal: Int, rightVal: Int): Int = leftVal % rightVal
}