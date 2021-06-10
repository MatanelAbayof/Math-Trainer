package heb.apps.mathtrainer.data.math.function.binary

import heb.apps.mathtrainer.data.math.MathEvaluateAble
import heb.apps.mathtrainer.data.math.PrintInfo

abstract class BinaryOperation<Input, Output>(var leftExpression: MathEvaluateAble<Input, Output>,
                                              var rightExpression: MathEvaluateAble<Input, Output>)
    : MathEvaluateAble<Input, Output> {

    // convert to LaTex
    override fun toLatex(printInfo: PrintInfo) : String {
        val innerPrintInfo = printInfo.copy(isRootExpression = false)
        var expression = buildInnerLatex(leftExpression.toLatex(innerPrintInfo),
                            rightExpression.toLatex(innerPrintInfo), printInfo)
        if(!printInfo.isRootExpression && printInfo.isParenthesesUsed)
            expression = "($expression)"
        return expression
    }

    // build inner LaTex
    protected abstract fun buildInnerLatex(leftExLatex: String, rightExLatex: String,
                                           printInfo: PrintInfo) : String

    // evaluate inner values
    protected abstract fun evalInnerValues(leftVal: Output, rightVal: Output) : Output

    // evaluate
    override fun eval(input: Input?) : Output {
        val leftVal = leftExpression.eval(input)
        val rightVal = rightExpression.eval(input)
        return evalInnerValues(leftVal, rightVal)
    }
}