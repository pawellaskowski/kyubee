package com.pjl.kyubee.model.preparation

import android.arch.lifecycle.MutableLiveData
import com.pjl.kyubee.timer.Timer

class SimpleStrategy(timer: MutableLiveData<Timer>) : PreparationStrategy(timer) {
    override fun onDownEvent() {
        when (timer.value?.state) {
            Timer.State.RUNNING -> {
                timer.value = timer.value?.stop()
            }
            else -> {
                timer.value = timer.value?.prepare()
            }
        }
        super.onDownEvent()
    }

    override fun onUpEvent() {
        super.onUpEvent()
        when (timer.value?.state) {
            Timer.State.READY -> {
                timer.value = timer.value?.start()
            }
            Timer.State.PREPARING -> {
                timer.value = timer.value?.reset()
            }
            else -> return
        }
    }
}