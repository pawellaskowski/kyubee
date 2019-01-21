package com.pjl.kyubee.timer.strategy

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.pjl.kyubee.timer.Timer
import com.pjl.kyubee.utilities.holdDuration
import com.pjl.kyubee.utilities.now

abstract class TimingStrategy(protected var timer: Timer) {

    private val handler = Handler()
    private val hold = Hold()
    private var preparationStartTime = Long.MIN_VALUE

    open fun onDownEvent() {
        handler.post(hold)
        preparationStartTime = now()
    }

    open fun onUpEvent() {
        handler.removeCallbacks(hold)
    }

    protected inner class Hold : Runnable {
        override fun run() {
            if (timer.isPreparing()) {
                val holdTime = now() - preparationStartTime
                if (holdTime < holdDuration) {
                    handler.postDelayed(this, 10)
                } else {
                    timer = timer.ready()
                }
            }
        }
    }
}