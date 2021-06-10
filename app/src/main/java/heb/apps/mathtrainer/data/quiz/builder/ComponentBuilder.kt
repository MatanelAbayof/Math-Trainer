package heb.apps.mathtrainer.data.quiz.builder

import android.content.Context
import heb.apps.mathtrainer.utils.language.Language

abstract class ComponentBuilder<ResType>(protected val ctx: Context) {
    // file name of component
    abstract val fileName: String

    // map of builders by languages
    private val map = mutableMapOf<Language,() -> ResType>()

    // register to map
    open fun register(lang: Language, resFunc: () -> ResType) {
        require(!map.containsKey(lang)) {
            "Map in ComponentBuilder already contains " +
                    "language=$lang"
        }
        map[lang] = resFunc
    }

    // build component
    fun buildComponentBox(lang: Language) : ComponentBox<ResType> {
        require(map.containsKey(lang)) { "Map in ComponentBuilder not contains language=$lang" }
        return ComponentBox(lang, map[lang]!!(), fileName)
    }

    // build all components
    fun buildAll() : List<ComponentBox<ResType>> {
        val compsBoxes = mutableListOf<ComponentBox<ResType>>()
        for (pair in map) {
            val componentBox = ComponentBox(pair.key, pair.value(), fileName)
            compsBoxes.add(componentBox)
        }
        return compsBoxes
    }

    data class ComponentBox<T>(val language: Language, val value: T, val fileName: String)
}