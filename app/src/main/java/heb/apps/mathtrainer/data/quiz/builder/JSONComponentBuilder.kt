package heb.apps.mathtrainer.data.quiz.builder

import android.content.Context
import heb.apps.mathtrainer.utils.json.JSONEncodable
import heb.apps.mathtrainer.utils.json.JSONObj
import heb.apps.mathtrainer.utils.language.Language
import org.json.JSONArray

abstract class JSONComponentBuilder<T : JSONEncodable>(ctx: Context)
    : ComponentBuilder<JSONObj>(ctx) {
    // name of JSON object for hold list of T
    abstract val listJsonObjName: String

    override fun register(lang: Language, resFunc: () -> JSONObj) {
        super.register(lang, resFunc)
        registerToQuizJSONBld(lang, resFunc)
    }

    // register to quiz JSON builder
    private fun registerToQuizJSONBld(lang: Language, resFunc: () -> JSONObj) {
        val fullName = listJsonObjName + " - " + lang.getExtension()
        QuizJSONBuilder.jsonsMap[fullName] = resFunc
    }

    // build objects as JSON object
    protected fun buildJSON(objects: List<T>) : JSONObj {
        val jsonArr = JSONArray()

        for (obj in objects)
            jsonArr.put(obj.toJSON())

        val result = JSONObj()
        result.put(listJsonObjName, jsonArr)
        return result
    }
}