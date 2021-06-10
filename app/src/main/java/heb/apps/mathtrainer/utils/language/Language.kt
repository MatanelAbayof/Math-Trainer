package heb.apps.mathtrainer.utils.language

import java.util.*

enum class Language {
    HEBREW {
        override fun getExtension() = "iw"
        override fun getReadingDir(): ReadingDirection = ReadingDirection.RTL
    },
    ENGLISH {
        override fun getExtension() = "en"
        override fun getReadingDir(): ReadingDirection = ReadingDirection.LTR
    };

    // get extension of language
    abstract fun getExtension() : String

    // get reading direction
    abstract fun getReadingDir() : ReadingDirection

    enum class ReadingDirection {
        RTL, LTR
    }

    companion object {

        // get supported device language. default is English
        fun getDeviceLanguage() : Language {
            return when(Locale.getDefault().language) {
                Locale("he").language -> HEBREW
                else -> ENGLISH
            }
        }
    }
}