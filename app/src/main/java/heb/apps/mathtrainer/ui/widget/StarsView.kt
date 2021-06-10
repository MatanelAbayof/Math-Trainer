package heb.apps.mathtrainer.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.data.math.numbers.isNatural
import heb.apps.mathtrainer.utils.ResolutionConverter

class StarsView(ctx: Context, attrs: AttributeSet? = null) : LinearLayout(ctx, attrs) { // TODO: show animation when add new filled star
    var numOfStars = 0
    set(value) {
        require(numOfStars >= 0) { "Number of stars must be a natural number" }
        field = value
        if(field < numOfFilledStars)
            numOfFilledStars = field // already call updateStarsViews()
        else
            updateStarsViews()
    }
    var numOfFilledStars = 0
    set(value) {
        require(value <= numOfStars) { "Number of filled stars must be less" +
                " or equals than number of stars" }
        field = value
        updateStarsViews()
    }
    var starSize: Int = STAR_SIZE
    set(value) {
        require(value.isNatural()) {
            "Star size must be a natural number"
        }
        field = value
        updateStarsSizes()
    }
    private val starsImages = mutableListOf<ImageView>()

    init {
        initViews()
        readAttrs(attrs)
    }

    // read attributes
    private fun readAttrs(attrs : AttributeSet?) {
         context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.StarsViewAttrs,
            0, 0).apply {

            try {
                numOfStars = getInteger(R.styleable.StarsViewAttrs_numOfStars, 0)
                numOfFilledStars = getInteger(R.styleable.StarsViewAttrs_numOfFilledStars, 0)
                starSize = getInteger(R.styleable.StarsViewAttrs_starSize, STAR_SIZE) // TODO better way it's to read dimension
            } finally {
                recycle()
            }
        }
    }

    // init views
    private fun initViews() {
        orientation = HORIZONTAL
    }

    // update stars views
    private fun updateStarsViews() {
        removeAllStarsViews()
        repeat(numOfFilledStars) {
            addStarView(createFilledStarView())
        }
        repeat(numOfStars - numOfFilledStars) {
            addStarView(createEmptyStarView())
        }
    }

    // remove all stars views
    private fun removeAllStarsViews() {
        starsImages.clear()
        removeAllViews()
    }

    // create empty star view
    private fun createEmptyStarView() : ImageView {
        val starIV = createStarIV()
        starIV.setImageResource(R.drawable.empty_star_gray)
        return starIV
    }

    // create filled star view
    private fun createFilledStarView() : ImageView {
        val starIV = createStarIV()
        starIV.setImageResource(R.drawable.filled_star_yellow)
        return starIV
    }

    // add star view
    private fun addStarView(starView: ImageView) {
        val lp = LayoutParams(getStarWidth(), getStarHeight())
        starsImages.add(starView)
        addView(starView, lp)
    }

    // update stars sizes
    private fun updateStarsSizes() {
        for(starImg in starsImages) {
            starImg.layoutParams.width = getStarWidth()
            starImg.layoutParams.height = getStarHeight()
            starImg.requestLayout() // invalidate view
        }
    }

    // get star width in pixels
    private fun getStarWidth(): Int = ResolutionConverter.dipToPx(context, starSize)

    // get star height in pixels
    private fun getStarHeight(): Int = ResolutionConverter.dipToPx(context, starSize)

    // create star image view
    private fun createStarIV() : ImageView = ImageView(context)

    companion object {
        // star size in dp
        private const val STAR_SIZE = 30
        // maximum number of stars
        const val MAX_NUM_OF_STARS = 3
        // minimum filled stars for unlock level
        const val MIN_STARS_FOR_UNLOCK_LEVEL = 2
    }
}