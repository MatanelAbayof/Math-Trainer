package heb.apps.mathtrainer.ui.activities.quiz

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.data.quiz.factory.QuizQuestionGen
import heb.apps.mathtrainer.data.quiz.quizquestion.PlayQQPack
import heb.apps.mathtrainer.data.quiz.quizquestion.QuizQuestion
import heb.apps.mathtrainer.memory.sp.tutorial.TutorialSp
import heb.apps.mathtrainer.ui.activities.MathBaseActivity
import heb.apps.mathtrainer.ui.activities.quiz.result.QuizResultActivity
import heb.apps.mathtrainer.ui.dialogs.quiz.ExitGameDialog
import heb.apps.mathtrainer.ui.dialogs.quiz.ResumeGameDialog
import heb.apps.mathtrainer.ui.dialogs.tutorial.GameTutorialDialog
import heb.apps.mathtrainer.ui.widget.PercentageBar
import heb.apps.mathtrainer.ui.widget.StarsView
import heb.apps.mathtrainer.utils.debug.TestsManager
import heb.apps.mathtrainer.utils.load.LoadGroup
import heb.apps.mathtrainer.utils.time.StopWatch
import heb.apps.mathtrainer.utils.time.TimeCounter
import kotlinx.android.parcel.Parcelize
import java.util.*

typealias QuizQuesResInfo = PlayQuizActivity.QuizQuestionResInfo

class PlayQuizActivity : MathBaseActivity() {

    private lateinit var tutorialSP: TutorialSp
    private lateinit var layoutHolder: LayoutHolder
    private lateinit var playQQPack: PlayQQPack
    private lateinit var questions: List<QuizQuestion>
    private var questionsResult = arrayListOf<QuizQuestionResInfo>()
    private var currentQuestionIndex = -1
    /** measure time left for current question */
    private val timeLeftSW = StopWatch(this)
    /** measure total time of all quiz */
    private val totalTimeCounter = TimeCounter()
    private var gameStatus = GameStatus.PLAYING
    /** flag that tell when game is paused in middle of the game */
    private var gamePausedFlag = false
    /** flag that tell when game is in waiting mode */
    private var waitingModeFlag = false

    //region dialogs
    private val resumeGameDialog by lazy {
        val resumeGameDialog = ResumeGameDialog(this@PlayQuizActivity)
        resumeGameDialog.onContinueBtClicked = {
            stopWaitingMode()
        }
        resumeGameDialog.onExitBtClicked = {
            exitGame()
        }
        resumeGameDialog.create()
    }
    private val exitGameDialog by lazy {
        val exitGameDialog = ExitGameDialog(this@PlayQuizActivity)
        exitGameDialog.onExit = {
            exitGame()
        }
        exitGameDialog.create()
    }
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        allowExit = false

        setActivityView(R.layout.activity_play_quiz)
        layoutHolder = LayoutHolder()
        tutorialSP = TutorialSp(this)

        // update stars
        layoutHolder.svUsersStars.numOfStars = StarsView.MAX_NUM_OF_STARS
        layoutHolder.svUsersStars.numOfFilledStars = StarsView.MAX_NUM_OF_STARS

        // read play quiz question pack
        playQQPack = getPlayQQPack()

        initEvents()

