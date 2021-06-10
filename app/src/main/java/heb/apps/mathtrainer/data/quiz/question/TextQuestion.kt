package heb.apps.mathtrainer.data.quiz.question

import android.content.Context
import android.view.View
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.ui.widget.EquationTV
import heb.apps.mathtrainer.utils.view.LayoutInflateHelper

class TextQuestion(var questionText: String = "") : Question() {

	private lateinit var layoutHolder: LayoutHolder

	override fun startLoading(ctx: Context?) {
		buildView(ctx!!)

		layoutHolder.etvQuestion.onLoadFinished = {
			onLoadFinish()
		}

		fillViewInfo()
	}

	override fun equals(other: Any?): Boolean {
		if(other !is TextQuestion)
			return false
		return (questionText == other.questionText)
	}

	// convert to equation
	override fun toEquation() : String = questionText

	// build view
	private fun buildView(ctx: Context) {
		view = LayoutInflateHelper.inflate(ctx, R.layout.quiz_text_question)
		layoutHolder = LayoutHolder(view!!)
	}

	// fill view info
	private fun fillViewInfo() {
		layoutHolder.etvQuestion.equation = questionText
	}
	
	// convert to string
	override fun toString() : String {
		return "TextQuestion: { QuestionText=$questionText, ${super.toString()} }"
	}

	override fun hashCode(): Int {
		var result = super.hashCode()
		result = 31 * result + questionText.hashCode()
		return result
	}

	private class LayoutHolder(mainView: View) {
		val etvQuestion: EquationTV = mainView.findViewById(R.id.equationTv_ques)
	}
}