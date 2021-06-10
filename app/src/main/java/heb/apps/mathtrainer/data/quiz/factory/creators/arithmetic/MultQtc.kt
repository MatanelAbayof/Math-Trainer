package heb.apps.mathtrainer.data.quiz.factory.creators.arithmetic

import android.content.Context
import heb.apps.mathtrainer.data.math.MathEvaluateAble
import heb.apps.mathtrainer.data.math.function.binary.BinaryOperation
import heb.apps.mathtrainer.data.math.function.binary.MultOperator
import heb.apps.mathtrainer.data.quiz.factory.creators.QuestionsInfo
import heb.apps.mathtrainer.utils.collection.mult

class MultQtc(ctx: Context)
    : BinaryArthQtc(ctx, QuestionsInfo.Categories.Arithmetic.Topics.MULTIPLICATION_ID) {

    override val justNaturalsRes: Boolean = true

    override val maxOperandPerLd: Int = 4

    override val numOfOperandsPerLd = 0.15f

    override val maxResNumPerLd: Int = 20

    override val minOperandNum: Int = 2

    // calculate sum of all operands
    override fun calcResult(operands: List<Int>): Int = operands.mult()

    // create function of 2 operands
    override fun createBinaryFunction(leftOperand: MathEvaluateAble<Int?, Int>,
                                      rightOperand: MathEvaluateAble<Int?, Int>
    ): BinaryOperation<Int?, Int> = MultOperator(leftOperand, rightOperand)
}