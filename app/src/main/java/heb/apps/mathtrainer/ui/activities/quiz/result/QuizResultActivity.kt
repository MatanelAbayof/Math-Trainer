package heb.apps.mathtrainer.ui.activities.quiz.result

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.data.quiz.factory.QuizQuestionGen
import heb.apps.mathtrainer.data.quiz.level.Level
import heb.apps.mathtrainer.data.quiz.level.UserLevel
import heb.apps.mathtrainer.data.quiz.quizquestion.PlayQQPack
import heb.apps.mathtrainer.data.quiz.resultmsgs.ResMsgsManager
import heb.apps.mathtrainer.memory.db.tasks.levels.GetLevelByIdTask
import heb.apps.mathtrainer.memory.db.tasks.levels.GetNextLevelTask
import heb.apps.mathtrainer.memory.db.tasks.userlevels.GetULByLevelIdTask
import heb.apps.mathtrainer.memory.db.tasks.userlevels.InsUpUserLevelTask
import heb.apps.mathtrainer.ui.activities.MathBaseActivity
import heb.apps.mathtrainer.ui.activities.quiz.QuizQuesResInfo
import heb.apps.mathtrainer.ui.widget.StarsView
import heb.apps.mathtrainer.utils.time.TimeFormatter
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.math.max
import kotlin.math.min

class QuizResultActivity : MathBaseActivity() {             // TODO need a class that holds all info and have functions for this

    private lateinit var layoutHolder: LayoutHolder
    private lateinit var quizRes: QuizRes
    private var lastUserLevel: UserLevel? = null
    private var newUserLevel: UserLevel? = null
    private var bestUserLevel: UserLevel? = null
    private var storyLevel: Level? = null
    private var nextLevel: Level? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityView(R.layout.activity_quiz_result)
        layoutHolder = LayoutHolder()

        // read info from extras
        quizRes = getQuizRes()

