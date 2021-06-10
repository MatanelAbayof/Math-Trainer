package heb.apps.mathtrainer.data.math.utils

data class IntegerRange(val min: Int, val max: Int) { // TODO make base class Range<T>??

    init {
        checkLegalRange()
    }

    // check legal range
    private fun checkLegalRange() {
        require(min <= max) {
            "Minimum value (=$min) cannot be bigger than maximum value (=$max)"
        }
    }
}

// check if number at range
infix fun Int.at(range: IntegerRange): Boolean = ((this >= range.min) && (this < range.max))