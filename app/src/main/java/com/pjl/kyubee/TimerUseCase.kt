package com.pjl.kyubee

interface TimerUseCase {

    fun onDownEvent()

    fun onUpEvent()

    fun remainingInspection(): Long
}