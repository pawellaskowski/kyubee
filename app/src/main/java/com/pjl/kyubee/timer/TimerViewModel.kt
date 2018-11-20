package com.pjl.kyubee.timer

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.pjl.kyubee.model.Solve
import com.pjl.kyubee.model.SolveRepository
import com.pjl.kyubee.model.preparation.PreparationStrategy
import com.pjl.kyubee.utilities.inspectionDuration
import com.pjl.kyubee.utilities.now
import javax.inject.Inject

class TimerViewModel @Inject constructor(
        private val repository: SolveRepository,
        private val preparation: PreparationStrategy
) : ViewModel() {

    val timer: LiveData<Timer> = preparation.timer

    fun remainingInspection(): Long {
        val startTime = timer.value?.startTime ?: now()
        return (startTime + inspectionDuration - now()) / 1000
    }

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