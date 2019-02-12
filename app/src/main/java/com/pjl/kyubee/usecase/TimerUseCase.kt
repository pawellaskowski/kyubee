package com.pjl.kyubee.usecase

import com.pjl.kyubee.timer.Timer

interface TimerUseCase {

    fun onDownEvent(): Timer

    fun onUpEvent(): Timer

    fun remainingInspection(): Long
}