package heb.apps.mathtrainer.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.utils.view.LayoutInflateHelper


class PercentageBar(ctx: Context, attrs: AttributeSet? = null) : RelativeLayout(ctx, attrs) { // TODO add 2 borders: to background rect and to filled rect
    var percentage: Int = MIN_PERCENTAGE
    set(value) {
        require (value in MIN_PERCENTAGE..MAX_PERCENTAGE) {
            "Percentage value must be between $MIN_PERCENTAGE to $MAX_PERCENTAGE"
        }

        field = value
        updateViews()
    }
    var showText: Boolean = true
    set(value) {
        field = value
        updateTextVisibility()
    }
    private lateinit var layoutHolder: LayoutHolder
    private val filledRectPaint = Paint()
    private val backRectPaint = Paint()
    private val filledRect = Rect()
    private val backRect = Rect()

    init {
        buildViews()
        readAttrs(attrs)
        updateViews()
        setWillNotDraw(false) // allow draw calls
    }

    // read attributes
    private fun readAttrs(attrs : AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PercentageBarAttrs,
            0, 0).apply {

            try {
                percentage = getInteger(R.styleable.PercentageBarAttrs_percentage, MIN_PERCENTAGE)
                showText = getBoolean(R.styleable.PercentageBarAttrs_showText, true)
            } finally {
                recycle()
            }
        }
    }

    // build views
    private fun buildViews() {
        val view = LayoutInflateHelper.inflate(context, R.layout.percentage_bar)
        val lp = LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.addRule(CENTER_IN_PARENT, TRUE)
        addView(view, lp)
        layoutHolder = LayoutHolder(view)

        // init paints
        backRectPaint.color = Color.GRAY
        backRectPaint.style = Paint.Style.FILL
        filledRectPaint.color = Color.GREEN
        filledRectPaint.style = Paint.Style.FILL
    }

    // update views
    private fun updateViews() {
        updatePercentageViews()
        updateBackRectSize(width, height)
        updateFilledRectSize(width, height)
        updateTextVisibility()
        invalidate()
    }

    // update percentage views
    private fun updatePercentageViews() {
        // update text
        layoutHolder.tvPercentage.text = String.format(
            context.getString(R.string.percentage_display_text), percentage)
        // update color
        filledRectPaint.color = percentageToColor(percentage)
    }

    // convert percentage to color
    private fun percentageToColor(p: Int) : Int {
        val green= (p*0xFF/ MAX_PERCENTAGE.toFloat()).toInt()
        val red  = 0xFF - green
        return Color.rgb(red, green,0)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawRect(backRect, backRectPaint)
            drawRect(filledRect, filledRectPaint)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        updateBackRectSize(w, h)
        updateFilledRectSize(w, h)
    }

    // update back rectangle
    private fun updateBackRectSize(w: Int, h: Int) {
        backRect.top = 0
        backRect.left = 0
        backRect.right = w
        backRect.bottom = h
    }

    // update text visibility
    private fun updateTextVisibility() {
        when(showText) {
            true -> layoutHolder.tvPercentage.visibility = View.VISIBLE
            false -> layoutHolder.tvPercentage.visibility = View.GONE
        }
    }

    // update filled rectangle
    private fun updateFilledRectSize(w: Int, h: Int) {
        filledRect.top = 0
        filledRect.left = 0
        filledRect.right = (w*percentage/MAX_PERCENTAGE.toFloat()).toInt()
        filledRect.bottom = h
    }

    private class LayoutHolder(mainView: View) {
        val tvPercentage: TextView = mainView.findViewById(R.id.textView_percentage)
    }

    companion object {
        private const val LOG_TAG = "PercentageBar"
        // minimum percentage
        const val MIN_PERCENTAGE = 0
        // maximum percentage
        const val MAX_PERCENTAGE = 100
    }
}