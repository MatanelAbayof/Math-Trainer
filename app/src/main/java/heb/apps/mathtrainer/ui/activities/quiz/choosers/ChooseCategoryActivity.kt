package heb.apps.mathtrainer.ui.activities.quiz.choosers

import android.os.Bundle
import android.widget.LinearLayout
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.data.quiz.category.Category
import heb.apps.mathtrainer.memory.db.tasks.categories.GetCategoriesTask
import heb.apps.mathtrainer.ui.dialogs.ErrorDialog
import heb.apps.mathtrainer.ui.dialogs.help.HelpCategoryDialog
import heb.apps.mathtrainer.ui.widget.quiz.category.CategoryBt
import heb.apps.mathtrainer.utils.view.addMatchPrntView

class ChooseCategoryActivity : BaseChooserActivity() {

    private lateinit var layoutHolder: LayoutHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityView(R.layout.activity_choose_category)
        layoutHolder = LayoutHolder()

        loadCategories()
    }

    /** load categories */
    private fun loadCategories() {
        showLoadingView()

        // get categories from DB
        val getCategoriesTask = GetCategoriesTask(applicationContext)
        getCategoriesTask.onFinishListener = { categories ->
            if(categories.isNullOrEmpty())
                showNoCategoriesDialog()
            else
                addCategoriesBts(categories)
            hideLoadingView()
        }
        getCategoriesTask.start()
    }

    /** show not categories dialog */
    private fun showNoCategoriesDialog() {
        val dialog = ErrorDialog(this@ChooseCategoryActivity)
        dialog.setMessage(R.string.no_categories_error_msg)
        dialog.setOnDismissListener {
            finish()
        }
        dialog.show()
    }

    /** add categories buttons */
    private fun addCategoriesBts(categories: List<Category>) {
        // build views
        categories.forEach { addCategoryBt(it) }
    }

    /** add category button */
    private fun addCategoryBt(category: Category) {
        val categoryBt = CategoryBt(this, category, false) // TODO need to know if button is locked
        categoryBt.onClickListener = {
            if(categoryBt.locked)
                showLockCategoryBtDialog(categoryBt.category)
            else
                startChooseTopicActivity(categoryBt.category)

        }
        categoryBt.onHelpClickListener = { _ ->
            showHelpCategoryDialog(categoryBt.category)
        }
        layoutHolder.llListCategories.addMatchPrntView(categoryBt)
    }

    /** show lock category button dialog */
    private fun showLockCategoryBtDialog(category: Category) {
        // TODO
    }

    /** start choose topic activity */
    private fun startChooseTopicActivity(category: Category) {
        intentsManager.Choosers().startChooseTopicActivity(category.id)
    }

    /** show help category dialog */
    private fun showHelpCategoryDialog(category: Category) {
        val helpCategoryDialog = HelpCategoryDialog(
            this@ChooseCategoryActivity,
            category
        )
        helpCategoryDialog.show()
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

    private inner class LayoutHolder {
        val llListCategories: LinearLayout = findViewById(R.id.linearLayout_categories)
    }
}
