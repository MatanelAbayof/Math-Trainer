package heb.apps.mathtrainer.ui.activities

import android.app.ActivityManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.utils.view.LayoutInflateHelper

abstract class BaseActivity(displayBackMenuBt: Boolean = true,
                            // allow exit from activity
                            protected var allowExit: Boolean = true)
    : AppCompatActivity() {

    private lateinit var layoutHolder: LayoutHolder
    // icon that show in tasks switcher
    protected abstract val taskSwitcherIconResId: Int

    //region displayBackMenuBt
    protected var displayBackMenuBt = displayBackMenuBt
        set(value) {
            field = value
            updateBackMenuBt()
        }
    //endregion

    //region background
    protected @ColorRes @DrawableRes var landscapeBackResId: Int? = null
        set(value) {
            field = value
            updateBackground()
        }
    protected @ColorRes @DrawableRes var portraitBackResId: Int? = null
        set(value) {
            field = value
            updateBackground()
        }
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base)
        layoutHolder = LayoutHolder()
        updateBackMenuBt()
        initDefBackground()
        updateTaskSwitcherIcon()
    }

    // update icon of tasks switcher
    private fun updateTaskSwitcherIcon() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try { // TODO better way it's to see why crash here in old version
                // label that null = leave the default title
                val td = ActivityManager.TaskDescription(null , taskSwitcherIconResId)
                setTaskDescription(td)
            } catch (e: Throwable) { }
        }
    }

    // init default background
    private fun initDefBackground() {
        landscapeBackResId = R.drawable.landspace_background
        portraitBackResId = R.drawable.portrait_background
    }

    // display/hide back button at action bar
    private fun updateBackMenuBt() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(displayBackMenuBt)
        supportActionBar!!.setDisplayShowHomeEnabled(displayBackMenuBt)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun finish() {
        if(allowExit)
            super.finish()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        updateBackground()
    }

    // update background
    private fun updateBackground() {
        when(resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                if(landscapeBackResId != null)
                    window.decorView.setBackgroundResource(landscapeBackResId!!)
                else
                    removeBackground()
            }
            Configuration.ORIENTATION_PORTRAIT -> {
                if(portraitBackResId != null)
                    window.decorView.setBackgroundResource(portraitBackResId!!)
                else
                    removeBackground()
            }
        }
    }

    // remove background
    private fun removeBackground() {
        window.decorView.setBackgroundResource(0)
    }

    @Deprecated("old function", ReplaceWith("setActivityView(Int|View)"))
    override fun setContentView(layoutResId: Int) {
        super.setContentView(layoutResId)
    }

    // set activity view
    fun setActivityView(view: View) {
        layoutHolder.flActivityInfo.removeAllViews()
        layoutHolder.flActivityInfo.addView(view)
    }

    fun setActivityView(viewResId: Int) {
        val view = LayoutInflateHelper.inflate(this, viewResId)
        setActivityView(view)
    }

    // show loading view
    fun showLoadingView() {
        layoutHolder.flActivityInfo.visibility = View.INVISIBLE
        layoutHolder.pbLoading.visibility = View.VISIBLE
    }

    // hide loading view
    fun hideLoadingView() {
        layoutHolder.pbLoading.visibility = View.GONE
        layoutHolder.flActivityInfo.visibility = View.VISIBLE
    }

    private inner class LayoutHolder {
        val flActivityInfo: FrameLayout = findViewById(R.id.frameLayout_activityInfo)
        val pbLoading: ProgressBar = findViewById(R.id.progressBar_loading)
    }

    companion object {
        private const val LOG_TAG = "BaseActivity"
    }
}