        if(savedInstanceState == null) {
            loadQuestions {
                showGameTutorialIfNeed {
                    startGame()
                }
            }
        }
    }

    /** continue play */
    private fun continuePlay() {
        if(gameStatus == GameStatus.PLAYING) {
            loadQuestions {
                totalTimeCounter.start()
                tryRemoveStar() // for current question
                if(isNotHaveStars())
                    finishGame()
                else
                    goToNextQuestion()
            }
        } else {
            allowExit = true
            exitGame()
        }
    }

    /** show game tutorial if need */
    private fun showGameTutorialIfNeed(onClosed: () -> Unit) {
        if (tutorialSP.showGameTutorial) {
            // show game tutorial
            val gameTutorialDialog = GameTutorialDialog(this)
            gameTutorialDialog.onClosedListener = { showAgain ->
                tutorialSP.showGameTutorial = showAgain
                onClosed()
            }
            gameTutorialDialog.show()
        } else
            onClosed()
    }

    /** init events */
    private fun initEvents() {
        layoutHolder.ivChooseGift.setOnClickListener {
            // TODO open gift dialog
        }
    }

    /** load questions */
    private fun loadQuestions(onFinished : () -> Unit) {
        showLoadingView()

        val qqInstance = QuizQuestionGen.getInstance(this)
        qqInstance.genQuizQuestions(playQQPack.numOfQuestions, playQQPack.filter) {
            this.questions = it

            loadQuestionsViews {
                allowExit = true
                hideLoadingView()

                onFinished()
            }
        }
    }

    /** load all questions views */
    private fun loadQuestionsViews(onFinish: () -> Unit) {
        val loadGroup = LoadGroup(questions)
        loadGroup.onLoadFinish = onFinish
        loadGroup.startLoading()
    }

    /** start game */
    private fun startGame() {
        // TODO show timer first

        // start total timer
        totalTimeCounter.start()

        goToNextQuestion()
    }

    /** get current question */
    private fun getCurrQuestion() : QuizQuestion {
        require (currentQuestionIndex in questions.indices) {
            "Cannot find question with index=$currentQuestionIndex at questions"
        }
        return questions[currentQuestionIndex]
    }

    /** show next question */
    private fun showNextQuestion() {
        currentQuestionIndex++
        updateQuestionNumView()

        val currentQuestion = getCurrQuestion()
        updateCurrentQuestionView()

        // listen user answer event
        currentQuestion.answersLayout.onUserAnswered = { userAnswer ->
            // update result
            val qqResInfo = addCurrentQuesToResult(userAnswer.toEquation())

            var needFinishGame = false
            if(!qqResInfo.isUserAnsweredOK()) {
                tryRemoveStar()
                if(isNotHaveStars())
                    needFinishGame = true
            }


            showQuesResMark(qqResInfo.isUserAnsweredOK()) {
                removeQuestionView()

                if(needFinishGame) {
                    finishGame()
                } else {
                    timeLeftSW.stop()
                    goToNextQuestion()
                }
            }
        }

        startTimeLeftTimer()
    }

    /** add current question to result. return the result */
    private fun addCurrentQuesToResult(userAnswerEquation: String? = null) : QuizQuestionResInfo {
        val currentQuestion = getCurrQuestion()
        val qqResInfo = QuizQuestionResInfo(currentQuestion.question.toEquation(),
            currentQuestion.correctAnswer.toEquation(), userAnswerEquation)
        questionsResult.add(qqResInfo)
        return qqResInfo
    }

    /** go to next question if have */
    private fun goToNextQuestion() {
        if(isFinishQuestions()) {
            finishGame()
        } else
            showNextQuestion()
    }

    /** add missing questions to result */
    private fun addMissingQuestionsToResult() {
        currentQuestionIndex++ // go to next question
        val numMissingQuestions = questions.size - currentQuestionIndex
        repeat(numMissingQuestions) {
            addCurrentQuesToResult()
            currentQuestionIndex++
        }
    }

    /** finish game */
    private fun finishGame() {
        gameStatus = GameStatus.FINISHED
        timeLeftSW.stop()
        //addMissingQuestionsToResult()
        startQuizResultActivity()
    }

    /** start time left timer */
    private fun startTimeLeftTimer() {
        layoutHolder.pbTimeLeft.percentage = PercentageBar.MAX_PERCENTAGE
        val currentQuestion = getCurrQuestion()
        var quesMaxTime = currentQuestion.level!!.minWinTime

        TestsManager.run {
            if(TestsManager.ENABLE_INF_TIME)
                quesMaxTime = TestsManager.INF_VAL
        }

        timeLeftSW.start(quesMaxTime, TIMER_UPDATE_INTERVAL, { remainingTime ->
            // calculate left percentage
            var leftPercentage = (100f*remainingTime/quesMaxTime).toInt()
            if(leftPercentage > PercentageBar.MAX_PERCENTAGE)
                leftPercentage = PercentageBar.MAX_PERCENTAGE
            else if(leftPercentage < PercentageBar.MIN_PERCENTAGE)
                leftPercentage = PercentageBar.MIN_PERCENTAGE
            layoutHolder.pbTimeLeft.percentage = leftPercentage
        },{
            layoutHolder.pbTimeLeft.percentage = PercentageBar.MIN_PERCENTAGE
            onTimeLeft()
        })
    }

    /** event when time of question left */
    private fun onTimeLeft() {
        removeQuestionView()
        // remove star if need
        tryRemoveStar()
        addCurrentQuesToResult()
        if(isNotHaveStars()) {
            finishGame()
        }
        else {
            // check if game paused
            if(gamePausedFlag) {
                goToWaitingMode()
            } else {
                goToNextQuestion()
            }
        }
    }

    /** go to waiting mode from pause */
    private fun goToWaitingMode() {
        waitingModeFlag = true
        timeLeftSW.stop()
        totalTimeCounter.pause()
        // TODO stop total time!
        removeQuestionView()
    }

    /** check if not left stars */
    private fun isNotHaveStars() : Boolean = (layoutHolder.svUsersStars.numOfFilledStars == 0)

    /** remove star */
    private fun tryRemoveStar() {
        val canRemoveStar = (layoutHolder.svUsersStars.numOfFilledStars > 0)
        if(canRemoveStar)
            layoutHolder.svUsersStars.numOfFilledStars--
    }

    /** show question result mark */
    private fun showQuesResMark(isCorrectedAnswer: Boolean, onFinish: () -> Unit) {
        val currentQuestion = getCurrQuestion()
        currentQuestion.answersLayout.showAnswerFeedback(currentQuestion.correctAnswer)

        // call finish with delayed time
        Handler().postDelayed({
            onFinish()
        }, RES_MARK_TIME)
    }

    /** finish game */
    private fun startQuizResultActivity() {
        val totalTime = totalTimeCounter.stopAndGetTimeLeft() // TODO was bug here! (time left???)
        val numOfFilledStars = layoutHolder.svUsersStars.numOfFilledStars

        val quizRes = QuizResultActivity.QuizRes(playQQPack,
            questionsResult, numOfFilledStars, totalTime)
        intentsManager.QuizResult().startQuizResultActivity(quizRes, ReqCodes.QUIZ_RESULT_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            ReqCodes.QUIZ_RESULT_REQUEST_CODE -> {
                exitGame()
            }
        }
    }

    /** exit game */
    private fun exitGame() {
        timeLeftSW.stop()
        super.finish()
    }

    override fun finish() {
        if(allowExit) {
            if(!exitGameDialog.isShowing)
                exitGameDialog.show()
        }
    }

    /** remove question view */
    private fun removeQuestionView() {
        layoutHolder.flQuizQues.removeAllViews()
    }

    /** update current question view */
    private fun updateCurrentQuestionView() {
        val currentQuestion = questions[currentQuestionIndex]
        layoutHolder.flQuizQues.addView(currentQuestion.getView())
    }

    /** check if no left questions */
    private fun isFinishQuestions() : Boolean = currentQuestionIndex + 1 >= questions.size

    /** get quiz questions info from extra */
    private fun getPlayQQPack() : PlayQQPack {
        try {
            return intent.getParcelableExtra(PLAY_QQ_PACK_EXTRA_IN)!!
        } catch (e: Exception) {
            throw MissingResourceException("Cannot find '$PLAY_QQ_PACK_EXTRA_IN'" +
                    " in extra at ${this::class.qualifiedName}", javaClass.name, "")
        }
    }

    /** update question number view */
    private fun updateQuestionNumView() {
        val questionNumText = "${currentQuestionIndex+1}/${questions.size}"
        layoutHolder.tvQuestionNum.text = questionNumText
    }


    override fun onPause() {
        // cancel exit dialog if showed
        if(exitGameDialog.isShowing)
            exitGameDialog.dismiss()

        // update pause flag
        if(gameStatus == GameStatus.PLAYING)
            gamePausedFlag = true
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        // check if return from pause and game it not finished
        if((gamePausedFlag)&&(gameStatus == GameStatus.PLAYING)) {
            gamePausedFlag = false

            if(waitingModeFlag) {
                // show message to continue the game
                if(!resumeGameDialog.isShowing)
                    resumeGameDialog.show()
            }
        }
    }

    /** stop waiting mode */
    private fun stopWaitingMode() {
        waitingModeFlag = false
        totalTimeCounter.resume()
        goToNextQuestion()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        timeLeftSW.stop()
        outState.run {
            putParcelableArrayList(SaveIns.QUESTIONS_RESULT_KEY, questionsResult)
            putInt(SaveIns.CURRENT_QUESTION_IDX_KEY, currentQuestionIndex)
            if(totalTimeCounter.isActive)
                putLong(SaveIns.TOTAL_TIME_KEY, totalTimeCounter.timeLeft)
            putParcelable(SaveIns.GAME_STATUS_KEY, gameStatus)
            putBoolean(SaveIns.GAME_PAUSED_FLAG_KEY, gamePausedFlag)
            putBoolean(SaveIns.WAIT_MODE_FLAG_KEY, waitingModeFlag)
            putInt(SaveIns.NUM_OF_FILLED_STARS_KEY, layoutHolder.svUsersStars.numOfFilledStars)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.run {
            questionsResult = getParcelableArrayList(SaveIns.QUESTIONS_RESULT_KEY)!!
            currentQuestionIndex = getInt(SaveIns.CURRENT_QUESTION_IDX_KEY)
            val totalTime = getLong(SaveIns.TOTAL_TIME_KEY) // return 0L if not found
            totalTimeCounter.appendTime(totalTime)
            gameStatus = getParcelable(SaveIns.GAME_STATUS_KEY)!!
            gamePausedFlag = getBoolean(SaveIns.GAME_PAUSED_FLAG_KEY)
            waitingModeFlag = getBoolean(SaveIns.WAIT_MODE_FLAG_KEY)
            layoutHolder.svUsersStars.numOfFilledStars = getInt(SaveIns.NUM_OF_FILLED_STARS_KEY)
        }

        continuePlay()
    }

    @Parcelize
    /** quiz question result info */
    data class QuizQuestionResInfo(val questionText: String,
                              val correctAnswerText: String,
                              val userAnswerText: String? = null) : Parcelable {

        // check if user answer OK
        fun isUserAnsweredOK() : Boolean = (correctAnswerText == userAnswerText)
    }

    private inner class LayoutHolder {
        val pbTimeLeft: PercentageBar = findViewById(R.id.percentageBar_timeLeft)
        val tvQuestionNum: TextView = findViewById(R.id.textView_questionNum)
        val flQuizQues: FrameLayout = findViewById(R.id.frameLayout_quizQuestion)
        val llQuizSection: LinearLayout = findViewById(R.id.linearLayout_quizSection)
        val ivChooseGift: ImageView = findViewById(R.id.imageView_chooseGift)
        val svUsersStars: StarsView = findViewById(R.id.starsView_userStars)
    }

    @Parcelize
    private enum class GameStatus : Parcelable {
        PLAYING, FINISHED
    }

    companion object {
        //private const val LOG_TAG = "PlayQuizAct"

        /** time to show question result mark in millis */
        const val RES_MARK_TIME = 1000L
        /** interval for update time in millis */
        const val TIMER_UPDATE_INTERVAL = 50L

        // extras
        const val PLAY_QQ_PACK_EXTRA_IN = "playQQPack"

        /** request codes */
        private object ReqCodes {
            const val QUIZ_RESULT_REQUEST_CODE = 0
        }

        /** save instance state keys */
        private object SaveIns {
            //const val QUESTIONS_KEY = "questions" // TODO: need save last questions or reload new
            const val QUESTIONS_RESULT_KEY = "questionsResult"
            const val CURRENT_QUESTION_IDX_KEY = "currentQuestionIdx"
            const val TOTAL_TIME_KEY = "totalTime" // TODO: use this
            const val GAME_STATUS_KEY = "gameStatus"
            const val GAME_PAUSED_FLAG_KEY = "gamePausedFlag"
            const val WAIT_MODE_FLAG_KEY = "waitModeFlag"
            const val NUM_OF_FILLED_STARS_KEY = "numOfFilledStars"
        }
    }
}
