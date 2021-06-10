package heb.apps.mathtrainer.ui.activities.intents

import heb.apps.mathtrainer.data.quiz.quizquestion.PlayQQPack
import heb.apps.mathtrainer.ui.activities.*
import heb.apps.mathtrainer.ui.activities.quiz.PlayQuizActivity
import heb.apps.mathtrainer.ui.activities.quiz.QuizQuesResInfo
import heb.apps.mathtrainer.ui.activities.quiz.choosers.ChooseCategoryActivity
import heb.apps.mathtrainer.ui.activities.quiz.choosers.ChooseLevelActivity
import heb.apps.mathtrainer.ui.activities.quiz.choosers.ChooseTopicActivity
import heb.apps.mathtrainer.ui.activities.quiz.result.QuizResultActivity
import heb.apps.mathtrainer.ui.activities.quiz.result.ShowAnswersResActivity
import heb.apps.mathtrainer.ui.activities.settings.DisplaySettingsActivity
import heb.apps.mathtrainer.ui.activities.settings.MainSettingsActivity
import heb.apps.mathtrainer.ui.activities.settings.account.AccountSettActivity
import heb.apps.mathtrainer.utils.debug.Test

class MathIntentsManager(activity: MathBaseActivity) : BaseIntentsManager(activity) {

    override val launchActivity: Class<*> = MainActivity::class.java

    @Test inner class Tests {

        // start tests activity
        fun startTestsActivity() {
            startActivity(TestsActivity::class.java)
        }

        // start DB builder activity
        fun startDbBuilderActivity() {
            startActivity(DbBuilderActivity::class.java)
        }

    }

    inner class Choosers {

        // start choose category activity
        fun startChooseCategoryActivity(requestCode: Int? = null) {
            startActivity(ChooseCategoryActivity::class.java, requestCode)
        }

        // start choose topic activity
        fun startChooseTopicActivity(categoryId: Int, requestCode: Int? = null) {
            val intent = createIntent(ChooseTopicActivity::class.java)
            intent.putExtra(ChooseTopicActivity.CATEGORY_ID_EXTRA_IN, categoryId)
            startActivity(intent, requestCode)
        }

        // start choose level activity
        fun startChooseLevelActivity(topicId: Int, requestCode: Int? = null) {
            val intent = createIntent(ChooseLevelActivity::class.java)
            intent.putExtra(ChooseLevelActivity.TOPIC_ID_EXTRA_IN, topicId)
            startActivity(intent, requestCode)
        }

    }

    inner class QuizResult {

        // start show answers activity
        fun startShowAnswersActivity(answers: ArrayList<QuizQuesResInfo>) {
            val showAnswersIntent = createIntent(ShowAnswersResActivity::class.java)
            showAnswersIntent.putExtra(ShowAnswersResActivity.QUESTIONS_EXTRA_IN, answers)
            startActivity(showAnswersIntent)
        }

        // start quiz result activity
        fun startQuizResultActivity(quizRes: QuizResultActivity.QuizRes, requestCode: Int? = null) {
            val intent = createIntent(QuizResultActivity::class.java)
            intent.putExtra(QuizResultActivity.QUIZ_RES_EXTRA_IN, quizRes)
            startActivity(intent, requestCode)
        }

    }

    inner class Settings {

        // start main settings activity
        fun startMainSettingsActivity(requestCode: Int) {
            startActivity(MainSettingsActivity::class.java, requestCode)
        }

        // start account settings activity
        fun startAccountSettActivity(requestCode: Int? = null) {
            startActivity(AccountSettActivity::class.java, requestCode)
        }

        // start display settings activity
        fun startDisplaySettingsActivity(requestCode: Int? = null) {
            startActivity(DisplaySettingsActivity::class.java, requestCode)
        }
    }

    // start play game activity
    fun startPlayGameActivity(playQQPack: PlayQQPack, requestCode: Int? = null) {
        val playQuizIntent = createIntent(PlayQuizActivity::class.java)
        playQuizIntent.putExtra(PlayQuizActivity.PLAY_QQ_PACK_EXTRA_IN, playQQPack)
        startActivity(playQuizIntent, requestCode)
    }

    // start sign up activity
    fun startSignUpActivity(requestCode: Int) {
        startActivity(SignUpActivity::class.java, requestCode)
    }

}