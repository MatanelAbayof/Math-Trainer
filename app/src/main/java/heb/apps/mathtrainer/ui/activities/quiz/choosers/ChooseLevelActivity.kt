package heb.apps.mathtrainer.ui.activities.quiz.choosers

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.data.quiz.category.Category
import heb.apps.mathtrainer.data.quiz.factory.QuizQuestionGen
import heb.apps.mathtrainer.data.quiz.level.Level
import heb.apps.mathtrainer.data.quiz.level.UserLevelFullInf
import heb.apps.mathtrainer.data.quiz.question.Question
import heb.apps.mathtrainer.data.quiz.quizquestion.PlayQQPack
import heb.apps.mathtrainer.data.quiz.topic.Topic
import heb.apps.mathtrainer.exception.MissingArgException
import heb.apps.mathtrainer.memory.db.tasks.categories.GetCategoryByIdTask
import heb.apps.mathtrainer.memory.db.tasks.topics.GetTopicByIdTask
import heb.apps.mathtrainer.memory.db.tasks.userlevels.GetULsFullInfTask
import heb.apps.mathtrainer.ui.dialogs.ErrorDialog
import heb.apps.mathtrainer.ui.dialogs.LockedLevelDialog
import heb.apps.mathtrainer.ui.dialogs.help.HelpLevelDialog
import heb.apps.mathtrainer.ui.widget.StarsView
import heb.apps.mathtrainer.ui.widget.quiz.level.LevelBt
import heb.apps.mathtrainer.utils.ResolutionConverter
import heb.apps.mathtrainer.utils.collection.forEachFollow
import heb.apps.mathtrainer.utils.debug.TestsManager
import heb.apps.mathtrainer.utils.getJSON
import heb.apps.mathtrainer.utils.putJSON

class ChooseLevelActivity : BaseChooserActivity() {

    private lateinit var category: Category
    private lateinit var topic: Topic
    private lateinit var layoutHolder: LayoutHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityView(R.layout.activity_choose_level)
        layoutHolder = LayoutHolder()

