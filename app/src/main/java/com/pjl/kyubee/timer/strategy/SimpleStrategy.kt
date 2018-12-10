package com.pjl.kyubee.timer.strategy

import androidx.lifecycle.MutableLiveData
import com.pjl.kyubee.timer.Timer

class SimpleStrategy(timer: MutableLiveData<Timer>) : TimingStrategy(timer) {

    override fun onDownEvent() {
        timer.value = timer.value?.let {
            when {
                it.isRunning() -> it.stop()
                else -> it.prepare()
            }
        }
        super.onDownEvent()
    }

    override fun onUpEvent() {
        super.onUpEvent()
        timer.value = timer.value?.let {
            when {
                it.isReady() -> it.start()
                it.isPreparing() -> it.reset()
                else -> it
            }
        }
    }
}