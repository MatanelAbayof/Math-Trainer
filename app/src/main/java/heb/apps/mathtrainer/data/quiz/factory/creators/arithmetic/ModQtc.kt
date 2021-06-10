package heb.apps.mathtrainer.data.quiz.factory.creators.arithmetic

import android.content.Context
import heb.apps.mathtrainer.data.math.MathEvaluateAble
import heb.apps.mathtrainer.data.math.function.binary.ModOperator
import heb.apps.mathtrainer.data.math.type.IntNum
import heb.apps.mathtrainer.data.math.utils.IntegerRange
import heb.apps.mathtrainer.data.math.utils.RandomHelper
import heb.apps.mathtrainer.data.quiz.factory.creators.QuestionsInfo

class ModQtc(ctx: Context)
    : ArithmeticQtc(ctx, QuestionsInfo.Categories.Arithmetic.Topics.MOD_ID) {

    override val justNaturalsRes: Boolean = true
    override val maxResNumPerLd: Int = 5
    override val maxDscCorrectAnswerPerLd: Int = 10
    override val maxAbsoluteDscCorrectAnswer: Int = 30

    /** random a number */
    override fun randCorrectAnswer(levelDiff: Int) : ModOperator {
        val operandARange = getOperandARange(levelDiff)
        val operandNRange = getOperandNRange(levelDiff)

        var correctAnswer: ModOperator? = null
        tryRandResNumWithLimit(levelDiff, ({
            val a = RandomHelper.next(operandARange)
            val n = RandomHelper.next(operandNRange)

            // build question
            correctAnswer = ModOperator(IntNum(a), IntNum(n))
            val resNum = correctAnswer!!.eval()
            resNum
        }))
        return correctAnswer!!
    }

    /** get operand 'a' range of equation "a mod n" */
    private fun getOperandARange(levelDiff: Int) : IntegerRange {
        val minNum = MIN_OPERAND_A
        val maxNum = MAX_OPERAND_A_PER_LD*levelDiff + 1
        return getFixedRange(minNum, maxNum)
    }

    /** get operand 'n' range of equation "a mod n" */
    private fun getOperandNRange(levelDiff: Int) : IntegerRange {
        val minNum = MIN_OPERAND_N
        val maxNum = MAX_OPERAND_N_PER_LD*levelDiff + 1
        return getFixedRange(minNum, maxNum)
    }

    /** extra check for new answer */
    override fun checkRandAnswer(correctAnswer: MathEvaluateAble<Int?, Int>,
                                 answer: Int,
                                 ignoreAnswers: List<String>): Boolean {
        val questionEx = correctAnswer as ModOperator
        val modN = (questionEx.rightExpression as IntNum).value
        val answerVal = correctAnswer.eval()
        return (answer % modN) != answerVal
    }

    companion object {
        /** minimum operand 'a' of  equation "a mod n" */
        private const val MIN_OPERAND_A = 4
        /** maximum operand 'a' of  equation "a mod n" */
        private const val MAX_OPERAND_A_PER_LD = 30
        /** minimum operand 'n' of  equation "a mod n" */
        private const val MIN_OPERAND_N = 2
        /** maximum operand 'n' of  equation "a mod n" */
        private const val MAX_OPERAND_N_PER_LD = 5
    }
}