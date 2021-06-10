package heb.apps.mathtrainer.memory.db.tasks

import android.content.Context
import android.util.Log
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.data.quiz.category.Category
import heb.apps.mathtrainer.data.quiz.level.Level
import heb.apps.mathtrainer.data.quiz.levelxp.LevelXp
import heb.apps.mathtrainer.data.quiz.topic.Topic
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.utils.json.JSONHelper

class UpdateResourcesTask(ctx: Context) : MathDBTask<Void, Void>(ctx) {

    override fun doInBack(db: MathDB, input: Void?): Void? {
        initTables(ctx, db)
        return null
    }

    /** init tables */
    private fun initTables(ctx: Context, db: MathDB) {
        initCategories(ctx, db)
        initTopics(ctx, db)
        initLevels(ctx, db)
        initLevelsXps(ctx, db)
    }

    /** init categories */
    private fun initCategories(ctx: Context, db: MathDB) {
        val categoriesJSON = JSONHelper.readJSONObj(ctx, R.raw.categories)
        val categories = Category.parseList(categoriesJSON)
        db.categoryDao().insertOrUpdate(categories)
    }

    /** init topics */
    private fun initTopics(ctx: Context, db: MathDB) {
        val topicsJSON = JSONHelper.readJSONObj(ctx, R.raw.topics)
        val topics = Topic.parseList(topicsJSON)
        db.topicDao().insertOrUpdate(topics)
    }

    /** init levels */
    private fun initLevels(ctx: Context, db: MathDB) {
        val levelsJSON = JSONHelper.readJSONObj(ctx, R.raw.levels)
        val levels = Level.parseList(levelsJSON)
        db.levelDao().insertOrUpdate(levels)
    }

    /** init levels xps */
    private fun initLevelsXps(ctx: Context, db: MathDB) {
        val levelsXpsJSON = JSONHelper.readJSONObj(ctx, R.raw.levels_xps)
        val levelsXps = LevelXp.parseList(levelsXpsJSON)
        db.levelXpDao().insertOrUpdate(levelsXps)
    }

    companion object {
        private const val LOG_TAG = "UpdateResourcesTask"
    }
}