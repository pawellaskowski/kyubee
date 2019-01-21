package com.pjl.kyubee.timer.strategy

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.pjl.kyubee.timer.Timer
import com.pjl.kyubee.utilities.inspectionDuration

class InspectionStrategy(timer: Timer) : TimingStrategy(timer) {

    private val handler = Handler()
    private val inspectionRunnable = InspectionRunnable()

    override fun onDownEvent() {
        timer = when {
            timer.isRunning() -> timer.stop()
            timer.isInspecting() -> timer.prepare()
            else ->  {
                handler.post(inspectionRunnable)
                timer.inspect()
            }
        }

        super.onDownEvent()
    }

    override fun onUpEvent() {
        super.onUpEvent()
        timer = when {
            timer.isReady() -> {
                handler.removeCallbacks(inspectionRunnable)
                timer.start()
            }
            timer.isPreparing() -> timer.inspect()
            else -> timer
        }
    }

    private inner class InspectionRunnable : Runnable {
        override fun run() {
            if (timer.getTime() < inspectionDuration) {
                handler.postDelayed(this, 10)
            } else {
                timer = Timer.RESET_TIMER
            }
        }
    }
}