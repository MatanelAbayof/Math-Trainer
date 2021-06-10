package heb.apps.mathtrainer.ui.activities.settings.account

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.data.user.UserInfo
import heb.apps.mathtrainer.memory.sp.user.UserSp
import heb.apps.mathtrainer.ui.activities.settings.SettingsActivity
import heb.apps.mathtrainer.ui.widget.BaseButton

class AccountSettActivity : SettingsActivity() {

    inner class LayoutHolder {
        val tvUserName: TextView = findViewById(R.id.imageView_userName)
        val btLogOut: BaseButton = findViewById(R.id.button_log_out)
        val ivGender: ImageView = findViewById(R.id.imageView_gender)
    }

    // members
    private lateinit var layoutHolder: LayoutHolder
    private lateinit var userSP: UserSp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityView(R.layout.activity_account_sett)
        layoutHolder = LayoutHolder()
        userSP = UserSp(applicationContext)

        fillUserInfoViews()

        layoutHolder.btLogOut.setOnClickListener{
            showLogOutDialog()
        }
    }

    // fill user info at views
    private fun fillUserInfoViews() {
        // get user info
        val userInfo = userSP.userInfo
        // fill views
        layoutHolder.tvUserName.text = userInfo.name
        // TODO layoutHolder.ivGender.setImageResource()
    }

    // show log out dialog
    private fun showLogOutDialog() {
        val logOutDialog = AlertDialog.Builder(this@AccountSettActivity) // TODO create dialog class
        logOutDialog.setTitle(R.string.log_out)
        logOutDialog.setMessage(R.string.log_out_msg)
        logOutDialog.setIcon(R.drawable.ic_action_exit_black)
        logOutDialog.setPositiveButton(android.R.string.yes) { _,_ ->
            userSP.userInfo =
                UserInfo() // reset user info to default
            // TODO clear userLevels from DB
            finish()
        }
        logOutDialog.setNeutralButton(android.R.string.no) { _,_ -> }
        logOutDialog.create().show()
    }
}
