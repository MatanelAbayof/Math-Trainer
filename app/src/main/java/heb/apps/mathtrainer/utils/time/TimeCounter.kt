package heb.apps.mathtrainer.utils.time

class TimeCounter {
    // check if counter is active
    var isActive: Boolean = false

    // old time left to add
    private var oldTime: Long = 0

    // check if timer paused
    private var isPaused: Boolean = false

    // get time left in millis
    val timeLeft: Long
        get() {
            require(isActive) { "You cannot get time left because TimeCounter is not active" }
            var timeLeft = oldTime
            if(!isPaused)
                timeLeft += System.currentTimeMillis() - startTime!!
            return timeLeft
        }

    // the start time (date in millis)
    private var startTime: Long? = null

    // start counter
    fun start() {
        reset()
        updateStartTime()
    }

    // stop and return time left
    fun stopAndGetTimeLeft() : Long {
        val timeLeft = this.timeLeft
        stop()
        return timeLeft
    }

    // pause counter
    fun pause() {
        require(isActive) { "You cannot pause TimeCounter that not active" }
        oldTime += timeLeft
        isPaused = true
        startTime = null
    }

    // resume counter
    fun resume() {
        require(isActive) { "You cannot resume TimeCounter that not active" }
        require(isPaused) { "You cannot resume TimeCounter that not paused" }
        isPaused = false
        updateStartTime()
    }

    // stop counter
    fun stop() {
        isActive = false
        startTime = null
        oldTime = 0
        isPaused = false
    }

    // reset time
    private fun reset() {
        oldTime = 0
        isPaused = false
        startTime = null
        isActive = true
    }

    // append time
    fun appendTime(time: Long) {
        oldTime += time
    }

    // update start time to now
    private fun updateStartTime() {
        startTime = System.currentTimeMillis()
    }
}