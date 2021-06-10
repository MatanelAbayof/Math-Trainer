package heb.apps.mathtrainer.utils.time

import heb.apps.mathtrainer.data.math.numbers.isNatural
import heb.apps.mathtrainer.ui.activities.MathBaseActivity
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.ceil

class StopWatch(private val activity: MathBaseActivity) { // need activity for run events in UI thread

    // active flag
    var active: Boolean = false
        private set
    private var innerTimer: Timer? = null
    private var remainingTicks: Int = 0

    // start
    fun start(delayTime: Long,
              progressUpdateTime: Long,
              onProgressUpdated: (timeLeft: Long) -> Unit = {},
              onFinish: () -> Unit = {}) {

        // check args
        require(delayTime.isNatural()) { "Delay time must be a natural number" }
        require(progressUpdateTime.isNatural(false)) { "Progress update time must be > 0" }
        require(progressUpdateTime <= delayTime) { "Progress update time cannot be bigger " +
                                                         "than delay time" }

        if(active) stop()

        // init timer
        innerTimer = Timer()
        remainingTicks = ceil(delayTime.toDouble()/progressUpdateTime.toDouble()).toInt()
        active = true

        innerTimer!!.schedule(progressUpdateTime, progressUpdateTime) {
            remainingTicks--

            // call progress event
            val remainingTime = remainingTicks*progressUpdateTime
            activity.runOnUiThread { onProgressUpdated(remainingTime) }

            // check if finish
            if(remainingTicks == 0) {
                stop()
                activity.runOnUiThread { onFinish() }
            }
        }
    }

    // stop
    fun stop() {
        if(active) {
            innerTimer!!.apply {
                cancel()
                purge()
            }
            active = false
        }
    }
}