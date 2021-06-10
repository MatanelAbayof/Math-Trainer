package heb.apps.mathtrainer.ui.widget.quiz.answer

import android.content.Context
import android.util.TypedValue
import android.widget.LinearLayout
import heb.apps.mathtrainer.data.quiz.answer.Answer

class AnswersLayout(context: Context,
                    private val answers: List<Answer> = listOf())
    : LinearLayout(context) {

    init {
        orientation = VERTICAL

        addAnswers(answers)
    }

    // event
    var onUserAnswered : (answer: Answer) -> Unit = { }

    // add answers to layout
    private fun addAnswers(answers: List<Answer>) {
        // add views & listen events
        for(answer in answers) {
            addAnswer(answer)
            listenAnswerEvent(answer)
        }
    }

    // listen answer event
    private fun listenAnswerEvent(answer: Answer) {
        if (answer is IAnswerView)
            answer.onUserAnswered = {
                onUserAnswered(it)
            }
        else
            throw ClassCastException("Cannot listen answer event (bad casting)")
    }

    // show answer feedback at all answers
    fun showAnswerFeedback(correctAnswer: Answer) {
        answers.forEach { answer ->
            if(answer is IAnswerView)
                answer.showAnswerFeedback(correctAnswer)
        }
    }

    // add answer to layout
    private fun addAnswer(answer: Answer) {
        val childLP = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        childLP.topMargin = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            5f,
            context.resources.displayMetrics
        ).toInt()
        if (answer is IAnswerView)
            addView(answer.view, childLP)
        else
            throw ClassCastException("Cannot add answer to AnswersLayout (bad casting)")
    }
}