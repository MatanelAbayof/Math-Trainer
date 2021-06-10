package heb.apps.mathtrainer.utils.load

class LoadGroup<T : LoadObj<S>, S>(private val elements: List<T> = emptyList())
    : LoadObj<Void> {
    // event when finish to load all elements
    override var onLoadFinish: () -> Unit = {}

    // count num of elements that load
    private var countLoads = 0

    var isLoadFinished: Boolean = false
        private set

    override fun startLoading(v: Void?) {
        handleLoadEvents()
    }

    // handle load events
    private fun handleLoadEvents() {
        if(elements.isEmpty())
            onLoadFinish() // nothing to load

        for (element in elements) {
            element.onLoadFinish = {
                incLoad()
            }
            element.startLoading()
        }
    }

    // increment load count
    private fun incLoad() {
        countLoads++
        // check if all element has been load
        if(countLoads == elements.size) {
            isLoadFinished = true
            onLoadFinish()
        }
    }
}