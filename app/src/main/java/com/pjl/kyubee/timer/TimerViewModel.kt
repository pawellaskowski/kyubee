package com.pjl.kyubee.timer

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.pjl.kyubee.model.SolveRepository

class TimerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SolveRepository(application)
    private val timer: MutableLiveData<Timer> = MutableLiveData()

    init {
        timer.value = Timer.RESET_TIMER
    }

    fun getTimer(): LiveData<Timer> = timer

    fun onTimerClick() {
        if (timer.value?.isRunning() == true) {
            stop()
        } else {
            start()
        }
    }

    private fun start() {
        timer.value = timer.value?.start()
    }

    private fun stop() {
        timer.value = timer.value?.stop()
    }
}