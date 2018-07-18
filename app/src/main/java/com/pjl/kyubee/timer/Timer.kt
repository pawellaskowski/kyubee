package com.pjl.kyubee.timer

import android.os.SystemClock

class Timer(val state: State, val startTime: Long, val accumulatedTime: Long) {

    public enum class State { RESET, RUNNING, STOPPED }

    companion object {
        const val UNUSED = Long.MIN_VALUE
        val RESET_TIMER = Timer(State.RESET, UNUSED, 0)
    }

    fun now(): Long = SystemClock.elapsedRealtime()

    fun isRunning() = state == State.RUNNING
    fun isStopped() = state == State.STOPPED
    fun isReset() = state == State.RESET

    fun start(): Timer = Timer(State.RUNNING, now(), 0)

    fun stop(): Timer = Timer(State.STOPPED, startTime, getTime())

    fun reset(): Timer = RESET_TIMER

    fun getTime(): Long = now() - startTime


}