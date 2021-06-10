package heb.apps.mathtrainer.memory.db.tasks.categories

import android.content.Context
import heb.apps.mathtrainer.data.quiz.category.Category
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask

class GetCategoriesTask(ctx: Context) : MathDBTask<Void, List<Category>>(ctx) {

    // do in background
    override fun doInBack(db: MathDB, input: Void?): List<Category> = db.categoryDao().getAll()

}