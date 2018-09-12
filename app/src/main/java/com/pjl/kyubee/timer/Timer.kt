package com.pjl.kyubee.timer

import android.os.SystemClock

class Timer(val state: State, val startTime: Long, val accumulatedTime: Long) {
    enum class State { RESET, PREPARING, READY, RUNNING, STOPPED }

    companion object {
        const val UNUSED = Long.MIN_VALUE
        val RESET_TIMER = Timer(State.RESET, UNUSED, 0)
    }

    fun now(): Long = SystemClock.elapsedRealtime()

    fun isPreparing() = state == State.PREPARING
    fun isReady() = state == State.READY
    fun isRunning() = state == State.RUNNING
    fun isStopped() = state == State.STOPPED
    fun isReset() = state == State.RESET

    fun prepare(): Timer = Timer(State.PREPARING, now(), 0)

    fun ready(): Timer = Timer(State.READY, UNUSED, 0)

    fun start(): Timer = Timer(State.RUNNING, now(), 0)

    fun stop(): Timer = Timer(State.STOPPED, startTime, getTime())

    fun reset(): Timer = RESET_TIMER

    fun getTime(): Long = now() - startTime
}