package heb.apps.mathtrainer.data.quiz.factory.creators.arithmetic

import android.content.Context
import heb.apps.mathtrainer.data.math.MathEvaluateAble
import heb.apps.mathtrainer.data.math.function.binary.AdditionOperator
import heb.apps.mathtrainer.data.math.function.binary.BinaryOperation
import heb.apps.mathtrainer.data.quiz.factory.creators.QuestionsInfo

class AddQtc(ctx: Context)
	: BinaryArthQtc(ctx, QuestionsInfo.Categories.Arithmetic.Topics.ADDITION_ID) {

	override val justNaturalsRes: Boolean = true

	// calculate sum of all operands
	override fun calcResult(operands: List<Int>): Int = operands.sum()

	// create function of 2 operands
	override fun createBinaryFunction(leftOperand: MathEvaluateAble<Int?, Int>,
									  rightOperand: MathEvaluateAble<Int?, Int>
	): BinaryOperation<Int?, Int> = AdditionOperator(leftOperand, rightOperand)
}

