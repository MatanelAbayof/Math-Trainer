package heb.apps.mathtrainer.ui.widget.quiz.answer

import android.view.View
import heb.apps.mathtrainer.data.quiz.answer.Answer

// interface for answer view
interface IAnswerView {

    // event when user answered
    var onUserAnswered: (answer: Answer) -> Unit
    // answer view
    val view: View
    // show answer feedback
    fun showAnswerFeedback(correctAnswer: Answer)

}