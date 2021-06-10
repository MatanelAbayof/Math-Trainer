package heb.apps.mathtrainer.memory.db.tasks.categories

import android.content.Context
import heb.apps.mathtrainer.data.quiz.category.Category
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask

class AddCategoriesTask(ctx: Context) : MathDBTask<List<Category>, Void>(ctx) {

    override fun doInBack(db: MathDB, input: List<Category>?): Void? { // input = categories
        if(input != null) {
            val categoryDao = db.categoryDao()
            for(category in input)
                categoryDao.insert(category)
        }
        return null
    }

}