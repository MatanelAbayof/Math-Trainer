package heb.apps.mathtrainer.memory.db.tasks.categories

import android.content.Context
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask

class DeleteCategoryByIdTask(ctx: Context) : MathDBTask<Int, Void>(ctx) {

    override fun doInBack(db: MathDB, input: Int?): Void? { // input = id
        if(input != null)
            db.categoryDao().deleteCategoryById(input)
        return null
    }

}