        if(savedInstanceState == null) {
            handleGameResult()
        }
    }

    /** handle game result */
    private fun handleGameResult() {
        showLoadingView()

        initStoryLevel {
            fetchLastUserLevel {
                newUserLevel = calcNewUserLevel()
                bestUserLevel = calcBestUserLevel()
                fetchNextLevel {
                    updateUserLevel {
                        buildResultViews()
                        hideLoadingView()
                    }
                }
            }
        }
    }

    /** update user level in DB, if need */
    private fun updateUserLevel(onFinish: () -> Unit) {
        if(bestUserLevel == null) {
            onFinish()
            return
        }

        val insUpULTask = InsUpUserLevelTask(applicationContext)
        insUpULTask.onFinishListener = {
            onFinish()
        }
        insUpULTask.start(bestUserLevel)
    }

    /** calculate best user level, if can */
    private fun calcBestUserLevel() : UserLevel? {
        if(storyLevel == null)
            return null
        val bestUserLevel = UserLevel()
        bestUserLevel.id = newUserLevel!!.id
        bestUserLevel.levelId = newUserLevel!!.levelId
        bestUserLevel.numOfFilledStars = when(lastUserLevel) {
            null -> newUserLevel!!.numOfFilledStars
            else -> max(lastUserLevel!!.numOfFilledStars, newUserLevel!!.numOfFilledStars)
        }
        bestUserLevel.minFinishTime = when {
            (lastUserLevel == null) || haveNewStars() -> newUserLevel!!.minFinishTime
            else -> min(lastUserLevel!!.minFinishTime!!, newUserLevel!!.minFinishTime!!)
        }
        return bestUserLevel
    }

    /** calculate new user level, if can. otherwise return null */
    private fun calcNewUserLevel() : UserLevel? {
        if(storyLevel == null)
            return null

        val userLevel = UserLevel()
        if(lastUserLevel != null)
            userLevel.id = lastUserLevel!!.id
        userLevel.levelId = storyLevel!!.id
        userLevel.minFinishTime = quizRes.totalTime
        userLevel.numOfFilledStars = quizRes.usersStars
        return userLevel
    }

    /** fetch next level from DB */
    private fun fetchNextLevel(onFinish: () -> Unit) {
        when(storyLevel) {
            null -> onFinish() // no need to fetch
            else -> {
                val getNextLevelTask = GetNextLevelTask(applicationContext)
                getNextLevelTask.onFinishListener = {
                    nextLevel = it
                    onFinish()
                }
                getNextLevelTask.start(storyLevel)
            }
        }
    }

    /** fetch last user level from DB */
    private fun fetchLastUserLevel(onFinish: () -> Unit) {
        when(storyLevel) {
            null -> onFinish() // no need to fetch
            else -> {
                val getULTask = GetULByLevelIdTask(applicationContext)
                getULTask.onFinishListener = {
                    lastUserLevel = it
                    onFinish()
                }
                getULTask.start(storyLevel!!.id)
            }
        }
    }

    /** init story level, if can */
    private fun initStoryLevel(onFinish: () -> Unit) {
        if(!quizRes.isStoryRes()) {
            onFinish()
            return
        }

        val storyLevelId = quizRes.storyLevelId
        val getLevelTask = GetLevelByIdTask(applicationContext)
        getLevelTask.onFinishListener = { level ->
            storyLevel = level
            onFinish()
        }
        getLevelTask.start(storyLevelId)
    }

    /** build result views */
    private fun buildResultViews(createNewFeedback: Boolean = true) {
        if(createNewFeedback)
            updateResMsg()

        // update num of starts
        layoutHolder.svUserStars.numOfStars = StarsView.MAX_NUM_OF_STARS
        layoutHolder.svUserStars.numOfFilledStars = quizRes.usersStars

        layoutHolder.btMoreInfo.setOnClickListener {
            startShowAnswersResActivity()
        }

        buildQuizInfSecView()

        buildActsBts()
        initActsBtsEvents()
    }

    /** start show answers result activity */
    private fun startShowAnswersResActivity() {
        intentsManager.QuizResult().startShowAnswersActivity(quizRes.questionsResultInfo)
    }

    /** update result message (rand message from list by num of filled stars) */
    private fun updateResMsg() {
        val resMsgMng = ResMsgsManager(baseContext)
        val resMsg = resMsgMng.randMsg(quizRes.usersStars)
        layoutHolder.tvPrimaryMsg.text = resMsg
    }

    /** build quiz info section view */
    private fun buildQuizInfSecView() {
        buildEarnXpPart()
        buildTimePart()
    }

    /** build earn XP part */
    private fun buildEarnXpPart() {
        val infoSec = layoutHolder.InfoSection()
        if(storyLevel != null) {
            val earnXpVal = calcNewXp()

            if(earnXpVal > 0) {
                infoSec.tvEarnXpValue.text = String.format("+%d", earnXpVal)
                infoSec.llEarnXpSection.visibility = View.VISIBLE
            }
        }
    }

    /** check if have a new stars */
    private fun haveNewStars() : Boolean = calcNewFilledStars() > 0

    /** calculate new filled stars */
    private fun calcNewFilledStars() : Int {
        return when {
            (storyLevel == null) -> 0
            (lastUserLevel == null) -> newUserLevel!!.numOfFilledStars
            (newUserLevel!!.numOfFilledStars > lastUserLevel!!.numOfFilledStars) -> {
                 newUserLevel!!.numOfFilledStars - lastUserLevel!!.numOfFilledStars
            }
            else -> 0
        }
    }

    /** calculate new xp */
    private fun calcNewXp() : Int {
        return when {
            (storyLevel == null) -> 0
            else -> calcNewFilledStars()*storyLevel!!.xpPerStar
        }
    }

    /** build time part */
    private fun buildTimePart() {
        val infoSec = layoutHolder.InfoSection()
        infoSec.tvTime.text = TimeFormatter.format(this, quizRes.totalTime)

        if(lastUserLevel != null) {
            if(calcNewFilledStars() == 0) {
                val diffTime = calcDiffTime()

                val diffTimeFormat = TimeFormatter.format(this, diffTime)
                val diffTimeText = if(diffTime > 0) "+$diffTimeFormat" else diffTimeFormat
                infoSec.tvDeltaFromRecord.text = diffTimeText
                infoSec.tvDeltaFromRecord.visibility = View.VISIBLE

                // check if is new record
                if(isNewTimeRecord()) {
                    infoSec.tvNewRecord.visibility = View.VISIBLE
                    infoSec.tvDeltaFromRecord.setTextColor(Color.rgb(0,0x80,0))
                } else {
                    infoSec.tvDeltaFromRecord.setTextColor(Color.BLACK)
                }
            }
        }
    }

    /** calculate diff time */
    private fun calcDiffTime() : Long = quizRes.totalTime -  lastUserLevel!!.minFinishTime!!

    /** check if is new time record */
    private fun isNewTimeRecord() : Boolean {
        if(lastUserLevel == null)
            return false
        // check if earn new stars
        if(newUserLevel!!.numOfFilledStars >= lastUserLevel!!.numOfFilledStars) {
            val diffTime = calcDiffTime()
            if(diffTime < 0)
                return true
        }
        return false
    }

    /** build actions buttons */
    private fun buildActsBts() {
        val llNextLevel = layoutHolder.ActionsBts().llNextLevel

        // check if have next level
        if(nextLevel != null) {
            llNextLevel.visibility = View.VISIBLE

            // check if next level is locked
            llNextLevel.isEnabled =
                (bestUserLevel!!.numOfFilledStars >= StarsView.MIN_STARS_FOR_UNLOCK_LEVEL)
        } else {
            llNextLevel.visibility = View.INVISIBLE
        }
    }

    /** init actions buttons events */
    private fun initActsBtsEvents() {
        layoutHolder.ActionsBts().llBackToMenu.setOnClickListener {
            finish()
        }
        layoutHolder.ActionsBts().llRestart.setOnClickListener {
            restartGame()
        }
        if(canGoNextLevel()) {
            layoutHolder.ActionsBts().llNextLevel.setOnClickListener {
                startNextLevelGame()
            }
        }
    }

    /** check if can go to next level */
    private fun canGoNextLevel() : Boolean =
        layoutHolder.ActionsBts().llNextLevel.visibility == View.VISIBLE &&
                layoutHolder.ActionsBts().llNextLevel.isEnabled

    /** start game */
    private fun startGame(playQQPACK: PlayQQPack) {
        intentsManager.startPlayGameActivity(playQQPACK, ReqCodes.PLAY_QUIZ_REQ_CODE)
    }

    /** start next level game */
    private fun startNextLevelGame() {
        val playQQPack = PlayQQPack(quizRes.playQQPACK.numOfQuestions,
            QuizQuestionGen.Filter(QuizQuestionGen.Filter.FilterType.WHITE_LIST,
                setOf(nextLevel!!.id)))
        startGame(playQQPack)
    }

    /** restart game */
    private fun restartGame() {
        startGame(quizRes.playQQPACK)
    }

    /** get quiz result info from extra */
    private fun getQuizRes() : QuizRes {
        try {
            return intent.getParcelableExtra(QUIZ_RES_EXTRA_IN)!!
        } catch (e: Exception) {
            throw MissingResourceException("Cannot find '$QUIZ_RES_EXTRA_IN' in extra at " +
                    "QuizResultActivity", javaClass.name, "")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            ReqCodes.PLAY_QUIZ_REQ_CODE -> {
                finish()
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.run {
            // restore here
            val feedbackMsg = getString(SaveIns.FEEDBACK_MSG_KEY)
            layoutHolder.tvPrimaryMsg.text = feedbackMsg
            lastUserLevel = getParcelable(SaveIns.LAST_USER_LVL_KEY)
            newUserLevel = getParcelable(SaveIns.NEW_USER_LVL_KEY)
            bestUserLevel = getParcelable(SaveIns.BEST_USER_LVL_KEY)
            storyLevel = getParcelable(SaveIns.STORY_LVL_KEY)
            nextLevel = getParcelable(SaveIns.NEXT_LVL_KEY)
        }
        buildResultViews(createNewFeedback=false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            // save here
            putString(SaveIns.FEEDBACK_MSG_KEY, layoutHolder.tvPrimaryMsg.text.toString())
            putParcelable(SaveIns.LAST_USER_LVL_KEY, lastUserLevel)
            putParcelable(SaveIns.NEW_USER_LVL_KEY, newUserLevel)
            putParcelable(SaveIns.BEST_USER_LVL_KEY, bestUserLevel)
            putParcelable(SaveIns.STORY_LVL_KEY, storyLevel)
            putParcelable(SaveIns.NEXT_LVL_KEY, nextLevel)
        }
        super.onSaveInstanceState(outState)
    }

    private inner class LayoutHolder {
        val rlAllInfo: RelativeLayout = findViewById(R.id.relativeLayout_allInfo)
        val tvPrimaryMsg: TextView = findViewById(R.id.textView_primaryMsg)
        val btMoreInfo: TextView = findViewById(R.id.button_viewAnswersInfo)
        val svUserStars: StarsView = findViewById(R.id.starsView_userStars)

        inner class InfoSection {
            val llEarnXpSection: LinearLayout = findViewById(R.id.linearLayout_earnXpSection)
            val tvEarnXpValue: TextView = findViewById(R.id.textView_earnXpValue)
            val tvTime: TextView = findViewById(R.id.textView_time)
            val tvDeltaFromRecord: TextView = findViewById(R.id.textView_deltaFromRecord)
            val tvNewRecord: TextView = findViewById(R.id.textView_newRecord)
        }

        inner class ActionsBts {
            val llBackToMenu: LinearLayout = findViewById(R.id.linearLayout_backToMenu)
            val llRestart: LinearLayout = findViewById(R.id.linearLayout_restart)
            val llNextLevel: LinearLayout = findViewById(R.id.linearLayout_nextLevel)
        }
    }

    /** quiz result */
    @Parcelize
    data class QuizRes(val playQQPACK: PlayQQPack,
                       val questionsResultInfo: ArrayList<QuizQuesResInfo>,
                       val usersStars: Int,
                       val totalTime: Long) : Parcelable {

        // check if is story quiz result
        fun isStoryRes() : Boolean =
            playQQPACK.filter.filterType == QuizQuestionGen.Filter.FilterType.WHITE_LIST &&
                    playQQPACK.filter.levelsIds.size == 1 &&
                    questionsResultInfo.isNotEmpty()

        // get story level id
        val storyLevelId: Int
        get() {
            require(isStoryRes()) {
                "Cannot get story level because is not a story"
            }
            return  playQQPACK.filter.levelsIds.first()
        }
    }

    companion object {
        private const val LOG_TAG = "QuizResultActivity"

        // extras
        const val QUIZ_RES_EXTRA_IN = "quizResult"

        /** request codes */
        private object ReqCodes {
            const val PLAY_QUIZ_REQ_CODE = 0
        }

        /** save instance state keys */
        private object SaveIns {
            const val FEEDBACK_MSG_KEY = "feedbackMsg"
            const val LAST_USER_LVL_KEY = "lastUserLvl"
            const val NEW_USER_LVL_KEY = "newUserLvl"
            const val BEST_USER_LVL_KEY = "bestUserLvl"
            const val STORY_LVL_KEY = "storyUserLvl"
            const val NEXT_LVL_KEY = "nextUserLvl"
        }
    }
}
