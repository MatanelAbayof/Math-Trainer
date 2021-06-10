package heb.apps.mathtrainer.memory.db

import android.content.Context
import androidx.room.Database
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import heb.apps.mathtrainer.data.quiz.category.Category
import heb.apps.mathtrainer.data.quiz.level.Level
import heb.apps.mathtrainer.data.quiz.level.UserLevel
import heb.apps.mathtrainer.data.quiz.levelxp.LevelXp
import heb.apps.mathtrainer.data.quiz.topic.Topic
import heb.apps.mathtrainer.memory.db.dao.*


@Database(entities = [Category::class, Topic::class, Level::class, UserLevel::class, LevelXp::class],
          version = MathDB.VERSION, exportSchema = false)
abstract class MathDB : AppDB() {

    abstract fun categoryDao(): CategoryDAO

    abstract fun topicDao(): TopicDAO

    abstract fun levelDao(): LevelDAO

    abstract fun levelXpDao(): LevelXpDAO

    abstract fun userLevelDao(): UserLevelDAO

    override fun onCreate(ctx: Context) { }

    override fun onOpen(ctx: Context) { }

    companion object {
        /** version of DB */
        const val VERSION = 2

        const val LOG_TAG = "MathDB"

        /** name of database */
        private const val DB_NAME = "math_db"

        /** get DB instance */
        fun instance(applicationContext: Context) : MathDB {
            return DBManager.getDB(
                applicationContext,
                MathDB::class.java, DB_NAME, Migrations.migrations)
        }
        private object Migrations { // Note: class that help: 'MathDB_Impl'

            val MIGRATION_1_2: Migration = object : Migration(1, 2) {

                override fun migrate(database: SupportSQLiteDatabase) {
                   database.execSQL("CREATE TABLE IF NOT EXISTS `${LevelXp.TABLE_NAME}` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `xp` INTEGER NOT NULL)")
                }
            }

            val migrations = arrayOf(MIGRATION_1_2)
        }
    }

}