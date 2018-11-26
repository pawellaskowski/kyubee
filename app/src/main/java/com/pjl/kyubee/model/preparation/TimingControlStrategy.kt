package com.pjl.kyubee.model.preparation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.os.Handler
import com.pjl.kyubee.timer.Timer
import com.pjl.kyubee.utilities.holdDuration
import com.pjl.kyubee.utilities.now

abstract class TimingControlStrategy {

    protected val _timer = MutableLiveData<Timer>()
    val timer: LiveData<Timer>
        get() = _timer
    private val handler = Handler()
    private val hold = Hold()
    private var preparationStartTime = Long.MIN_VALUE

    init {
        _timer.value = Timer.RESET_TIMER
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
            _timer.value?.let {
                if (it.isPreparing()) {
                    val holdTime = now() - preparationStartTime
                    if (holdTime < holdDuration) {
                        handler.postDelayed(this, 10)
                    } else {
                        _timer.value = it.ready()
                    }
                }
            }
        }
    }
}