package heb.apps.mathtrainer.memory.db.tasks.userlevels

import android.content.Context
import heb.apps.mathtrainer.data.quiz.level.UserLevel
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask

class GetULByLevelIdTask(ctx: Context) : MathDBTask<Int, UserLevel?>(ctx) {

    override fun doInBack(db: MathDB, input: Int?): UserLevel?
                        = db.userLevelDao().getULByLevelId(input!!) // if not found - return null

}