package heb.apps.mathtrainer.ui.activities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.data.quiz.builder.QuizJSONBuilder


class DbBuilderActivity : MathBaseActivity() { // Note: this activity is using only for tests

    private lateinit var layoutHolder: LayoutHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityView(R.layout.activity_db_builder)
        layoutHolder = LayoutHolder()

        QuizJSONBuilder.prepareAllJSONs(applicationContext)


        // TODO check if: QuizJSONBuilder.jsonsMap.isEmpty()

        addCreateBts()
        handleCreateBtsEvents()
    }

    // add create buttons
    private fun addCreateBts() {
        val names = QuizJSONBuilder.jsonsMap.map { it.key }
        layoutHolder.lvCreateBts.adapter = ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, names)
    }

    // handle create buttons events
    private fun handleCreateBtsEvents() {
        layoutHolder.lvCreateBts.setOnItemClickListener { _, _ , pos, _ ->
            val name = layoutHolder.lvCreateBts.getItemAtPosition(pos) as String
            val jsonFunc =  QuizJSONBuilder.jsonsMap[name]
            val jsonStr = jsonFunc!!().toString()
            copyTextToClipboard(jsonStr)
            // show success message
            Toast.makeText(this@DbBuilderActivity, R.string.copied_to_clipboard_msg,
                           Toast.LENGTH_SHORT).show()
        }
    }

    // copy text to clipboard
    private fun copyTextToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(null, text) // TODO set 'label' param
        clipboard.setPrimaryClip(clip)
    }

    private inner class LayoutHolder {
        val lvCreateBts: ListView = findViewById(R.id.listView_createBts)
    }
}
