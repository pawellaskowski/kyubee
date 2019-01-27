package com.pjl.kyubee

import com.pjl.kyubee.timer.Timer
import com.pjl.kyubee.timer.strategy.TimingStrategyFactory
import com.pjl.kyubee.utilities.inspectionDuration
import com.pjl.kyubee.utilities.now
import javax.inject.Inject

class TimerInteractor @Inject constructor(
        private val solveUseCase: SolveUseCase,
        strategyFactory: TimingStrategyFactory
) : TimerUseCase {

    private var timer = Timer.RESET_TIMER
    private val timingController = strategyFactory.create(timer)

    override fun onDownEvent(): Timer {
        timer = timingController.onDownEvent()
//        if (timer.state == Timer.State.STOPPED) {
//            solveUseCase.save(timer)
//        }
        return timer
    }

    override fun onUpEvent(): Timer {
        timer = timingController.onUpEvent()
        return timer
    }

    override fun remainingInspection(): Long {
        val startTime = timer.startTime
        return (startTime + inspectionDuration - now()) / 1000
    }
}