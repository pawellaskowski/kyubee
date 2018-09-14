package com.pjl.kyubee.model.preparation

import android.arch.lifecycle.MutableLiveData
import com.pjl.kyubee.timer.Timer

class SimpleStrategy(timer: MutableLiveData<Timer>) : PreparationStrategy(timer) {

    override fun onDownEvent() {
        timer.value?.let {
            when (it.state) {
                Timer.State.RUNNING -> timer.value = it.stop()
                else -> timer.value = it.prepare()
            }
        }
        super.onDownEvent()
    }

    override fun onUpEvent() {
        super.onUpEvent()
        timer.value?.let {
            when (it.state) {
                Timer.State.READY -> timer.value = it.start()
                Timer.State.PREPARING -> timer.value = it.reset()
                else -> return
            }
        }
    }
}