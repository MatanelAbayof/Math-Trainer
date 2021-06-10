package heb.apps.mathtrainer.data.quiz.factory.creators.arithmetic

import android.content.Context
import heb.apps.mathtrainer.data.math.MathEvaluateAble
import heb.apps.mathtrainer.data.math.utils.IntegerRange
import heb.apps.mathtrainer.data.math.utils.RandomHelper
import heb.apps.mathtrainer.data.quiz.factory.creators.QuesTopicCreator
import heb.apps.mathtrainer.data.quiz.factory.creators.QuestionsInfo
import heb.apps.mathtrainer.data.quiz.quizquestion.QuizQuestion
import kotlin.math.abs

abstract class ArithmeticQtc(context: Context, topicId: Int)
    : QuesTopicCreator(context, QuestionsInfo.Categories.Arithmetic.ID, topicId) {

    /** allow natural numbers only in result */
    protected open val justNaturalsRes: Boolean = false
    /** minimum level difficulty for open question */
    protected open val minLevelDiffOpenQs: Int = DEF_MIN_LEVEL_DIFF_FOR_OQ
    /** distance from correct answer to random per level difficulty */
    protected open val maxDscCorrectAnswerPerLd: Int = DEF_DISTANCE_FROM_ANSWER_PER_LD
    /** absolute maximum distance from correct answer */
    protected open val maxAbsoluteDscCorrectAnswer: Int? = null
    /** relation of minimum result number per level difficulty */
    protected open val minResNumPerLd: Int? = null
    /** relation of maximum result number per level difficulty */
    protected open val maxResNumPerLd: Int = DEF_MAX_RES_NUM_PER_LD

    /** create quiz question */
    override fun createQuizQuestion(levelDiff: Int) : QuizQuestion {
        // random correct answer
        val correctAnswer = randCorrectAnswer(levelDiff)

        // create quiz question
        return when(levelDiff) {
            // TODO in minLevelDiffOpenQs..Int.MAX_VALUE -> createOpenQQ(levelDiff, correctAnswer)
            else -> createQQOfList(levelDiff, correctAnswer)
        }
    }

    /*// create open quiz question
    private fun createOpenQQ(levelDiff: Int, correctAnswer: RationalNum) : QuizQuestion {
        // TODO
    }*/

    // random correct answer */
    protected abstract fun randCorrectAnswer(levelDiff: Int) : MathEvaluateAble<Int?, Int>

    /** extra check for new answer */
    protected open fun checkRandAnswer(correctAnswer: MathEvaluateAble<Int?, Int>,
                                       answer: Int,
                                       ignoreAnswers: List<String>): Boolean = true

    /** rand answer from range */
    private fun randAnswer(correctAnswer: MathEvaluateAble<Int?, Int>,
                           range: IntegerRange,
                           ignoreAnswers: List<String>) : Int {
        var answer : Int
        do {
            answer = RandomHelper.next(range)
        } while(answer.toString() in ignoreAnswers || !checkRandAnswer(correctAnswer, answer, ignoreAnswers))
        return answer
    }

    /** check if number in result range */
    protected fun canBeResNum(levelDiff: Int, num: Int) : Boolean {
        if(abs(num) > getMaxResultNum(levelDiff))
            return false
        if(minResNumPerLd != null)
            if(num < getMinimumResNum(levelDiff))
                return false
        return true
    }
    private fun canBeResNum(pair: Pair<Int, Int>) : Boolean {
        val (levelDiff, num) = pair
        return canBeResNum(levelDiff, num)
    }

    /** get minimum result number */
    private fun getMinimumResNum(levelDiff: Int) : Int = minResNumPerLd!!*levelDiff

    /** calculate maximum result */
    private fun getMaxResultNum(levelDiff: Int) : Int = maxResNumPerLd*levelDiff

    /** get result number range */
    protected fun getResultNumRange(levelDiff: Int) : IntegerRange {
        val maxNum = maxResNumPerLd*levelDiff+1
        val minNum = -maxNum
        return getFixedRange(minNum, maxNum)
    }

    /** get fixed range */
    protected fun getFixedRange(minNum: Int, maxNum: Int) : IntegerRange {
        var fixedMinNum = minNum
        var fixedMaxNum = maxNum
        if(justNaturalsRes) {
            if(minNum < 0) fixedMinNum = 0
            if(maxNum < 0) fixedMaxNum = 0
        }
        return IntegerRange(fixedMinNum, fixedMaxNum)
    }

    /** create quiz question of list */
    private fun createQQOfList(levelDiff: Int, correctAnswer: MathEvaluateAble<Int?, Int>)
            : QuizQuestion {

        // build info
        val questionText = correctAnswer.asQuestion(printInfo)
        val correctAnswerRes = correctAnswer.eval()
        val correctAnswerText = correctAnswerRes.toString()
        val numOfAnswers = DEF_NUM_OF_ANSWERS
        val range = getRangeFromCorrectAnswer(levelDiff, correctAnswerRes)

        return createTextQQOfList(levelDiff, numOfAnswers, questionText, correctAnswerText,
            ({ _, previousAnswers -> randAnswer(correctAnswer, range, previousAnswers).toString() }))
    }

    /** get range of answers from correct answer */
    private fun getRangeFromCorrectAnswer(levelDiff: Int, correctAnswer: Int)
            : IntegerRange {
        var distance = maxDscCorrectAnswerPerLd*levelDiff

        maxAbsoluteDscCorrectAnswer?.let { if(distance > it) distance = it }

        val minNum = correctAnswer - distance
        val maxNum = correctAnswer + distance

        return getFixedRange(minNum, maxNum)
    }

    /** try random result number with limit */
    protected fun tryRandResNumWithLimit(levelDiff: Int, randFunc: () -> Int) : Int {
        val customRandFunc: () -> Pair<Int, Int> = { Pair(levelDiff, randFunc()) }
        val (_, resultNum) = tryRandWithLimit(customRandFunc, ::canBeResNum)
        return resultNum
    }

    companion object {
        // default number of answers
        const val DEF_NUM_OF_ANSWERS = 4
        // default relation of maximum result number per level difficulty
        private const val DEF_MAX_RES_NUM_PER_LD = 10
        // default minimum level difficulty for open question
        private const val DEF_MIN_LEVEL_DIFF_FOR_OQ = 11 // TODO need change to 8 when open question will support (i.e. MathKeyboard will be ready)
        // default distance from correct answer to random per level difficulty
        private const val DEF_DISTANCE_FROM_ANSWER_PER_LD = 5
    }
}