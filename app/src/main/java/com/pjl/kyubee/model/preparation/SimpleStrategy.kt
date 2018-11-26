package com.pjl.kyubee.model.preparation

class SimpleStrategy : TimingControlStrategy() {

    override fun onDownEvent() {
        _timer.value = _timer.value?.let {
            when {
                it.isRunning() -> it.stop()
                else -> it.prepare()
            }
        }
        super.onDownEvent()
    }

    override fun onUpEvent() {
        super.onUpEvent()
        _timer.value = _timer.value?.let {
            when {
                it.isReady() -> it.start()
                it.isPreparing() -> it.reset()
                else -> it
            }
        }
    }
}