package heb.apps.mathtrainer.memory.db.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface DAOBase<T> {

    // insert new item. return FAIL_INSERT_FLAG if already exists when onConflict = OnConflictStrategy.IGNORE
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(t: T) : Long

    // update new item
    @Update
    fun update(vararg items: T)

    // TODO find a way to use insertOrUpdate from here

    companion object {
        // flag that return when fail to insert, and have onConflict = OnConflictStrategy.IGNORE
        const val FAIL_INSERT_FLAG = -1L
    }
}