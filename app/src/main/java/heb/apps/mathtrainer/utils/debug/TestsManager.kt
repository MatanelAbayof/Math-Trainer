package heb.apps.mathtrainer.utils.debug

annotation class Test

object TestsManager {

    const val IS_TEST_MODE = false // Note: in production, set this to false

    const val ENABLE_ENTER_LOCKED_LEVELS = true

    /** enable slow loading info from DB */
    const val ENABLE_SLOW_LOADING = false

    /** enable infinity time for each question in quiz */
    const val ENABLE_INF_TIME = false

    /** slow loading time at DB task in millis */
    const val SLOW_LOADING_DB_TIME = 1000L

    /** run in test only */
    inline fun run(task: () -> Unit) {
        if(IS_TEST_MODE)
            task()
    }

    /** infinity value */
    const val INF_VAL: Long = 1000000000L
}