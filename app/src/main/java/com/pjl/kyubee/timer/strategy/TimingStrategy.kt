package com.pjl.kyubee.timer.strategy

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.pjl.kyubee.timer.Timer
import com.pjl.kyubee.utilities.holdDuration
import com.pjl.kyubee.utilities.now

abstract class TimingStrategy(protected val timer: MutableLiveData<Timer>) {

    private val handler = Handler()
    private val hold = Hold()
    private var preparationStartTime = Long.MIN_VALUE

    init {
        timer.value = Timer.RESET_TIMER
    }

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