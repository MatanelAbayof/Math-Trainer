package heb.apps.mathtrainer.data.quiz.factory.creators.arithmetic

import android.content.Context
import heb.apps.mathtrainer.data.math.MathEvaluateAble
import heb.apps.mathtrainer.data.math.function.binary.BinaryOperation
import heb.apps.mathtrainer.data.math.function.binary.SubOperator
import heb.apps.mathtrainer.data.quiz.factory.creators.QuestionsInfo

class SubQtc(ctx: Context)
	: BinaryArthQtc(ctx, QuestionsInfo.Categories.Arithmetic.Topics.SUBTRACTION_ID) {

	override val justNaturalsRes: Boolean = false

	// calculate sub of first operands against sum of all other operands
	// (i.e. res = a1 - (a2 + a3 + ... + an))
	override fun calcResult(operands: List<Int>): Int
            = if(operands.isEmpty()) 0
              else operands[0] - operands.slice(IntRange(1, operands.size-1)).sum()

	// create function of 2 operands
	override fun createBinaryFunction(leftOperand: MathEvaluateAble<Int?, Int>,
									  rightOperand: MathEvaluateAble<Int?, Int>
	): BinaryOperation<Int?, Int> = SubOperator(leftOperand, rightOperand)
}