package heb.apps.mathtrainer.memory.db.tasks

import android.content.Context
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.utils.debug.TestsManager

abstract class MathDBTask<In, Out>(appCtx: Context): DBTask<In, Out>(appCtx)  {
    private val db: MathDB
    get() = MathDB.instance(ctx)

    // background progress
    override fun doInBackground(vararg args: In?): Out? {
        val input: In? = if (args.isEmpty()) null else args[0]

        TestsManager.run {
            if(TestsManager.ENABLE_SLOW_LOADING)
                Thread.sleep(TestsManager.SLOW_LOADING_DB_TIME)
        }

        return doInBack(db, input)
    }

    // background progress of children
    abstract fun doInBack(db: MathDB, input: In? = null) : Out?
}