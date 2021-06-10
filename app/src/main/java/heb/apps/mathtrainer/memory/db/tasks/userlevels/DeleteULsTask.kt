package heb.apps.mathtrainer.memory.db.tasks.userlevels

import android.content.Context
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask

class DeleteULsTask(ctx: Context) : MathDBTask<Void, Void>(ctx) {

    override fun doInBack(db: MathDB, input: Void?): Void? {
        db.userLevelDao().deleteAll()
        return null
    }

}