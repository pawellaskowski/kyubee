package com.pjl.kyubee.model.preparation

import android.os.Handler
import com.pjl.kyubee.timer.Timer
import com.pjl.kyubee.utilities.inspectionDuration

class InspectionStrategy : TimingControlStrategy() {

    private val handler = Handler()
    private val inspectionRunnable = InspectionRunnable()

    override fun onDownEvent() {
        _timer.value = _timer.value?.let {
            when {
                it.isRunning() -> it.stop()
                it.isInspecting() -> it.prepare()
                else ->  {
                    handler.post(inspectionRunnable)
                    it.inspect()
                }
            }
        }
        super.onDownEvent()
    }

    override fun onUpEvent() {
        super.onUpEvent()
        _timer.value = _timer.value?.let {
            when {
                it.isReady() -> {
                    handler.removeCallbacks(inspectionRunnable)
                    it.start()
                }
                it.isPreparing() -> it.inspect()
                else -> it
            }
        }
    }

    private inner class InspectionRunnable : Runnable {
        override fun run() {
            _timer.value?.let {
                if (it.getTime() < inspectionDuration) {
                    handler.postDelayed(this, 10)
                } else {
                    _timer.value = Timer.RESET_TIMER
                }
            }
        }
    }
}