package heb.apps.mathtrainer.memory.db.tasks.categories

import android.content.Context
import heb.apps.mathtrainer.data.quiz.category.Category
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask

class GetCategoryByNameTask(ctx: Context) : MathDBTask<String, Category>(ctx) {

    override fun doInBack(db: MathDB, uniqueName: String?): Category? {
        return when(val category = db.categoryDao().getCategoryByName(uniqueName!!)) {
            null -> throw IllegalArgumentException("Cannot find category with " +
                    "uniqueName=$uniqueName at DB")
            else -> category
        }
    }


}