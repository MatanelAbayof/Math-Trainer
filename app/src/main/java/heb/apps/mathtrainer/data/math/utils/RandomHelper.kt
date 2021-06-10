package heb.apps.mathtrainer.data.math.utils

import heb.apps.mathtrainer.data.math.numbers.isNatural
import kotlin.random.Random

object RandomHelper {

    private val DEFAULT_NATURAL_RANGE = IntegerRange(0, Int.MAX_VALUE)

    // random integer
    fun next(range: IntegerRange = DEFAULT_NATURAL_RANGE) : Int
            = Random.nextInt(range.min, range.max)

    // next integers
    fun nextArr(size: Int, range: IntegerRange = DEFAULT_NATURAL_RANGE) : List<Int> {
        require(size.isNatural()) {
            "Size must be equals or bigger than 0"
        }
        val randValues = mutableListOf<Int>()
        repeat(size) {
            randValues.add(next(range))
        }
        return randValues
    }
}