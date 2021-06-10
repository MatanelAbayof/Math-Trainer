package heb.apps.mathtrainer.memory.db.tasks.userlevels

import android.content.Context
import heb.apps.mathtrainer.data.quiz.level.UserLevel
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask
import heb.apps.mathtrainer.ui.widget.StarsView
import heb.apps.mathtrainer.utils.debug.Test

@Test
class CompleteAllLvlsTask(ctx: Context) : MathDBTask<Void, Void>(ctx) {

    override fun doInBack(db: MathDB, input: Void?): Void? {
        val lvlDao = db.levelDao()
        val userLvlDao = db.userLevelDao()

        val levels = lvlDao.getLevels()
        levels.forEach { lvl ->
            var userLvl = userLvlDao.getULByLevelId(lvl.id)
            if(userLvl == null)
                userLvl = UserLevel()
            userLvl.levelId = lvl.id
            userLvl.minFinishTime = 0
            userLvl.numOfFilledStars = StarsView.MAX_NUM_OF_STARS
            userLvlDao.insertOrUpdate(userLvl)
        }

        return null
    }

}