package com.pjl.kyubee.timer

import com.pjl.kyubee.timer.Timer.Companion.RESET_TIMER

object TimerModel {

    var timerListener: TimerListener? = null
    var timer = RESET_TIMER
        set(value) {
            timerListener?.timerUpdated(field, value)
            field = value
        }

    fun stopTimer() {
        timer = timer.stop()
    }

    fun startTimer() {
        timer = timer.start()
    }

}