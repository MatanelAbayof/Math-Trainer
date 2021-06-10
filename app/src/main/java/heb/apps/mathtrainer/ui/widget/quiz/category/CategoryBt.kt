package heb.apps.mathtrainer.ui.widget.quiz.category

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.data.quiz.category.Category
import heb.apps.mathtrainer.utils.ImageHelper
import heb.apps.mathtrainer.utils.ResolutionConverter
import heb.apps.mathtrainer.utils.view.LayoutInflateHelper
import heb.apps.mathtrainer.utils.view.setPaddingTop

class CategoryBt(ctx: Context, val category: Category, val locked: Boolean = false)
    : FrameLayout(ctx) {

    private lateinit var layoutHolder: LayoutHolder
    var onHelpClickListener: (CategoryBt) -> Unit  = {}
    var onClickListener: (CategoryBt) -> Unit  = {}

    init {
        isFocusable = false
        buildCategoryView()
        initEvents()
    }

    // build category view
    private fun buildCategoryView() {
        val categoryView = LayoutInflateHelper.inflate(context, R.layout.category_bt)
        layoutHolder =
            LayoutHolder(categoryView)

        // fix padding when unlocked
        if(!locked) {
            val paddingTop  = ResolutionConverter.dipToPx(context, 10)
            layoutHolder.rlCategory.setPaddingTop(paddingTop)
        }

        updateCategoryInfo()
        // add view as child
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        addView(categoryView, lp)
    }

    // update category info
    private fun updateCategoryInfo() {
        layoutHolder.tvCategoryName.text = category.displayName

        category.imagePath?.let { imgPath ->
            val imgRes = ImageHelper.getImgResByName(context, imgPath)
            layoutHolder.ivCategoryImage.setImageResource(imgRes)
        }

        updateLockedBt()
    }

    // init events
    private fun initEvents() {
        layoutHolder.ivHelp.setOnClickListener {
            onHelpClickListener(this)
        }
        layoutHolder.rlCategory.setOnClickListener {
            onClickListener(this)
        }
    }

    // update locked button
    private fun updateLockedBt() {
        when(locked) {
            true -> layoutHolder.ivLock.visibility = View.VISIBLE
            false -> layoutHolder.ivLock.visibility = View.INVISIBLE
        }
    }

    private class LayoutHolder(view: View) {
        val rlCategory: RelativeLayout = view.findViewById(R.id.relativeLayout_category)
        val tvCategoryName: TextView = view.findViewById(R.id.textView_categoryName)
        val ivCategoryImage: ImageView = view.findViewById(R.id.imageView_categoryImage)
        val ivLock: ImageView = view.findViewById(R.id.imageView_lock)
        val ivHelp: ImageView = view.findViewById(R.id.imageView_help)
    }
}