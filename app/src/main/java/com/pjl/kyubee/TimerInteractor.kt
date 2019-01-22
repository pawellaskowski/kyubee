package com.pjl.kyubee

import com.pjl.kyubee.timer.Timer
import com.pjl.kyubee.timer.strategy.TimingStrategyFactory
import com.pjl.kyubee.utilities.inspectionDuration
import com.pjl.kyubee.utilities.now
import javax.inject.Inject

class TimerInteractor @Inject constructor(
        strategyFactory: TimingStrategyFactory
) : TimerUseCase {

    private val timer = Timer.RESET_TIMER
    private val timingController = strategyFactory.create(timer)

    override fun onDownEvent(): Timer {
        return timingController.onDownEvent()
    }

    override fun onUpEvent(): Timer {
        return timingController.onUpEvent()
    }

    override fun remainingInspection(): Long {
        val startTime = timer.startTime
        return (startTime + inspectionDuration - now()) / 1000
    }
}