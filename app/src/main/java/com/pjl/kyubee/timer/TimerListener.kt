package com.pjl.kyubee.timer

interface TimerListener {
    fun timerUpdated(before: Timer, after: Timer)
}