package heb.apps.mathtrainer.ui.activities.settings

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.memory.db.tasks.userlevels.DeleteULsTask
import heb.apps.mathtrainer.memory.sp.user.UserSp
import heb.apps.mathtrainer.ui.widget.settings.SettListItem

class MainSettingsActivity : ListSettingsActivity() {

    private lateinit var userSP: UserSp

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        userSP = UserSp(applicationContext)
        buildList()
    }

    override fun onItemClick(settListItem: SettListItem, position: Int) {
        when(position) {
            ListItem.ACCOUNT_SETTINGS.ordinal -> {
                if(userSP.userInfo.isLogged()) {
                    startAccountSettActivity()
                } else {
                    // TODO show suggestion to log in (with dialog)
                }
            }
            ListItem.DISPLAY_SETTINGS.ordinal -> {
                startDisplaySettingsActivity()
            }
            /* ListItem.GAME_SETTINGS.ordinal -> {
                 // TODO game display settings activity
             }
             ListItem.NOTIFICATION_SETTINGS.ordinal -> {
                 // TODO open notifications settings activity
             }*/
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            ACCOUNT_SETT_REQUEST_CODE -> {
                updateSettLI(ListItem.ACCOUNT_SETTINGS.ordinal)
            }
        }
    }

    // build settings list items
    override fun buildSettingsListItems() : List<LiUpdateEvent> {
        val listItemsManager = mutableListOf<LiUpdateEvent>()

        listItemsManager.add {
            SettListItem(getString(R.string.account), getAccountInfo())
        }

        listItemsManager.add {
            SettListItem(getString(R.string.display))
        }

        /*TODO listItemsManager.add(object : ListItemUpdate {
             override fun getUpdatedLI() = SettListItem(getString(R.string.game))
         })
         listItemsManager.add(object : ListItemUpdate {
             override fun getUpdatedLI() = SettListItem(getString(R.string.notifications))
         })*/
        return listItemsManager
    }

    // get account text info
    private fun getAccountInfo() : String {
        val userInfo = userSP.userInfo
        return when (userInfo.isLogged()) {
            true -> getString(R.string.logged_as) + " " + userInfo.name
            false -> getString(R.string.not_connected)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.resetAllSettings -> {
                showResetSettDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // show reset settings dialog
    private fun showResetSettDialog() {
        val resetDialog = AlertDialog.Builder(this@MainSettingsActivity) // TODO create dialog class
        resetDialog.setTitle(R.string.reset_all_settings)
        resetDialog.setMessage(R.string.reset_all_settings_msg)
        resetDialog.setIcon(R.drawable.ic_delete_black_24dp)
        resetDialog.setPositiveButton(android.R.string.ok) { _,_ ->
            resetAllSettings()
        }
        resetDialog.setNegativeButton(android.R.string.cancel)  { _,_ -> }
        resetDialog.create().show()
    }

    // reset all settings
    private fun resetAllSettings() {
        userSP.clearAll()
        settingsSp.clearAll()
        // update logging item
        updateSettLI(ListItem.ACCOUNT_SETTINGS.ordinal)

        // clear userLevels from DB
        val deleteULsTask = DeleteULsTask(applicationContext)
        deleteULsTask.onFinishListener = {
            // show success message
            Toast.makeText(applicationContext, R.string.reset_all_settings_success_msg,
                Toast.LENGTH_LONG).show()
        }
        deleteULsTask.startWithDialog(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_settings_menu, menu)
        return true
    }

    // start display settings activity
    private fun startDisplaySettingsActivity() {
        intentsManager.Settings().startDisplaySettingsActivity()
    }

    // start account settings activity
    private fun startAccountSettActivity() {
        intentsManager.Settings().startAccountSettActivity(ACCOUNT_SETT_REQUEST_CODE)
    }

    private enum class ListItem {
        ACCOUNT_SETTINGS,
        DISPLAY_SETTINGS,
        //GAME_SETTINGS,
        //NOTIFICATION_SETTINGS;
    }

    companion object {
        // request codes
        private const val ACCOUNT_SETT_REQUEST_CODE = 0
    }
}