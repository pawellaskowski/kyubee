package com.pjl.kyubee.model.preparation

import android.arch.lifecycle.MutableLiveData
import com.pjl.kyubee.timer.Timer

class SimpleStrategy(timer: MutableLiveData<Timer>) : PreparationStrategy(timer) {

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