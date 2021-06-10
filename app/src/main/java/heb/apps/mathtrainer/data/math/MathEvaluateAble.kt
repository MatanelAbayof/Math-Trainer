package heb.apps.mathtrainer.data.math

interface MathEvaluateAble<Input, Output> : MathExpression {

    // evaluate
    fun eval(input: Input? = null) : Output

    // create equation in LaTex
    fun asEquation(input: Input? = null, printInfo: PrintInfo = PrintInfo()) : String
        = "${toLatex(printInfo)} = ${eval(input)}"

}