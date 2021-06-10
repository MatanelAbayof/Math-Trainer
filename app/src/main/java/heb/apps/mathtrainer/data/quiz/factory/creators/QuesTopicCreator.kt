package heb.apps.mathtrainer.data.quiz.factory.creators

import android.content.Context
import android.util.Log
import heb.apps.mathtrainer.data.math.PrintInfo
import heb.apps.mathtrainer.data.quiz.answer.Answer
import heb.apps.mathtrainer.data.quiz.answer.TextAnswer
import heb.apps.mathtrainer.data.quiz.factory.QuizQuestionFactory
import heb.apps.mathtrainer.data.quiz.question.TextQuestion
import heb.apps.mathtrainer.data.quiz.quizquestion.QuizQuestion
import heb.apps.mathtrainer.memory.sp.settings.SettingsSp
import heb.apps.mathtrainer.ui.widget.quiz.answer.TextAnswerBt
import kotlin.random.Random

abstract class QuesTopicCreator(val ctx: Context, val categoryId: Int, val topicId: Int) {
	val printInfo: PrintInfo =  PrintInfo()
	
	init {
		register()

		// init print info from settings
		val settingsSp = SettingsSp(ctx)
		printInfo.printMode = settingsSp.mathDisplayType
	}
	
	// create quiz question
	abstract fun createQuizQuestion(levelDiff: Int) : QuizQuestion
		
	// register to factory
	private fun register() {
		val qqFac = QuizQuestionFactory.instance
		qqFac.register(topicId, ({ levelDifficulty: Int -> createQuizQuestion(levelDifficulty) }))
	}

	// create text quiz question of list
	protected fun createTextQQOfList(levelDiff: Int, numOfAnswers: Int, questionText: String,
					correctAnswerText: String,
					answerGen: (levelDiff: Int, ignoreAnswers: List<String>)
					 -> String) : QuizQuestion {

		require(numOfAnswers >= 0) {
			"Number of answers (=$numOfAnswers) must be bigger or equals to 0"
		}

		// add user answers
		val userAnswers = mutableListOf<Answer>()
		val allAnswersStr = mutableListOf( correctAnswerText )
		val correctAnswerIndex = Random.nextInt(numOfAnswers)
		for (i in 0 until numOfAnswers) {
			val answerStr =
				if(i == correctAnswerIndex) correctAnswerText
				else {
					val a = answerGen(levelDiff, allAnswersStr)
					allAnswersStr.add(a)
					a
				}

			val userAnswer =
                TextAnswerBt(ctx, answerStr)
			userAnswers.add(userAnswer)
		}

		// create quiz question
		val textQuestion = TextQuestion(questionText)
		val textAnswer = TextAnswer(correctAnswerText)
		return QuizQuestion(ctx, textQuestion, textAnswer, userAnswers)
	}

	// try random with limit
	protected fun <T> tryRandWithLimit(randFunc: () -> T, checkFunc: (T) -> Boolean) : T {
		var countTries = 0
		var result: T? = null
		while(countTries < MAX_TRIES_RESULT_RANGE) {
			result = randFunc()
			if(checkFunc(result)) {
				Log.v(LOG_TAG, "tries = $countTries") // TODO remove logs
				return result
			}
			countTries++
		}
		Log.v(LOG_TAG, "max tries (=$countTries)")
		return result!!
	}

	companion object {
		private const val LOG_TAG = "QuesTopicCreator"
		// maximum tries for check answer in range
		private const val MAX_TRIES_RESULT_RANGE = 1000000
	}
}