package com.pjl.kyubee.timer

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.pjl.kyubee.KyubeeApp
import com.pjl.kyubee.model.Solve
import com.pjl.kyubee.model.preparation.PreparationStrategy
import com.pjl.kyubee.model.preparation.SimpleStrategy

class TimerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as KyubeeApp).getRepository()
    private val timer: MutableLiveData<Timer> = MutableLiveData()
    private var preparation: PreparationStrategy = SimpleStrategy(timer)

    init {
        timer.value = Timer.RESET_TIMER
    }

    fun getTimer(): LiveData<Timer> = timer

    fun onDownEvent() {
        preparation.onDownEvent()
        timer.value?.let {
            if (it.isStopped()) {
                repository.insert(Solve(it.accumulatedTime))
            }
        }
    }

    fun onUpEvent() {
        preparation.onUpEvent()
    }

}