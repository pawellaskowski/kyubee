package com.pjl.kyubee.model.preparation

import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import com.pjl.kyubee.timer.Timer
import com.pjl.kyubee.utilities.holdDuration
import com.pjl.kyubee.utilities.now

abstract class PreparationStrategy(protected val timer: MutableLiveData<Timer>) {

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
            timer.value?.let {
                if (it.isPreparing()) {
                    val holdTime = now() - preparationStartTime
                    if (holdTime < holdDuration) {
                        handler.postDelayed(this, 10)
                    } else {
                        timer.value = it.ready()
                    }
                }
            }

        }
    }
}