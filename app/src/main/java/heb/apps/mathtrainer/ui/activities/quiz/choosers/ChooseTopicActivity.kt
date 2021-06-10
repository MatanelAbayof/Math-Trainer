package heb.apps.mathtrainer.ui.activities.quiz.choosers

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.data.quiz.topic.TopicWithExtras
import heb.apps.mathtrainer.memory.db.tasks.categories.GetCategoryByIdTask
import heb.apps.mathtrainer.memory.db.tasks.categories.GetTopicsWithExtrasTask
import heb.apps.mathtrainer.ui.dialogs.ErrorDialog
import heb.apps.mathtrainer.ui.dialogs.help.HelpTopicDialog
import heb.apps.mathtrainer.ui.widget.quiz.topic.TopicBt
import heb.apps.mathtrainer.utils.ResolutionConverter

class ChooseTopicActivity : BaseChooserActivity() {

    private var categoryId: Int = NOT_CATEGORY_ID
    private lateinit var layoutHolder: LayoutHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityView(R.layout.activity_choose_topic)
        layoutHolder = LayoutHolder()
        categoryId = getCategoryId()

        if((savedInstanceState == null) || (categoryId == NOT_CATEGORY_ID)) {
            updateNavAndLoadBts()
        }
    }

    /** update navigator and load topics buttons */
    private fun updateNavAndLoadBts() {
        showLoadingView()
        updateNavigator {
            loadTopicsBts()
        }
    }

    /** update navigator view */
    private fun updateNavigator(onFinish: () -> Unit) {
        val getCatByIdTask = GetCategoryByIdTask(applicationContext)
        getCatByIdTask.onFinishListener = { category ->
            layoutHolder.tvNavigator.text = category!!.displayName
            onFinish()
        }
        getCatByIdTask.start(categoryId)
    }

    /** load topics buttons */
    private fun loadTopicsBts() {
        showLoadingView()

        val getTopicsTask = GetTopicsWithExtrasTask(applicationContext)
        getTopicsTask.onFinishListener = { topics ->
            if(topics.isNullOrEmpty())
                showNoTopicsDialog()
            else
                setTopicsBts(topics)
            hideLoadingView()
        }
        getTopicsTask.start(categoryId)
    }

    /** show no topics dialog */
    private fun showNoTopicsDialog() {
        val dialog = ErrorDialog(this@ChooseTopicActivity)
        dialog.setMessage(R.string.no_topics_error_msg)
        dialog.setOnDismissListener {
            finish()
        }
        dialog.show()
    }

    /** set topics buttons */
    private fun setTopicsBts(topics: List<TopicWithExtras>) {
        // remove last topics views
        layoutHolder.llListTopics.removeAllViews()

        // add new topics views
        for(topic in topics)
            addTopicBt(topic)
    }

    /** add topic button */
    private fun addTopicBt(topicWithEx: TopicWithExtras) {
        val topicBt = TopicBt(baseContext, topicWithEx)
        topicBt.onClickListener = {
            startChooseLevelActivity(topicBt.topicWithEx)
        }
        topicBt.onHelpClickListener = {
            showHelpTopicDialog(it.topicWithEx)
        }
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.topMargin = ResolutionConverter.dipToPx(baseContext,4)
        lp.leftMargin = ResolutionConverter.dipToPx(baseContext,8)
        lp.rightMargin = ResolutionConverter.dipToPx(baseContext,8)
        lp.bottomMargin = ResolutionConverter.dipToPx(baseContext,4)
        layoutHolder.llListTopics.addView(topicBt, lp)
    }

    /** start choose level activity */
    private fun startChooseLevelActivity(topicWithEx: TopicWithExtras) {
        val topicId = topicWithEx.topic.id
        intentsManager.Choosers().startChooseLevelActivity(topicId, ReqCodes.CHOOSE_LEVEL_REQUEST_CODE)
    }

    /** show help topic dialog */
    private fun showHelpTopicDialog(topicWithEx: TopicWithExtras) {
        val helpTopicDialog =
            HelpTopicDialog(this@ChooseTopicActivity, topicWithEx.topic)
        helpTopicDialog.show()
    }

    /** get category id from extra */
    private fun getCategoryId() : Int {
        val categoryId = intent.getIntExtra(CATEGORY_ID_EXTRA_IN, NOT_CATEGORY_ID)
        require(categoryId != NOT_CATEGORY_ID) { "Cannot find '$CATEGORY_ID_EXTRA_IN' at extras " +
                "in ${this::class.qualifiedName}" }
        return categoryId
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            ReqCodes.CHOOSE_LEVEL_REQUEST_CODE -> {
                // update topics
                loadTopicsBts()
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.run {
            categoryId = getInt(SaveIns.CATEGORY_KEY, NOT_CATEGORY_ID)
            layoutHolder.tvNavigator.text = getString(SaveIns.NAV_TEXT_KEY)
        }
        loadTopicsBts()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putInt(SaveIns.CATEGORY_KEY, categoryId)
            putString(SaveIns.NAV_TEXT_KEY, layoutHolder.tvNavigator.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    private inner class LayoutHolder {
        //val svListTopics: ScrollView = findViewById(R.id.scrollView_topics)
        val llListTopics: LinearLayout = findViewById(R.id.linearLayout_topics)
        val tvNavigator: TextView = findViewById(R.id.textView_navigator)
    }

    companion object {

        /** not category id */
        private const val NOT_CATEGORY_ID = -1

        const val CATEGORY_ID_EXTRA_IN = "categoryId"

        /** request codes */
        private object ReqCodes {
            const val CHOOSE_LEVEL_REQUEST_CODE = 0
        }

        /** save instance state keys */
        private object SaveIns {
            /** category key */
            const val CATEGORY_KEY = "category"
            /** navigator text key */
            const val NAV_TEXT_KEY = "navText"
        }
    }
}
