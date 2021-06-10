package heb.apps.mathtrainer.utils.debug

import android.util.Log

class LogManager {

    enum class LogType {
        Error {
            override fun log(tag: String, message: String) { Log.e(tag, message) }
        },
        Info {
            override fun log(tag: String, message: String) { Log.i(tag, message) }
        },
        Warn {
            override fun log(tag: String, message: String) { Log.w(tag, message) }
        };
        abstract fun log(tag: String, message: String)
    }

    companion object {
        var allowDebug: Boolean = true

        // log message
        fun log(logType: LogType, tag: String, message: String) {
            if(allowDebug)
                logType.log(tag, message)
        }
    }
}

