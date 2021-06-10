package heb.apps.mathtrainer.data.quiz.factory.creators.arithmetic

import android.content.Context
import heb.apps.mathtrainer.data.math.MathEvaluateAble
import heb.apps.mathtrainer.data.math.function.binary.AdditionOperator
import heb.apps.mathtrainer.data.math.function.binary.BinaryOperation
import heb.apps.mathtrainer.data.math.function.binary.MultOperator
import heb.apps.mathtrainer.data.math.function.binary.SubOperator
import heb.apps.mathtrainer.data.math.type.IntNum
import heb.apps.mathtrainer.data.math.utils.IntegerRange
import heb.apps.mathtrainer.data.math.utils.RandomHelper
import heb.apps.mathtrainer.data.quiz.factory.creators.QuestionsInfo
import kotlin.math.floor

/** operator input */
private typealias OperatorInput = Pair<MathEvaluateAble<Int?, Int>, MathEvaluateAble<Int?, Int>>
/** operator generator */
private typealias OperatorGen = (OperatorInput) -> BinaryOperation<Int?, Int>

class MixAsmQtc(ctx: Context)
    : ArithmeticQtc(ctx, QuestionsInfo.Categories.Arithmetic.Topics.MIX_ASM_ID) {

    override val justNaturalsRes: Boolean = false
    override val maxResNumPerLd: Int = 10
    override val maxDscCorrectAnswerPerLd: Int = 5

    /** random correct answer */
    override fun randCorrectAnswer(levelDiff: Int) : MathEvaluateAble<Int?, Int> {
        val operandNumRange = getOperandRange(levelDiff)
        val numOfOperands = getNumOfOperands(levelDiff)

        var correctAnswer: MathEvaluateAble<Int?, Int>? = null
        tryRandResNumWithLimit(levelDiff, ({
            // rand operands
            val operands = randOperands(numOfOperands, operandNumRange)

            // build answer
            correctAnswer = buildRandCorrectAnswer(operands)
            val resNum = correctAnswer!!.eval()
            resNum
        }))
        return correctAnswer!!
    }

    /** build random correct answer from list of operands */
    private fun buildRandCorrectAnswer(operands: List<Int>) : MathEvaluateAble<Int?, Int> {
        require(operands.isNotEmpty()) {
            "Cannot build correct answer with empty operands list"
        }

        if(operands.size == 1) {
            return IntNum(operands.first())
        }

        if(operands.size == MIN_NUM_OPERANDS) {
            val operatorInput = OperatorInput(IntNum(operands[0]), IntNum(operands[1]))
            return randOperator(operatorInput)
        }

        // slice operands to into 2 parts
        val cutIndex = RandomHelper.next(IntegerRange(1, operands.size))
        val leftSideOperands = operands.subList(0, cutIndex)
        val rightSideOperands = operands.subList(cutIndex, operands.size)

        // build answers pf 2 parts
        val leftAnswer = buildRandCorrectAnswer(leftSideOperands)
        val rightAnswer = buildRandCorrectAnswer(rightSideOperands)

        // create answer with this 2 answers
        val operatorInput = OperatorInput(leftAnswer, rightAnswer)
        return randOperator(operatorInput)
    }

    /** random operands */
    private fun randOperands(numOfOperands: Int, operandNumRange: IntegerRange) : List<Int>
        = List(numOfOperands) { RandomHelper.next(operandNumRange) }

    /** random operator */
    private fun randOperator(input: OperatorInput)
            : BinaryOperation<Int?, Int> {
        val operatorGenerator = operatorsFactory.random()
        return operatorGenerator(input)
    }

    /** get number of operands */
    private fun getNumOfOperands(levelDiff: Int) : Int
            = floor((levelDiff.toFloat()*NUM_OPERANDS_PER_LD) + MIN_NUM_OPERANDS).toInt()

    /** get operand range */
    private fun getOperandRange(levelDiff: Int) : IntegerRange
        = IntegerRange(MIN_OPERAND_NUM, MAX_OPERAND_NUM_PER_LD*levelDiff+1)

    companion object {
        /** minimum operand number */
        private const val MIN_OPERAND_NUM = 1
        /** relation of maximum operand number per level difficulty */
        private const val MAX_OPERAND_NUM_PER_LD = 5
        /** minimum number of operands */
        private const val MIN_NUM_OPERANDS = 2
        /** relation of number of operands per level difficulty */
        private const val NUM_OPERANDS_PER_LD = 0.2f
        /** factory of operators */
        private val operatorsFactory: List<OperatorGen> = listOf(
            ({ (l, r) -> AdditionOperator(l, r) }),
            ({ (l, r) -> SubOperator(l, r) }),
            ({ (l, r) -> MultOperator(l, r) })
        )
    }
}