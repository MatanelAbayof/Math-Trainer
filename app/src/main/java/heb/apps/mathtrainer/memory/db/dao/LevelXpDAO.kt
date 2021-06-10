package heb.apps.mathtrainer.memory.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import heb.apps.mathtrainer.data.quiz.level.Level
import heb.apps.mathtrainer.data.quiz.level.UserLevel
import heb.apps.mathtrainer.data.quiz.levelxp.LevelXp

@Dao
interface LevelXpDAO : DAOBase<LevelXp> {

    @Query("SELECT * FROM ${LevelXp.TABLE_NAME}")
    fun getLevelsXps(): List<LevelXp>

    /** calculate XP that user earn */
    @Query("SELECT sum(earnXp) FROM ( " +
            "SELECT *, xpPerStar*numOfFilledStars AS earnXp FROM ${UserLevel.TABLE_NAME} AS ul " +
            "INNER JOIN ${Level.TABLE_NAME} AS l " +
            "ON ul.levelId = l.id)")
    fun calcEarnXp() : Int

    /** get current level id of user */
    @Query("SELECT (min(id) - 1) AS id FROM ${LevelXp.TABLE_NAME} WHERE xp > :earnXp")
    fun calcCurrentUserLevelId(earnXp: Int) : Int

    /** get last level xp */
    @Query("SELECT * FROM ${LevelXp.TABLE_NAME} ORDER BY id DESC LIMIT 1") // "ORDER BY id DESC LIMIT 1" = select maximum id
    fun getLastLevelXp() : LevelXp

    /** get level xp by id */
    @Query("SELECT * FROM ${LevelXp.TABLE_NAME} WHERE id = :id LIMIT 1")
    fun getLevelXp(id: Int) : LevelXp

    @Transaction
    fun insertOrUpdate(levelXp: LevelXp) {
        if (insert(levelXp) == DAOBase.FAIL_INSERT_FLAG)
            update(levelXp)
    }

    @Transaction
    fun insertOrUpdate(levelsXps: List<LevelXp>) {
        levelsXps.forEach { insertOrUpdate(it) }
    }
}