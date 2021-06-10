package heb.apps.mathtrainer.data.quiz.quizquestion

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.data.quiz.answer.Answer
import heb.apps.mathtrainer.data.quiz.level.Level
import heb.apps.mathtrainer.data.quiz.question.Question
import heb.apps.mathtrainer.ui.widget.quiz.answer.AnswersLayout
import heb.apps.mathtrainer.utils.load.LoadObj
import heb.apps.mathtrainer.utils.view.LayoutInflateHelper
import heb.apps.mathtrainer.utils.view.addMatchPrntView

open class QuizQuestion(val ctx: Context,
						val question: Question,
						val correctAnswer: Answer,
						userAnswers: List<Answer>,
						var level: Level? = null) : LoadObj<Void> {

	override var onLoadFinish: () -> Unit = {}

	val answersLayout =
        AnswersLayout(ctx, userAnswers)

	private var view: View? = null

	// start loading view
	override fun startLoading(t: Void?) {
		// TODO when answer will be EquationTV, listen loading from answers also

		question.onLoadFinish = {
			onLoadFinish()
		}

		// start build view
		buildView()
	}

	// build view
	private fun buildView() {
		val mainLayout = LayoutInflateHelper.inflate(ctx, R.layout.quiz_question_layout)
		val lh = LayoutHolder(mainLayout)

		mainLayout.layoutParams = FrameLayout.LayoutParams(
			FrameLayout.LayoutParams.MATCH_PARENT,
			FrameLayout.LayoutParams.MATCH_PARENT
		)

		// add question view
		lh.rlQuestion.addMatchPrntView(question.getView(ctx))

		// add answers layout
		lh.rlAnswers.addMatchPrntView(answersLayout)

		view = mainLayout
	}

	// create view
	fun getView() : View {
		if(view == null)
			startLoading()
		return view!!
	}
	
	// convert to string
	override fun toString() : String {
		return "QuizQuestion: { Question=$question, CorrectAnswer=$correctAnswer, answersLayout=$answersLayout }"
	}

	private class LayoutHolder(mainLayout: View) {
		val rlQuestion: RelativeLayout = mainLayout.findViewById(R.id.relativeLayout_question) // Note: must be RelativeLayout instead FlameLayout to gravity children center
		val rlAnswers: RelativeLayout = mainLayout.findViewById(R.id.relativeLayout_answers)
	}
}