package heb.apps.mathtrainer.ui.widget.quiz.answer

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.data.quiz.answer.Answer
import heb.apps.mathtrainer.data.quiz.answer.TextAnswer
import heb.apps.mathtrainer.ui.widget.BaseButton
import heb.apps.mathtrainer.utils.view.LayoutInflateHelper

class OpenTextAnswer(context: Context, answerText: String = "",
                     private val inputType: Int = InputType.TYPE_CLASS_TEXT) :
    TextAnswer(answerText),
    IAnswerView {

    override var answerText: String = answerText
    set(value) {
        field = value
        // update view
        if(layoutHolder.etAnswer.text.toString() != value)
            layoutHolder.etAnswer.setText(value)
    }

    override var onUserAnswered: (answer: Answer) -> Unit = { }
    override val view: View = LayoutInflateHelper.inflate(context, R.layout.quiz_open_answer_text)
    private val layoutHolder =
        LayoutHolder(view)

    init {
        initView()
    }

    // init view
    private fun initView() {
        fillViewInfo()
        handleViewEvents()
    }

    // fill view info
    private fun fillViewInfo() {
        layoutHolder.etAnswer.setText(answerText)
        layoutHolder.etAnswer.inputType = inputType
    }

    override fun showAnswerFeedback(correctAnswer: Answer) {
        // TODO
    }

    // handle view events
    private fun handleViewEvents() {
        layoutHolder.etAnswer.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                answerText = s.toString()
                updateSubmitBt()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        // handle on user answered events
        layoutHolder.etAnswer.setOnEditorActionListener { _,actionId,_ ->
            when(actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    layoutHolder.btSubmitText.performClick()
                    true
                }
                else -> false
            }
        }
        layoutHolder.btSubmitText.setOnClickListener {
            onUserAnswered(this) // call set answer event
        }

        updateSubmitBt()
    }

    // check if input is OK
    private fun checkInput() = layoutHolder.etAnswer.text.toString().trim().isNotEmpty()

    // update submit button
    private fun updateSubmitBt() {
        layoutHolder.btSubmitText.isEnabled = checkInput()
    }

    // convert to string
    override fun toString() : String = "OpenTextAnswer: { ${super.toString()} }"

    private class LayoutHolder(view: View) {
        val etAnswer: EditText = view.findViewById(R.id.editText_answer)
        val btSubmitText: BaseButton = view.findViewById(R.id.button_submitAnswer)
    }
}