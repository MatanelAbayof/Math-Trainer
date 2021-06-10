package heb.apps.mathtrainer.memory.db.tasks

import android.content.Context
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.utils.debug.Test

@Test
class TestDBTask(ctx: Context) : MathDBTask<Void, Void>(ctx) {

    override fun doInBack(db: MathDB, input: Void?): Void? {
        return null
    }

}