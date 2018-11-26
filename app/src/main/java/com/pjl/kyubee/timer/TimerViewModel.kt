package com.pjl.kyubee.timer

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.pjl.kyubee.model.Solve
import com.pjl.kyubee.repository.SolveRepository
import com.pjl.kyubee.model.preparation.TimingControlStrategy
import com.pjl.kyubee.utilities.inspectionDuration
import com.pjl.kyubee.utilities.now
import javax.inject.Inject

class TimerViewModel @Inject constructor(
        private val repository: SolveRepository,
        private val controller: TimingControlStrategy
) : ViewModel() {

    fun getTimer(): LiveData<Timer> = controller.timer

    fun remainingInspection(): Long {
        val startTime = getTimer().value?.startTime ?: now()
        return (startTime + inspectionDuration - now()) / 1000
    }

    fun onDownEvent() {
        controller.onDownEvent()
        getTimer().value?.let {
            if (it.isStopped()) {
                repository.insert(Solve(it.accumulatedTime))
            }
        }
    }

    fun onUpEvent() {
        controller.onUpEvent()
    }
}