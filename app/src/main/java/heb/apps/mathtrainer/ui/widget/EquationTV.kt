package heb.apps.mathtrainer.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import android.webkit.WebViewClient
import heb.apps.mathtrainer.R
import io.github.kexanie.library.MathView


class EquationTV(ctx: Context,
                 attrs: AttributeSet? = null)
    : MathView(WebViewCompat.getBrilliantContext(ctx), attrs) {
    var equation: String = ""
    set(value) {
        field = value
        updateText()
    }
    var fontSize: String = DEF_FONT_SIZE
    set(value) {
        field = value
        updateText()
    }

    var onLoadFinished: () -> Unit = {}
    set(value) {
        field = value
        bindLoadFinishedEvent()
    }

    // first time flag for checking load event
    private var firstTimeFlag = true

    init {
        isFocusable = false
        isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false
        setEngine(Engine.KATEX)
        readAttrs(attrs)
    }

    // read attributes
    private fun readAttrs(attrs : AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.EquationTvAttrs,
            0, 0).apply {

            try {
                val equationAttr = getString(R.styleable.EquationTvAttrs_equation)
                if(equationAttr != null)
                    equation = equationAttr
                val fontSizeAttr = getString(R.styleable.EquationTvAttrs_fontSize)
                if(fontSizeAttr != null)
                    fontSize = fontSizeAttr
            } finally {
                recycle()
            }
        }
    }

    // update text (Latex syntax)
    private fun updateText() {
        text = "\\[\\$fontSize{$equation}\\]"
    }

    // bind load finished event
    private fun bindLoadFinishedEvent() {
        webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String?) {
                updateDocGravity()

                // when MathView load first time, this event invoke - so we call to event
                if(firstTimeFlag)
                    firstTimeFlag = false
                else
                    onLoadFinished()
            }

        }
    }

    companion object {
        // default font size
        private const val DEF_FONT_SIZE = "small"
    }
}