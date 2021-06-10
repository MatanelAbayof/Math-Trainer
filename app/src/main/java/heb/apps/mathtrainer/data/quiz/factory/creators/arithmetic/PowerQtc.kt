package heb.apps.mathtrainer.data.quiz.factory.creators.arithmetic

import android.content.Context
import heb.apps.mathtrainer.data.math.function.binary.ExponentOperator
import heb.apps.mathtrainer.data.math.type.IntNum
import heb.apps.mathtrainer.data.math.utils.IntegerRange
import heb.apps.mathtrainer.data.math.utils.RandomHelper
import heb.apps.mathtrainer.data.quiz.factory.creators.QuestionsInfo

class PowerQtc(ctx: Context)
    : ArithmeticQtc(ctx, QuestionsInfo.Categories.Arithmetic.Topics.POWER_ID) {

    override val justNaturalsRes: Boolean = true
    override val minResNumPerLd: Int = 4
    override val maxResNumPerLd: Int = 80
    override val maxDscCorrectAnswerPerLd: Int = 50
    override val maxAbsoluteDscCorrectAnswer: Int? = null

    // maximum base of exponentiation per level difficulty
    private val maxBaseNumPerLd: Int = DEF_MAX_BASE_NUM_PER_LD

    // random number
    override fun randCorrectAnswer(levelDiff: Int) : ExponentOperator {
        val baseNumRange = getBaseNumRange(levelDiff)

        var correctAnswer: ExponentOperator? = null
        tryRandResNumWithLimit(levelDiff, ({
            val baseNum = IntNum(RandomHelper.next(baseNumRange))

            val exponentNumRange = getExponentNumRange(baseNum.eval(), levelDiff)
            val exponentNum = IntNum(RandomHelper.next(exponentNumRange))

            // build question
            correctAnswer = ExponentOperator(baseNum, exponentNum)
            val resultNum = correctAnswer!!.eval()
            resultNum
        }))
        return correctAnswer!!
    }

    // get base of exponentiation range
    private fun getBaseNumRange(levelDiff: Int) : IntegerRange {
        val minNum = MIN_BASE_NUM
        var maxNum = maxBaseNumPerLd*levelDiff+1
        if(maxNum > MAX_BASE_NUM)
            maxNum = MAX_BASE_NUM + 1
        return getFixedRange(minNum, maxNum)
    }

    // get exponent of exponentiation range
    private fun getExponentNumRange(baseNum: Int, levelDiff: Int) : IntegerRange {
        val minNum = MIN_EXPONENT_NUM
        val maxNum = getMaxExponent(baseNum)+1
        return getFixedRange(minNum, maxNum)
    }

    // get maximum exponent relative to base
    private fun getMaxExponent(baseNum: Int) : Int {
        var exponent: Int = (MAX_EXPONENT_NUM*(1f - baseNum.toFloat()/MAX_BASE_NUM)).toInt()
        if(exponent < MIN_EXPONENT_NUM)
            exponent = MIN_EXPONENT_NUM
        else if(exponent > MAX_EXPONENT_NUM)
            exponent = MAX_EXPONENT_NUM
        return exponent
    }

    companion object {
        // default maximum base of exponentiation per level difficulty
        private const val DEF_MAX_BASE_NUM_PER_LD = 4
        // minimum base of exponentiation number
        private const val MIN_BASE_NUM = 2
        // maximum base of exponentiation number
        private const val MAX_BASE_NUM = 40
        // minimum exponent of exponentiation number
        private const val MIN_EXPONENT_NUM = 2
        // maximum exponent of exponentiation number
        private const val MAX_EXPONENT_NUM = 12
    }
}