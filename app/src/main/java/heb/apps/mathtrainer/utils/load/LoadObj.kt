package heb.apps.mathtrainer.utils.load

interface LoadObj<T> {
    // event that invokes when load finished
    var onLoadFinish: () -> Unit
    // start load object
    fun startLoading(t: T? = null)
}