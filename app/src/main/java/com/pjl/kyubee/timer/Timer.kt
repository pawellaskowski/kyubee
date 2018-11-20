package com.pjl.kyubee.timer

import com.pjl.kyubee.utilities.now

class Timer(val state: State, val startTime: Long, val accumulatedTime: Long = 0) {

    enum class State { RESET, INSPECTING, PREPARING, READY, RUNNING, STOPPED }

    fun isInspecting() = state == State.INSPECTING
    fun isPreparing() = state == State.PREPARING
    fun isReady() = state == State.READY
    fun isRunning() = state == State.RUNNING
    fun isStopped() = state == State.STOPPED
    fun isReset() = state == State.RESET

    fun inspect() = if (isPreparing()) {
        Timer(State.INSPECTING, startTime)
    } else {
        Timer(State.INSPECTING, now())
    }

    fun prepare() = Timer(State.PREPARING, startTime)

    fun ready() = Timer(State.READY, startTime)

    fun start() = Timer(State.RUNNING, now())

    fun stop() = Timer(State.STOPPED, startTime, getTime())

    fun reset() = RESET_TIMER

    fun getTime() = now() - startTime

    companion object {
        private const val UNUSED = Long.MIN_VALUE
        val RESET_TIMER = Timer(State.RESET, UNUSED)
    }
}