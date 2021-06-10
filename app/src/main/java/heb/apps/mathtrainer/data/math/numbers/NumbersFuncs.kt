package heb.apps.mathtrainer.data.math.numbers

// check if number is natural
fun Int.isNatural(withZero: Boolean = true) : Boolean = if(withZero) this >= 0 else this > 0
fun Long.isNatural(withZero: Boolean = true) : Boolean = if(withZero) this >= 0 else this > 0