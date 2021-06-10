package heb.apps.mathtrainer.ui.activities

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import heb.apps.mathtrainer.BuildConfig
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.data.quiz.factory.QuizQuestionGen
import heb.apps.mathtrainer.data.quiz.quizquestion.PlayQQPack
import heb.apps.mathtrainer.data.user.UserInfo
import heb.apps.mathtrainer.exception.MissingArgException
import heb.apps.mathtrainer.memory.db.tasks.TestDBTask
import heb.apps.mathtrainer.memory.db.tasks.UpdateResourcesTask
import heb.apps.mathtrainer.memory.db.tasks.levelsxps.GetUserLevelXpTask
import heb.apps.mathtrainer.memory.db.tasks.levelsxps.UserLevelXp
import heb.apps.mathtrainer.memory.sp.app.AppSp
import heb.apps.mathtrainer.memory.sp.tutorial.TutorialSp
import heb.apps.mathtrainer.memory.sp.user.UserSp
import heb.apps.mathtrainer.ui.activities.quiz.PlayQuizActivity
import heb.apps.mathtrainer.ui.activities.quiz.result.QuizResultActivity
import heb.apps.mathtrainer.ui.dialogs.BaseDialog
import heb.apps.mathtrainer.ui.dialogs.ContactUsDialog
import heb.apps.mathtrainer.ui.dialogs.RateUsDialog
import heb.apps.mathtrainer.ui.dialogs.WhatsNewDialog
import heb.apps.mathtrainer.ui.widget.BaseButton
import heb.apps.mathtrainer.ui.widget.PercentageBar
import heb.apps.mathtrainer.utils.MarketHelper
import heb.apps.mathtrainer.utils.debug.Test
import heb.apps.mathtrainer.utils.debug.TestsManager
import heb.apps.mathtrainer.utils.json.JSONObj
import heb.apps.mathtrainer.utils.string.format

class MainActivity : MathBaseActivity(false) {

    private lateinit var layoutHolder: LayoutHolder
    private lateinit var userSP: UserSp
    private lateinit var appSP: AppSp
    private lateinit var tutorialSP: TutorialSp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityView(R.layout.activity_main)
        layoutHolder = LayoutHolder()
        userSP = UserSp(this)
        appSP = AppSp(this)
        tutorialSP = TutorialSp(this)

        showLoadingView()

        val isNewVersion = checkAppNewVersion()

