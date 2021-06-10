package heb.apps.mathtrainer.utils.collection

import heb.apps.mathtrainer.data.math.numbers.IntHelper

/* iterate over the list with following 2 elements.
   when yield first element - the previous element will be null */
inline fun <E> List<E>.forEachFollow(onGetEl: (prevE: E?, currEl: E) -> Unit) {
    for(i in 0 until size) {
        // if it's first element
        if(i == 0)
            onGetEl(null, first())
        else
            onGetEl(get(i-1), get(i))
    }
}

// multiplication list values
fun List<Int>.mult() : Int = fold(IntHelper.MULTIPLICATION_IDENTITY_ELEMENT) { a, b -> a*b }

