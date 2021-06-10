package heb.apps.mathtrainer.memory.db.tasks.levelsxps

import android.content.Context
import heb.apps.mathtrainer.data.quiz.level.Level
import heb.apps.mathtrainer.data.quiz.levelxp.LevelXp
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask
import heb.apps.mathtrainer.ui.widget.PercentageBar


data class UserLevelXp(var earnXp: Int = 0,
                       var currentLvl: Level? = null,
                       var currentLvlXp: LevelXp? = null,
                       var nextLvl: Level? = null,
                       var nextLvlXp: LevelXp? = null) {

    /** check if has next level */
    fun hasNextLevel() : Boolean = (nextLvl != null)

    /** get left XP for next level */
    fun getLeftXpNextLvl() : Int {
        return when(hasNextLevel()) {
            true -> nextLvlXp!!.xp - earnXp
            false -> 0
        }
    }

    /** get left XP for next level in percents */
    fun getLeftXpNextLvlPercents() : Int {
        if(!hasNextLevel())
            return PercentageBar.MAX_PERCENTAGE
        val relMaxXp = nextLvlXp!!.xp - currentLvlXp!!.xp
        val relEarnXp = earnXp - currentLvlXp!!.xp
        return ((PercentageBar.MAX_PERCENTAGE*relEarnXp).toFloat()/relMaxXp).toInt()
    }
}

class GetUserLevelXpTask(ctx: Context) : MathDBTask<Void, UserLevelXp>(ctx) {

    override fun doInBack(db: MathDB, input: Void?): UserLevelXp {
        val levelXpDao = db.levelXpDao()
        val levelDao = db.levelDao()

        val userLevel = UserLevelXp()
        userLevel.earnXp = levelXpDao.calcEarnXp()
        val currentLevelNum = levelXpDao.calcCurrentUserLevelId(userLevel.earnXp)
        userLevel.currentLvl = levelDao.getLevelById(currentLevelNum)
        userLevel.currentLvlXp = levelXpDao.getLevelXp(currentLevelNum)

        val lastLvlXp = levelXpDao.getLastLevelXp()
        val nextLevelNum = when (currentLevelNum < lastLvlXp.id) {
            true ->  currentLevelNum + 1
            false -> null
        }

        nextLevelNum?.let {
            userLevel.nextLvl = levelDao.getLevelById(nextLevelNum)
            userLevel.nextLvlXp = levelXpDao.getLevelXp(nextLevelNum)
        }

        return userLevel
    }

}