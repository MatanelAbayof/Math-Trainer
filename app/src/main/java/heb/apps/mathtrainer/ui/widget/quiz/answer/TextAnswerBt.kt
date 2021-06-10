package heb.apps.mathtrainer.ui.widget.quiz.answer

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.data.quiz.answer.Answer
import heb.apps.mathtrainer.data.quiz.answer.TextAnswer
import heb.apps.mathtrainer.utils.view.LayoutInflateHelper

class TextAnswerBt(context: Context, answerText: String = "") : // TODO need disable option (virtual)
    TextAnswer(answerText),
    IAnswerView {

    override var answerText: String = answerText
    set(value) {
        field = value

        // update view
        if(layoutHolder.tvAnswer.text != value)
            layoutHolder.tvAnswer.text = value
    }
    override var onUserAnswered: (answer: Answer) -> Unit = { }
    override val view: View = LayoutInflateHelper.inflate(context, R.layout.quiz_text_answer_bt)
    private val layoutHolder =
        LayoutHolder(view)
    private var isAnswerSelected = false

    init {
        initView()
    }

    // init view
    private fun initView() {
        fillViewInfo()
        handleViewEvents()
    }

    // handle view events
    private fun handleViewEvents() {
        layoutHolder.rlAnswer.setOnClickListener {
            isAnswerSelected = true
            onUserAnswered(this) // call set answer event
        }
    }

    // fill view info
    private fun fillViewInfo() {
        layoutHolder.tvAnswer.text = answerText
    }

    // show answer feedback
    override fun showAnswerFeedback(correctAnswer: Answer) {
        val isSameAnswer =  correctAnswer == TextAnswer(answerText)

        if(isAnswerSelected) {
            if(isSameAnswer)
                layoutHolder.rlAnswer.setBackgroundResource(R.drawable.bt_selected_corrected_answer)
            else
                layoutHolder.rlAnswer.setBackgroundResource(R.drawable.bt_selected_uncorrected_answer)
        }

        if(isSameAnswer)
            layoutHolder.ivResultFeedback.setImageResource(R.drawable.ic_tick_green)
        else
            layoutHolder.ivResultFeedback.setImageResource(R.drawable.ic_cancel_red)
        layoutHolder.ivResultFeedback.visibility = View.VISIBLE
        view.isEnabled = false // disable click events
    }

    // convert to string
    override fun toString() : String = "TextAnswerBt: { ${super.toString()} }"

    private class LayoutHolder(view: View) {
        val rlAnswer: RelativeLayout = view.findViewById(R.id.relativeLayout_answerBt)
        val ivResultFeedback: ImageView = view.findViewById(R.id.imageView_resultFeedback)
        val tvAnswer: TextView = view.findViewById(R.id.textView_answer)
    }
}