        // update DB when app updated, or when in debug mode
        if(TestsManager.IS_TEST_MODE || isNewVersion) {
            allowExit = false

            val updateResourcesTask = UpdateResourcesTask(applicationContext)
            updateResourcesTask.onFinishListener = {

                if(isNewVersion) {

                    // show What's new dialog
                    val wnd = WhatsNewDialog(this)
                    wnd.setOnDismissListener {
                        onFinishLoadResources()
                    }
                    wnd.show()

                } else {
                    onFinishLoadResources()
                }
            }
            updateResourcesTask.start()
        } else {
            onFinishLoadResources()
        }
    }

    /** check if app is new version */
    private fun checkAppNewVersion() : Boolean {
        val lastAppVersion = appSP.lastVersionCode
        val currentAppVersion = BuildConfig.VERSION_CODE
        val isNewVersion = (lastAppVersion < currentAppVersion)

        if(isNewVersion)
            appSP.lastVersionCode = currentAppVersion

        return isNewVersion
    }

    /** on finish load resources */
    private fun onFinishLoadResources() {
        allowExit = true

        handleViewsEvents()

        if(tutorialSP.showMainScreenTutorial) {
            // TODO show tutorial
            //Toast.makeText(this, "tutorial", Toast.LENGTH_LONG).show()

            checkLogin()
            
            // TODO: tutorialSP.showMainScreenTutorial = false
        } else {
            checkLogin()
        }
    }

    /** check user logging info */
    private fun checkLogin() {
        // clear welcome message
        layoutHolder.tvWelcome.text = ""

        // get user info
        val userInfo = userSP.userInfo

        // check if not logged
        if(!userInfo.isLogged()) {
            showNotSignedDialog()
            hideLoadingView()
        } else {
            updateUserInfoView()
        }
    }

    /** update user info view */
    private fun updateUserInfoView() {
        updateWelcomeMessage()
        updateUserLevelView()
    }

    /** update user level view */
    private fun updateUserLevelView() {
        showLoadingView()

        val getUserLevelTsk = GetUserLevelXpTask(this)
        getUserLevelTsk.onFinishListener = { userLvlXp ->
            buildUserLevelView(userLvlXp!!)
            layoutHolder.llUserLvlXp.visibility = View.VISIBLE
            hideLoadingView()
        }
        getUserLevelTsk.start()
    }

    /** build user level view */
    private fun buildUserLevelView(userLvlXp: UserLevelXp) {
        if(userLvlXp.hasNextLevel()) {
            layoutHolder.pbXp.percentage = userLvlXp.getLeftXpNextLvlPercents()
            layoutHolder.pbXp.visibility = View.VISIBLE
        } else {
            layoutHolder.pbXp.visibility = View.GONE
        }

        layoutHolder.tvLevelNum.text = String.format(this, R.string.level_num, userLvlXp.currentLvl!!.id)
        layoutHolder.tvXp.text = String.format(this, R.string.xp_num, userLvlXp.earnXp)

        layoutHolder.llUserLvlXp.setOnClickListener {
            // TODO: show user left XP for next level in dialog
        }
    }

    /** handle views events */
    private fun handleViewsEvents() {
        layoutHolder.btPlay.setOnClickListener {
            // TODO need to go to ChooseGameTypeActivity

            startChooseCategoryActivity()
        }

        layoutHolder.btSettings.setOnClickListener {
            startMainSettingsActivity()
        }
    }

    /** start choose category activity */
    private fun startChooseCategoryActivity() {
        intentsManager.Choosers().startChooseCategoryActivity(ReqCodes.PLAY_GAME_REQ_CODE)
    }

    /** start main settings activity */
    private fun startMainSettingsActivity() {
        intentsManager.Settings().startMainSettingsActivity(ReqCodes.SETTINGS_REQ_CODE)
    }

    @Test
    private fun runTestDB() { // TODO remove this test

        val testDB = TestDBTask(applicationContext)
        testDB.onFinishListener = {
           // layoutHolder.tvWelcome.text ="res=" + it!!.map { it.completePercents }
        }
        testDB.start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            when(requestCode) {
                ReqCodes.SIGN_UP_REQ_CODE -> {
                    if (resultCode == Activity.RESULT_OK) {
                        // get result of user info
                        val userInfoStr =
                            data!!.getStringExtra(SignUpActivity.USER_INFO_EXTRA_OUT)
                        if (userInfoStr.isNullOrEmpty())
                            throw MissingArgException("Cannot parse UserInfo because SignUpActivity" +
                                    " return null result")
                        val userInfo = UserInfo.parse(JSONObj(userInfoStr))
                        // update user info in memory
                        userSP.userInfo = userInfo
                    }

                    checkLogin()
                }

                ReqCodes.SETTINGS_REQ_CODE -> {
                    checkLogin()
                }

                ReqCodes.PLAY_GAME_REQ_CODE -> {
                    updateUserLevelView()
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val openDbBuilder = menu.findItem(R.id.openDbBuilder)
        val openTestsActivity = menu.findItem(R.id.openTestsActivity)
        val openDummyResActivity = menu.findItem(R.id.openDummyResActivity)
        openDbBuilder.isVisible = TestsManager.IS_TEST_MODE
        openTestsActivity.isVisible = TestsManager.IS_TEST_MODE
        openDummyResActivity.isVisible = TestsManager.IS_TEST_MODE
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {
                openShareAppIntent()
                return true
            }
            R.id.about -> {
                // TODO
                return true
            }
            R.id.help -> {
                // TODO
                return true
            }
            R.id.contactUs -> {
                showContentUsDialog()
                return true
            }
            R.id.openDbBuilder -> {
                startDbBuilderActivity()
            }
            R.id.openDummyResActivity -> {
                startDummyResActivity()
            }
            R.id.openTestsActivity -> {
                startTestsActivity()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        if(false) // TODO need to show when exit after x enters and if user don't rated already
            showRateUsDialog()
        else
            finish()
    }

    /** update welcome message from memory */
    private fun updateWelcomeMessage() {
        val userInfo = userSP.userInfo
        if(userInfo.isLogged()) {
            val userMessage =  applicationContext.getString(R.string.pre_user_hello_message) + " " + userInfo.name
            layoutHolder.tvWelcome.text = userMessage
        }
    }

    /** show rate us dialog */
    private fun showContentUsDialog() {
        val contactUsDialog = ContactUsDialog(this@MainActivity)
        contactUsDialog.show()
    }

    /** show rate us dialog */
    private fun showRateUsDialog() {
        val rateUsDialog = RateUsDialog(this@MainActivity)
        rateUsDialog.onRateUsClicked = {
            MarketHelper.launchMarket(this, packageName)
            finish()
        }
        rateUsDialog.onExitClicked = {
            finish()
        }
        rateUsDialog.create().show()
    }

    /** open share application intent */
    private fun openShareAppIntent() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        val appLink = getString(R.string.google_play_pre_app_link) + packageName
        val shareMessage = getString(R.string.share_pre_message) + '\n' + appLink // TODO better way it's to use String.format
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_via)))
    }

    /** show not sign dialog */
    private fun showNotSignedDialog() {
        val notSignDialog = object : BaseDialog(this@MainActivity) {
            init {
                setMessage(getString(R.string.not_signed_err_msg))
                setCancelable(false)
                setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.sign_up)) { _, _ ->
                    startSignUpActivity()
                }
                setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.exit)) { _, _ ->
                    finish()
                }
            }
        }
        notSignDialog.show()
    }

    /** start DB builder activity */
    private fun startDbBuilderActivity() {
        intentsManager.Tests().startDbBuilderActivity()
    }

    /** start sign up activity */
    fun startSignUpActivity() {
        intentsManager.startSignUpActivity(ReqCodes.SIGN_UP_REQ_CODE)
    }

    @Test
    private fun startTestsActivity() {
        intentsManager.Tests().startTestsActivity()
    }

    @Test
    private fun startDummyResActivity() {
        val playQQPack = PlayQQPack(100, QuizQuestionGen.Filter(QuizQuestionGen.Filter.FilterType.WHITE_LIST, setOf(1)))
        val questionsResult = arrayListOf<PlayQuizActivity.QuizQuestionResInfo>()
        questionsResult.add(PlayQuizActivity.QuizQuestionResInfo("3+5+5+5+5+5+5+5+5+5+5+5+x+y*3+5+5+5+5+5+5+5+5+5+5+5+x+y-3+5+5+5+5+5+5+5+5+5+5+5+x+y", "8", "8"))

        for(i in 1..30)
            questionsResult.add(PlayQuizActivity.QuizQuestionResInfo("$i+3", "8", "7"))
        val quizRes = QuizResultActivity.QuizRes(playQQPack, questionsResult, 1, 88L)

        intentsManager.QuizResult().startQuizResultActivity(quizRes)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        /* savedInstanceState.run {
            // restore here
        } */
    }

    override fun onSaveInstanceState(outState: Bundle) {
        /* outState.run {
            // save here
        } */
        super.onSaveInstanceState(outState)
    }

    companion object {
        private const val LOG_TAG = "MainActivity"

        /** request codes */
        private object ReqCodes {
            const val SIGN_UP_REQ_CODE = 0
            const val SETTINGS_REQ_CODE = 1
            const val PLAY_GAME_REQ_CODE = 2
        }
    }

    private inner class LayoutHolder {
        val tvWelcome: TextView = findViewById(R.id.textView_welcomeMessage)
        val llUserLvlXp: LinearLayout = findViewById(R.id.linearLayout_userLvlXp)
        val pbXp: PercentageBar = findViewById(R.id.percentageBar_xp)
        val tvLevelNum: TextView = findViewById(R.id.textView_numOfLevel)
        val tvXp: TextView = findViewById(R.id.textView_xp)
        val btPlay: BaseButton = findViewById(R.id.button_startGame)
        val btSettings: BaseButton = findViewById(R.id.button_settings)
    }
}
