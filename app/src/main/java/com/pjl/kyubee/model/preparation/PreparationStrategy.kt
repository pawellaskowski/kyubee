package com.pjl.kyubee.model.preparation

import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import com.pjl.kyubee.timer.Timer

abstract class PreparationStrategy(protected val timer: MutableLiveData<Timer>) {
    private val handler = Handler()
    private val holdRunnable = HoldRunnable()

    open fun onDownEvent() {
        handler.post(holdRunnable)
    }

    open fun onUpEvent() {
        handler.removeCallbacks(holdRunnable)
    }

    protected inner class HoldRunnable : Runnable {
        override fun run() {
            val preparing = timer.value?.isPreparing() ?: false
            if (preparing) {
                val holdTime = timer.value?.getTime() ?: 0L
                if (holdTime < 1000) {
                    handler.postDelayed(this, 10)
                } else {
                    timer.value = timer.value?.ready()
                }
            }
        }
    }
}