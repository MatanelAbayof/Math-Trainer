package heb.apps.mathtrainer.ui.widget.quiz.question

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.ui.activities.quiz.PlayQuizActivity
import heb.apps.mathtrainer.ui.widget.EquationTV
import heb.apps.mathtrainer.utils.view.LayoutInflateHelper
import heb.apps.mathtrainer.utils.view.addMatchPrntView

class QuizQuesResView(ctx: Context, attrs: AttributeSet? = null,
                      val quesResInfo: PlayQuizActivity.QuizQuestionResInfo,
                      val onLoadFinished: () -> Unit = {})
    : FrameLayout(ctx, attrs) {
    private lateinit var layoutHolder: LayoutHolder

    // counter of equations that load
    private var countLoadEquations = 0

    init {
        buildView()
        fillViewInfo()
        handleEvents()
    }

    // handle events
    private fun handleEvents() {
        handleLoadEvents()
    }

    // handle load events
    private fun handleLoadEvents() {
        layoutHolder.eqTvQuestion.onLoadFinished = {
            onEquationLoad()
        }
        layoutHolder.eqTvCorrectAnswer.onLoadFinished = {
            onEquationLoad()
        }
        layoutHolder.eqTvUserAnswer.onLoadFinished = {
            onEquationLoad()
        }
    }

    // event when equation load
    private fun onEquationLoad() {
        countLoadEquations++
        // check if all equations load
        if(countLoadEquations == NUM_OF_EQUATIONS)
            onLoadFinished()
    }

    // fill view info
    private fun fillViewInfo() {
        when(quesResInfo.isUserAnsweredOK()) {
            true -> {
                layoutHolder.ivCorrectAnswer.setImageResource(R.drawable.ic_tick_green)
                setBackgroundResource(R.drawable.bt_light_green)
            }
            false -> {
                layoutHolder.ivCorrectAnswer.setImageResource(R.drawable.ic_cancel_red)
                setBackgroundResource(R.drawable.bt_light_purplet)
            }
        }

        layoutHolder.eqTvQuestion.equation = quesResInfo.questionText
        layoutHolder.eqTvCorrectAnswer.equation = quesResInfo.correctAnswerText
        if(quesResInfo.userAnswerText != null)
            layoutHolder.eqTvUserAnswer.equation = quesResInfo.userAnswerText
        else
            layoutHolder.eqTvUserAnswer.equation = NO_ANSWER_TEXT
    }

    // build view
    private fun buildView() {
        val view = LayoutInflateHelper.inflate(context, // TODO better way is to set padding in child
            R.layout.quiz_question_result_info)
        layoutHolder =
            LayoutHolder(view)
        addMatchPrntView(view)
    }


    private class LayoutHolder(mainView: View) {
        val llAllInfo: LinearLayout = mainView.findViewById(R.id.linearLayout_allInfo)
        val ivCorrectAnswer: ImageView = mainView.findViewById(R.id.imageView_isCorrectedAnswer)
        val eqTvQuestion: EquationTV = mainView.findViewById(R.id.equationTvQuestion)
        val eqTvCorrectAnswer: EquationTV = mainView.findViewById(R.id.equationTvCorrectAnswer)
        val eqTvUserAnswer: EquationTV = mainView.findViewById(R.id.equationTvUserAnswer)
    }

    companion object {
        // number of equations in QuizQuesResView
        private const val NUM_OF_EQUATIONS = 3
        // no answer text
        private const val NO_ANSWER_TEXT = "-"
    }
}