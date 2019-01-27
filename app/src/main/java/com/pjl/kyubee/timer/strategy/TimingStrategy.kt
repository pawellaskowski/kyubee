package com.pjl.kyubee.timer.strategy

import android.os.Handler
import com.pjl.kyubee.SolveUseCase
import com.pjl.kyubee.timer.Timer
import com.pjl.kyubee.utilities.holdDuration
import com.pjl.kyubee.utilities.now

abstract class TimingStrategy(protected var timer: Timer) {

    private val handler = Handler()
    private val hold = Hold()
    private var preparationStartTime = Long.MIN_VALUE

    protected abstract fun onDown(): Timer

    protected abstract fun onUp(): Timer

    fun onDownEvent(): Timer {
        handler.post(hold)
        preparationStartTime = now()
        return onDown()
    }

    fun onUpEvent(): Timer {
        handler.removeCallbacks(hold)
        return onUp()
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