        if(savedInstanceState == null) {
            updateNavAndLoadBts()
        }
    }

    /** update navigator and load levels buttons */
    private fun updateNavAndLoadBts() {
        showLoadingView()
        updateNavigator {
            loadLLevelsBts()
        }
    }

    /** update navigator view */
    private fun updateNavigator(onFinish: () -> Unit) {
        val getTopicByIdTask = GetTopicByIdTask(applicationContext) // TODO better way it's to use with one DBTask
        getTopicByIdTask.onFinishListener = { topicRes ->
            topic = topicRes!!
            val getCatByIdTask = GetCategoryByIdTask(applicationContext)
            getCatByIdTask.onFinishListener = { categoryRes ->
                category = categoryRes!!
                // build navigator info
                val navigatorText = "${category.displayName} > ${topic.displayName}"
                layoutHolder.tvNavigator.text = navigatorText
                onFinish()
            }
            getCatByIdTask.start(topic.categoryId)
        }
        getTopicByIdTask.start(getTopicId())
    }

    /** load levels buttons */
    private fun loadLLevelsBts() {
        showLoadingView()

        val getUlFullInfTask = GetULsFullInfTask(applicationContext)
        getUlFullInfTask.onFinishListener = { userLevelsFllInf ->
            if(userLevelsFllInf.isNullOrEmpty())
                showNoLevelsDialog()
            else
                setLevelsBts(userLevelsFllInf)

            hideLoadingView()
        }
        getUlFullInfTask.start(topic.id)
    }

    /** show no levels dialog */
    private fun showNoLevelsDialog() {
        val dialog = ErrorDialog(this@ChooseLevelActivity)
        dialog.setMessage(R.string.no_levels_error_msg)
        dialog.setOnDismissListener {
            finish()
        }
        dialog.show()
    }

    /** set levels buttons */
    private fun setLevelsBts(ulsFullInf: List<UserLevelFullInf>) {
        // remove last levels views
        layoutHolder.llListLevels.removeAllViews()

        ulsFullInf.forEachFollow { prevUl, currUl ->
            addLevelBt(prevUl, currUl)
        }
    }

    /** add level button */
    private fun addLevelBt(lastUlFullInf: UserLevelFullInf?, currUlFullInf: UserLevelFullInf) {
        val isLevelLocked = isLevelLocked(lastUlFullInf)
        val levelBt = LevelBt(
            baseContext,
            currUlFullInf,
            isLevelLocked
        )
        levelBt.onClickListener = { _ ->
            when {
                ((!levelBt.isLocked) ||
                 (TestsManager.IS_TEST_MODE && TestsManager.ENABLE_ENTER_LOCKED_LEVELS )) -> {
                    startPlayGameActivity(levelBt.ulFullInf.level)
                }
                else -> showLockedLevelDialog(lastUlFullInf!!, currUlFullInf)
            }
        }
        levelBt.onHelpClickListener = { _ ->
            showHelpLevelDialog(levelBt.ulFullInf.level)
        }
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.topMargin = ResolutionConverter.dipToPx(baseContext,2)
        layoutHolder.llListLevels.addView(levelBt, lp)
    }

    /** show locked level dialog */
    private fun showLockedLevelDialog(lastUlFullInf: UserLevelFullInf,
                                      currUlFullInf: UserLevelFullInf) {
        LockedLevelDialog(this@ChooseLevelActivity, lastUlFullInf, currUlFullInf).show()
    }

    /** check if level locked by previous level info */
    private fun isLevelLocked(lastUlFullInf: UserLevelFullInf?) : Boolean {
        if(lastUlFullInf == null) // check if it's first level in topic
            return false
        if(lastUlFullInf.userLevel == null) // check if user don't play with this level at all
            return true
        // check if user lose in this level
        return (lastUlFullInf.userLevel.numOfFilledStars < StarsView.MIN_STARS_FOR_UNLOCK_LEVEL)
    }

    /** start choose level activity */
    private fun startPlayGameActivity(level: Level) {
        val playQQPack = PlayQQPack()
        playQQPack.numOfQuestions = Question.DEF_NUM_QUESTIONS
        playQQPack.filter.filterType = QuizQuestionGen.Filter.FilterType.WHITE_LIST
        playQQPack.filter.levelsIds = setOf( level.id )

        intentsManager.startPlayGameActivity(playQQPack, ReqCodes.PLAY_QUIZ_REQ_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            ReqCodes.PLAY_QUIZ_REQ_CODE -> {
                // update levels
                loadLLevelsBts()
            }
        }
    }

    /** show help level dialog */
    private fun showHelpLevelDialog(level: Level) {
        val helpLevelDialog =
            HelpLevelDialog(this@ChooseLevelActivity, level)
        helpLevelDialog.show()
    }

    private inner class LayoutHolder {
        //val svListLevels: ScrollView = findViewById(R.id.scrollView_levels)
        val llListLevels: LinearLayout = findViewById(R.id.linearLayout_levels)
        val tvNavigator: TextView = findViewById(R.id.textView_navigator)
    }

    /** get topic id from extra */
    private fun getTopicId() : Int {
        val topicId = intent.getIntExtra(TOPIC_ID_EXTRA_IN, NOT_TOPIC_ID)
        if(topicId == NOT_TOPIC_ID)
            throw MissingArgException("Cannot find '$TOPIC_ID_EXTRA_IN' at extras " +
                    "in ${this::class.qualifiedName}")
        return topicId
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.run {
            category = Category.parse(getJSON(SaveIns.CATEGORY_KEY))
            topic = Topic.parse(getJSON(SaveIns.TOPIC_KEY))
            layoutHolder.tvNavigator.text = getString(SaveIns.NAV_TEXT_KEY)
        }
        loadLLevelsBts()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putJSON(SaveIns.CATEGORY_KEY, category.toJSON())
            putJSON(SaveIns.TOPIC_KEY, topic.toJSON())
            putString(SaveIns.NAV_TEXT_KEY, layoutHolder.tvNavigator.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    companion object {
        //private const val LOG_TAG = "ChooseLevelActivity"

        /** not topic id */
        private const val NOT_TOPIC_ID = -1

        /** extra of topic id */
        const val TOPIC_ID_EXTRA_IN = "topicId"

        /** request codes */
        private object ReqCodes {
            const val PLAY_QUIZ_REQ_CODE = 0
        }

        /** save instance state keys */
        private object SaveIns {
            /** category key */
            const val CATEGORY_KEY = "category"
            /** topic key */
            const val TOPIC_KEY = "topic"
            /** navigator text key */
            const val NAV_TEXT_KEY = "navText"
        }

    }
}
