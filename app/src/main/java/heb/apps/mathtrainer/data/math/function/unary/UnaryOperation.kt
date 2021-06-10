package heb.apps.mathtrainer.data.math.function.unary

import heb.apps.mathtrainer.data.math.MathEvaluateAble
import heb.apps.mathtrainer.data.math.PrintInfo

abstract class UnaryOperation<Input, Output>(var expression: MathEvaluateAble<Input?, Output>)
    : MathEvaluateAble<Input?, Output> {

    // evaluate
    override fun eval(input: Input?) : Output = evalOperation(expression.eval(input))

    // evaluate operation
    protected abstract fun evalOperation(expressionResult: Output) : Output

    // convert Math expression to LaTeX
    override fun toLatex(printInfo: PrintInfo) : String {
        val innerPrintInfo = printInfo.copy(isRootExpression = false)
        return buildInnerLatex(expression.toLatex(innerPrintInfo), printInfo)
    }

    // build inner LaTex
    protected abstract fun buildInnerLatex(exLatex: String, printInfo: PrintInfo) : String
}