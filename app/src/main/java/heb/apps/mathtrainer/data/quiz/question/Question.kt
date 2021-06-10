package heb.apps.mathtrainer.data.quiz.question

import android.content.Context
import android.view.View
import heb.apps.mathtrainer.utils.load.LoadObj

abstract class Question : LoadObj<Context> { // Note: all questions is views

	override var onLoadFinish: () -> Unit = {}
	protected open var view: View? = null

	// compare questions
	abstract override fun equals(other: Any?) : Boolean

	// create view of question, for question layout
	fun getView(ctx: Context) : View {
		if(view == null)
			startLoading(ctx)
		return view!!
	}

	// convert to equation
	abstract fun toEquation() : String
	
	// convert to string
	override fun toString() : String {
		return "Question: {}"
	}

	override fun hashCode(): Int {
		return javaClass.hashCode()
	}

	companion object {
		// default number of questions in quiz
		const val DEF_NUM_QUESTIONS = 10
	}
}