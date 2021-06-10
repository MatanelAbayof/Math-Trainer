package heb.apps.mathtrainer.ui.activities.quiz.result

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.ui.activities.MathBaseActivity
import heb.apps.mathtrainer.ui.activities.quiz.QuizQuesResInfo
import heb.apps.mathtrainer.ui.widget.quiz.question.QuizQuesResView
import heb.apps.mathtrainer.utils.ResolutionConverter
import java.util.*

class ShowAnswersResActivity : MathBaseActivity() {

    private lateinit var layoutHolder: LayoutHolder
    private lateinit var questions: ArrayList<QuizQuesResInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityView(R.layout.activity_show_answers_res)
        layoutHolder = LayoutHolder()
        questions = getQuestionsFromExtras()

        showLoadingView()
        buildViews()
    }

    // build views
    private fun buildViews() {
        if(questions.isEmpty()) {
            layoutHolder.tvNoQuestions.visibility = View.VISIBLE
            hideLoadingView()
        } else {
            buildQuestionsViews {
                layoutHolder.svQuestionsSection.visibility = View.VISIBLE
                hideLoadingView()
            }
        }
    }

    // build questions views
    private fun buildQuestionsViews(onLoadFinished: () -> Unit) {
        layoutHolder.llQuestions.removeAllViews()
        var counterQuestionsLoad = 0 // TODO use LoadGroup instead

        questions.forEach { question ->
            addQuestionView(question) {
                counterQuestionsLoad++
                if(counterQuestionsLoad == questions.size)
                    onLoadFinished()
           }
        }
    }

    // add question view to list
    private fun addQuestionView(question: QuizQuesResInfo, onLoadFinished: () -> Unit) {
        val quesResView = QuizQuesResView(
            this,
            quesResInfo = question,
            onLoadFinished = onLoadFinished)

        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.topMargin = ResolutionConverter.dipToPx(this, 4)
        layoutHolder.llQuestions.addView(quesResView, lp)
    }

    // get questions from extras
    private fun getQuestionsFromExtras() : ArrayList<QuizQuesResInfo> {
        try {
            return intent.getParcelableArrayListExtra(QUESTIONS_EXTRA_IN)!!
        } catch (e: Throwable) {
            throw IllegalArgumentException("Cannot parse 'Questions' argument " +
                    "from extras in '${this::class.qualifiedName}'")
        }
    }

    private inner class LayoutHolder {
        val svQuestionsSection: ScrollView = findViewById(R.id.scrollView_questionsSection)
        val llQuestions: LinearLayout = findViewById(R.id.linearLayout_questions)
        val tvNoQuestions: TextView = findViewById(R.id.textView_notFoundQuestions)
    }

    companion object {
        // extras
        const val QUESTIONS_EXTRA_IN = "questions"

        private const val LOG_TAG = "ShowAnswersResActivity"
    }
}
