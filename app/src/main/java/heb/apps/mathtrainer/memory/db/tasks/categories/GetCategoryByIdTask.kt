package heb.apps.mathtrainer.memory.db.tasks.categories

import android.content.Context
import heb.apps.mathtrainer.data.quiz.category.Category
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask

class GetCategoryByIdTask(ctx: Context) : MathDBTask<Int, Category>(ctx) {

    // input = category id
    override fun doInBack(db: MathDB, input: Int?): Category? {
        return when(val category = db.categoryDao().getCategoryById(input!!)) {
            null -> throw IllegalArgumentException("Cannot find category with id=$input at DB")
            else -> category
        }
    }
}