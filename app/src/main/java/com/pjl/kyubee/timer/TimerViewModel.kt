package com.pjl.kyubee.timer

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.pjl.kyubee.model.Solve
import com.pjl.kyubee.model.SolveRepository
import com.pjl.kyubee.model.preparation.PreparationStrategy
import com.pjl.kyubee.model.preparation.SimpleStrategy

class TimerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SolveRepository(application)
    private val timer: MutableLiveData<Timer> = MutableLiveData()
    private var preparation: PreparationStrategy = SimpleStrategy(timer)

    init {
        timer.value = Timer.RESET_TIMER
    }

    fun getTimer(): LiveData<Timer> = timer

    fun onDownEvent() {
        preparation.onDownEvent()
        val current = timer.value
        if (current?.isStopped() == true) {
            repository.insert(Solve(current.accumulatedTime))
        }
    }

    fun onUpEvent() {
        preparation.onUpEvent()
    }
}