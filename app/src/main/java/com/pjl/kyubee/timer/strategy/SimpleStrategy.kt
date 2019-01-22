package com.pjl.kyubee.timer.strategy

import com.pjl.kyubee.timer.Timer

class SimpleStrategy(timer: Timer) : TimingStrategy(timer) {

    override fun onDown(): Timer {
        timer = when {
            timer.isRunning() -> timer.stop()
            else -> timer.prepare()
        }
        return timer
    }

    override fun onUp(): Timer {
        timer = when {
            timer.isReady() -> timer.start()
            timer.isPreparing() -> timer.reset()
            else -> timer
        }
        return timer
    }
}