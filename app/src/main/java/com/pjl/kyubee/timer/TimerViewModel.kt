package com.pjl.kyubee.timer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pjl.kyubee.model.Solve
import com.pjl.kyubee.repository.SolveRepository
import com.pjl.kyubee.timer.strategy.TimingStrategyFactory
import com.pjl.kyubee.utilities.inspectionDuration
import com.pjl.kyubee.utilities.ioThread
import com.pjl.kyubee.utilities.now
import javax.inject.Inject

class TimerViewModel @Inject constructor(
        private val repository: SolveRepository,
        private val strategyFactory: TimingStrategyFactory
) : ViewModel() {

    private val _timer: MutableLiveData<Timer> = MutableLiveData()
    val timer: LiveData<Timer>
        get() = _timer
    private val controller = strategyFactory.createWith(_timer)

    init {
        _timer.value = Timer.RESET_TIMER
    }

    fun remainingInspection(): Long {
        val startTime = _timer.value?.startTime ?: now()
        return (startTime + inspectionDuration - now()) / 1000
    }

    fun onDownEvent() {
        controller.onDownEvent()
        _timer.value?.let {
            if (it.isStopped()) {
                ioThread {
                    repository.insert(Solve(it.accumulatedTime))
                }
            }
        }
    }

    fun onUpEvent() {
        controller.onUpEvent()
    }
}