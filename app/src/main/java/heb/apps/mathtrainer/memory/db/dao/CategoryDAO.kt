package heb.apps.mathtrainer.memory.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import heb.apps.mathtrainer.data.quiz.category.Category

@Dao
interface CategoryDAO : DAOBase<Category> {

    @Query("SELECT * FROM ${Category.TABLE_NAME} ORDER BY orderIndex")
    fun getAll(): List<Category>

    @Query("SELECT * FROM ${Category.TABLE_NAME} WHERE uniqueName=:uniqueName LIMIT 1")
    fun getCategoryByName(uniqueName: String) : Category?

    @Query("SELECT * FROM ${Category.TABLE_NAME} WHERE id=:id LIMIT 1")
    fun getCategoryById(id: Int) : Category?

    @Query("DELETE FROM ${Category.TABLE_NAME} WHERE id=:id")
    fun deleteCategoryById(id: Int)

    @Transaction
    fun insertOrUpdate(category: Category) {
        if(insert(category) == DAOBase.FAIL_INSERT_FLAG)
            update(category)
    }

    @Transaction
    fun insertOrUpdate(categories: List<Category>) {
        categories.forEach { insertOrUpdate(it) }
    }
}