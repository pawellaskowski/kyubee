package com.pjl.kyubee.timer.strategy

import androidx.lifecycle.MutableLiveData
import com.pjl.kyubee.timer.Timer

class SimpleStrategy(timer: Timer) : TimingStrategy(timer) {

    override fun onDownEvent() {
        timer = when {
            timer.isRunning() -> timer.stop()
            else -> timer.prepare()
        }
        super.onDownEvent()
    }

    override fun onUpEvent() {
        super.onUpEvent()
        timer = when {
            timer.isReady() -> timer.start()
            timer.isPreparing() -> timer.reset()
            else -> timer
        }
    }
}