package heb.apps.mathtrainer.memory.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import heb.apps.mathtrainer.data.quiz.level.Level

@Dao
interface LevelDAO : DAOBase<Level> {

    @Query("SELECT * FROM ${Level.TABLE_NAME} WHERE topicId = :topicId " +
                "ORDER BY difficulty")
    fun getLevelsByTopicId(topicId: Int): List<Level>

    @Query("SELECT * FROM ${Level.TABLE_NAME}")
    fun getLevels(): List<Level>

    /** get level by id */
    @Query("SELECT * FROM ${Level.TABLE_NAME} WHERE id = :levelId LIMIT 1")
    fun getLevelById(levelId: Int): Level?

    /** get next level. if not found, return null */
    @Query("SELECT * FROM ${Level.TABLE_NAME} " +
            "WHERE topicId = :topicId AND difficulty = (:currLevelDiff + 1)")
    fun getNextLevel(currLevelDiff: Int, topicId: Int) : Level?

    @Transaction
    fun getNextLevel(currLevel: Level) : Level?
            = getNextLevel(currLevel.difficulty, currLevel.topicId)

    @Transaction
    fun insertOrUpdate(level: Level) {
        if (insert(level) == DAOBase.FAIL_INSERT_FLAG)
            update(level)
    }

    @Transaction
    fun insertOrUpdate(levels: List<Level>) {
        levels.forEach { insertOrUpdate(it) }
    }
}