package heb.apps.mathtrainer.memory.db.tasks.levelsxps

import android.content.Context
import heb.apps.mathtrainer.data.quiz.level.Level
import heb.apps.mathtrainer.data.quiz.levelxp.LevelXp
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask

class GetLevelsXpsTask(ctx: Context) : MathDBTask<Void, List<LevelXp>>(ctx) {

    override fun doInBack(db: MathDB, input: Void?): List<LevelXp>? = db.levelXpDao().getLevelsXps()

}