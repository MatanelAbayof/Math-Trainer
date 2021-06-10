package heb.apps.mathtrainer.ui.widget.quiz.level

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.data.quiz.level.UserLevelFullInf
import heb.apps.mathtrainer.ui.widget.StarsView
import heb.apps.mathtrainer.utils.ImageHelper
import heb.apps.mathtrainer.utils.view.LayoutInflateHelper

class LevelBt(ctx: Context, val ulFullInf: UserLevelFullInf, val isLocked: Boolean)
    : RelativeLayout(ctx) {

    private lateinit var layoutHolder: LayoutHolder
    var onHelpClickListener: (LevelBt) -> Unit  = {}
    var onClickListener: (LevelBt) -> Unit  = {}

    init {
        isFocusable = false
        buildLevelView()
        initEvents()
    }

    // build level view
    private fun buildLevelView() {
        val levelView = LayoutInflateHelper.inflate(context, R.layout.level_bt)
        layoutHolder =
            LayoutHolder(
                levelView
            )
        updateLevelInfo()
        // add view as child
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        addView(levelView, lp)
    }

    // init events
    private fun initEvents() {
        layoutHolder.ivHelp.setOnClickListener {
            onHelpClickListener(this)
        }
        layoutHolder.llLevel.setOnClickListener {
            onClickListener(this)
        }
    }

    // update level info
    private fun updateLevelInfo() {
        layoutHolder.tvLevelName.text = ulFullInf.level.displayName
        layoutHolder.tvLevelDiff.text = ulFullInf.level.difficulty.toString()

        updateLevelImg()
        updateHelpBt()
        updateLockView()
        updateNumOfFilledStars()
    }

    // update level image
    private fun updateLevelImg() {
        ulFullInf.level.imagePath?.let { imgPath ->
            val imgRes = ImageHelper.getImgResByName(context, imgPath)
            layoutHolder.ivLevelImage.setImageResource(imgRes)
        }
    }

    // update help button
    private fun updateHelpBt() {
        when(ulFullInf.level.tutorialTextInfo.isNullOrEmpty()) {
            true -> layoutHolder.ivHelp.visibility = View.INVISIBLE
            false -> layoutHolder.ivHelp.visibility = View.VISIBLE
        }
    }

    // update lock view
    private fun updateLockView() {
        when(isLocked) {
            true -> layoutHolder.ivLock.visibility = View.VISIBLE
            false -> layoutHolder.ivLock.visibility = View.INVISIBLE
        }
    }

    // update number of filled stars
    private fun updateNumOfFilledStars() {
        val numOfFilledStars = when(ulFullInf.userLevel) {
            null -> 0
            else -> ulFullInf.userLevel.numOfFilledStars
        }
        layoutHolder.svStars.numOfFilledStars = numOfFilledStars
    }

    private class LayoutHolder(view: View) {
        val llLevel: LinearLayout = view.findViewById(R.id.linearLayout_level)
        val tvLevelName: TextView = view.findViewById(R.id.textView_levelName)
        val tvLevelDiff: TextView = view.findViewById(R.id.textView_levelDifficulty)
        val ivLock: ImageView = view.findViewById(R.id.imageView_lock)
        val svStars: StarsView = view.findViewById(R.id.starsView_levelStars)
        val ivLevelImage: ImageView = view.findViewById(R.id.imageView_levelImage)
        val ivHelp: ImageView = view.findViewById(R.id.imageView_help)
    }
}