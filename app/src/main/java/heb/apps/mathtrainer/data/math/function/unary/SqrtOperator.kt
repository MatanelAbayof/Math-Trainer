package heb.apps.mathtrainer.data.math.function.unary

import heb.apps.mathtrainer.data.math.MathEvaluateAble
import heb.apps.mathtrainer.data.math.PrintInfo
import kotlin.math.sqrt

class SqrtOperator(innerExpression: MathEvaluateAble<Int?, Int>)
    : UnaryOperation<Int?, Int>(innerExpression) {

    // evaluate unary operation
    override fun evalOperation(expressionResult: Int): Int
        = sqrt(expressionResult.toDouble()).toInt()

    // build inner LaTex
    override fun buildInnerLatex(exLatex: String, printInfo: PrintInfo): String
        = "\\sqrt{$exLatex}"

}