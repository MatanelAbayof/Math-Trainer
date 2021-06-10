package heb.apps.mathtrainer.data.quiz.factory.creators.arithmetic

import android.content.Context
import heb.apps.mathtrainer.data.math.function.unary.SqrtOperator
import heb.apps.mathtrainer.data.math.type.IntNum
import heb.apps.mathtrainer.data.math.utils.IntegerRange
import heb.apps.mathtrainer.data.math.utils.RandomHelper
import heb.apps.mathtrainer.data.math.utils.at
import heb.apps.mathtrainer.data.quiz.factory.creators.QuestionsInfo

class SqrtQtc(ctx: Context)
    : ArithmeticQtc(ctx, QuestionsInfo.Categories.Arithmetic.Topics.SQRT_ID) {

    override val justNaturalsRes: Boolean = true
    override val maxResNumPerLd: Int = 10
    override val maxDscCorrectAnswerPerLd: Int = 5
    override val maxAbsoluteDscCorrectAnswer: Int = 30

    /**
     * random a number
     */
    override fun randCorrectAnswer(levelDiff: Int) : SqrtOperator {
        val resultRange = getResultNumRange(levelDiff)
        val operandNumRange = getOperandRange(levelDiff)

        var correctAnswer: SqrtOperator? = null
        tryRandResNumWithLimit(levelDiff, ({
            // rand operand
            var resNum: Int
            var operandNum: Int
            do {
                resNum = RandomHelper.next(resultRange)
                operandNum = resNum*resNum
            } while(!(operandNum at operandNumRange))

            // build question
            correctAnswer = SqrtOperator(IntNum(operandNum))
            resNum
        }))
        return correctAnswer!!
    }

    /**
     * get operand of square root range
     */
    private fun getOperandRange(levelDiff: Int) : IntegerRange {
        val minNum = MIN_OPERAND_NUM
        val maxNum = MAX_OPERAND_NUM_PER_LD*levelDiff + 1
        return getFixedRange(minNum, maxNum)
    }

    companion object {
        /** minimum operand of square root per level difficulty */
        private const val MIN_OPERAND_NUM = 4
        /** maximum operand of square root per level difficulty */
        private const val MAX_OPERAND_NUM_PER_LD = 50
    }
}