package heb.apps.mathtrainer.data.quiz.factory.creators.arithmetic

import android.content.Context
import heb.apps.mathtrainer.data.math.type.RationalNum
import heb.apps.mathtrainer.data.math.utils.IntegerRange
import heb.apps.mathtrainer.data.math.utils.RandomHelper
import heb.apps.mathtrainer.data.math.utils.at
import heb.apps.mathtrainer.data.quiz.factory.creators.QuestionsInfo

class DivQtc(ctx: Context)
    : ArithmeticQtc(ctx, QuestionsInfo.Categories.Arithmetic.Topics.DIVISION_ID) {

    override val justNaturalsRes: Boolean = true
    override val maxResNumPerLd: Int = 8
    override val maxDscCorrectAnswerPerLd: Int = 4
    override val maxAbsoluteDscCorrectAnswer: Int? = 15

    private val maxNumeratorPerLd: Int = DEF_MAX_NUMERATOR_PER_LD
    private val maxDenominatorPerLd: Int = DEF_MAX_DENOMINATOR_PER_LD

    // random rational number
    override fun randCorrectAnswer(levelDiff: Int) : RationalNum {
        val resultNumRange = getResultNumRange(levelDiff)
        val denominatorRange = getDenominatorRange(levelDiff)
        val randFunc: () -> RationalNum = {
            val resultNum = RandomHelper.next(resultNumRange)
            val denominatorNum = RandomHelper.next(denominatorRange)
            val numeratorNum = resultNum*denominatorNum
            RationalNum(numeratorNum, denominatorNum)
        }
        val checkFunc: (RationalNum) -> Boolean = { num ->
            canBeResNum(levelDiff, num.eval())&&
            isNumeratorOK(levelDiff, num.numerator)&&
            (num.numerator != num.denominator)
        }

        return tryRandWithLimit(randFunc, checkFunc)
    }

    // check if numerator is in range
    private fun isNumeratorOK(levelDiff: Int, numerator: Int) : Boolean
            = numerator at getNumeratorRange(levelDiff)

    // get denominator range
    private fun getNumeratorRange(levelDiff: Int) : IntegerRange {
        val minNum = MIN_NUMERATOR
        val maxNum = maxNumeratorPerLd*levelDiff+1
        return getFixedRange(minNum, maxNum)
    }

    // get denominator range
    private fun getDenominatorRange(levelDiff: Int) : IntegerRange {
        val minNum = MIN_DENOMINATOR
        val maxNum = maxDenominatorPerLd*levelDiff+1
        return getFixedRange(minNum, maxNum)
    }

    companion object {
        // default maximum numerator per level difficulty
        private const val DEF_MAX_NUMERATOR_PER_LD = 28
        // default maximum denominator per level difficulty
        private const val DEF_MAX_DENOMINATOR_PER_LD = 14
        // default minimum numerator per level difficulty
        private const val MIN_NUMERATOR = 2
        // default minimum denominator per level difficulty
        private const val MIN_DENOMINATOR = 2
    }
}