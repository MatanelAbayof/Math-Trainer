package heb.apps.mathtrainer.memory.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import heb.apps.mathtrainer.data.quiz.level.Level
import heb.apps.mathtrainer.data.quiz.level.UserLevel
import heb.apps.mathtrainer.data.quiz.topic.Topic
import heb.apps.mathtrainer.ui.widget.PercentageBar
import heb.apps.mathtrainer.ui.widget.StarsView

@Dao
interface TopicDAO : DAOBase<Topic> {

    @Query("SELECT * FROM ${Topic.TABLE_NAME} WHERE categoryId = :categoryId " +
                "ORDER BY orderIndex")
    fun getTopicsByCategoryId(categoryId: Int): List<Topic>

    @Query("SELECT * FROM ${Topic.TABLE_NAME} WHERE id = :id")
    fun getTopicById(id: Int): Topic?

    @Transaction
    fun insertOrUpdate(topic: Topic) {
        if (insert(topic) == DAOBase.FAIL_INSERT_FLAG)
            update(topic)
    }

    @Transaction
    fun insertOrUpdate(topics: List<Topic>) {
        topics.forEach { insertOrUpdate(it) }
    }

   /* @Query("SELECT SUM(UL.numOfFilledStars) FROM ${Level.TABLE_NAME} AS L " +
            "INNER JOIN ${UserLevel.TABLE_NAME} AS UL " +
            "ON L.id = UL.levelId " +
            "WHERE L.topicId = :topicId LIMIT 1")
    fun getTopicFilledStars(topicId: Int) : Int

    @Query("SELECT COUNT(id) FROM ${Level.TABLE_NAME} WHERE topicId = :topicId LIMIT 1")
    fun getNumOfLevels(topicId: Int) : Int

    @Transaction
    fun getTopicCompletedPercent(topicId: Int) : Double {
        val maxStars = getNumOfLevels(topicId) * StarsView.MAX_NUM_OF_STARS
        if(maxStars == 0)
            return PercentageBar.MAX_PERCENTAGE.toDouble()
        val filledStars = getTopicFilledStars(topicId)
        return filledStars.toDouble() / maxStars
    }*/

    @Query("SELECT T.id AS topicId, " +
            "SUM(UL.numOfFilledStars) AS numOfFilledStars, " +
            "COUNT(L.topicId) AS numOfOLevels " +
            "FROM ${Topic.TABLE_NAME} AS T " +
            "LEFT JOIN ${Level.TABLE_NAME} AS L " +
            "ON T.id = L.topicId " +
            "LEFT JOIN ${UserLevel.TABLE_NAME} AS UL " +
            "ON L.id = UL.levelId " +
            "WHERE T.categoryId = :categoryId " +
            "GROUP BY T.id")
    fun getTopicsFilledStars(categoryId: Int) : List<TopicFilledStars>

    data class TopicFilledStars(val topicId: Int,
                                val numOfFilledStars: Int,
                                val numOfOLevels: Int) {
        val completePercents: Double
            get() = when(numOfOLevels) {
                0 -> PercentageBar.MAX_PERCENTAGE.toDouble()
                else -> {
                    val maxStars = StarsView.MAX_NUM_OF_STARS*numOfOLevels
                    val relation = numOfFilledStars.toDouble()/maxStars
                    PercentageBar.MAX_PERCENTAGE*relation
                }
            }
    }
}