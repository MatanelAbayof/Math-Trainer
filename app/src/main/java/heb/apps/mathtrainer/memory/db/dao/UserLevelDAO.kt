package heb.apps.mathtrainer.memory.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import heb.apps.mathtrainer.data.quiz.level.Level
import heb.apps.mathtrainer.data.quiz.level.UserLevel

@Dao
interface UserLevelDAO : DAOBase<UserLevel> {

    /** get user level by level id */
    @Query("SELECT * FROM ${UserLevel.TABLE_NAME} WHERE levelId = :levelId LIMIT 1")
    fun getULByLevelId(levelId: Int) : UserLevel?

    /** get user levels by topic id */
    @Query("SELECT UL.* FROM ${UserLevel.TABLE_NAME} AS UL " +
            "INNER JOIN ${Level.TABLE_NAME} AS L " +
            "ON UL.levelId = L.id " +
            "WHERE L.topicId = :topicId")
    fun getULByTopicId(topicId: Int) : List<UserLevel>

    @Transaction
    fun insertOrUpdate(userLevel: UserLevel) {
        if (insert(userLevel) == DAOBase.FAIL_INSERT_FLAG)
            update(userLevel)
    }

    /** delete all rows from table */
    @Query("DELETE FROM ${UserLevel.TABLE_NAME}")
    fun deleteAll()

}