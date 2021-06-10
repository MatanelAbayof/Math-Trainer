package heb.apps.mathtrainer.memory.db.tasks.userlevels

import android.content.Context
import heb.apps.mathtrainer.data.quiz.level.Level
import heb.apps.mathtrainer.data.quiz.level.UserLevel
import heb.apps.mathtrainer.data.quiz.level.UserLevelFullInf
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask

class GetULsFullInfTask(ctx: Context) : MathDBTask<Int, List<UserLevelFullInf>>(ctx) {

    override fun doInBack(db: MathDB, input: Int?): List<UserLevelFullInf>? { // Note: input = topicId
        require(input != null) {
            "Cannot have topic id at input"
        }
        val levels = db.levelDao().getLevelsByTopicId(input)
        val userLevels = db.userLevelDao().getULByTopicId(input)
        return buildULFullInfList(levels, userLevels)
    }

    // build user level full info list
    private fun buildULFullInfList(levels: List<Level>, userLevels: List<UserLevel>)
            : List<UserLevelFullInf> {
        val ulFullInfList = mutableListOf<UserLevelFullInf>()

        levels.forEach { level ->
            val userLevel = userLevels.find { it.levelId == level.id } // try find user level
            val ulFullInf = UserLevelFullInf(level, userLevel)
            ulFullInfList.add(ulFullInf)
        }

        return ulFullInfList
    }

}