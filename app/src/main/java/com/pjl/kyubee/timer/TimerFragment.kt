package com.pjl.kyubee.timer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pjl.kyubee.R
import kotlinx.android.synthetic.main.fragment_timer.*

class TimerFragment : Fragment() {
    private val timeRunnable = TimeUpdateRunnable()
    private val timerWatcher: TimerListener = TimerWatcher()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        TimerModel.timerListener = timerWatcher
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timer.setOnClickListener(TimerClickListener())
    }

    private fun getTimer() = TimerModel.timer

    private fun stopUpdatingTime() = timer.removeCallbacks(timeRunnable)

    private fun startUpdatingTime() {
        stopUpdatingTime()
        timer.post(timeRunnable)
    }

    private fun updateTime() {
        val currentTimer = getTimer()
        val totalTime = currentTimer.getTime()
        timer.text = totalTime.toString()
    }

    private inner class TimeUpdateRunnable() : Runnable {
        override fun run() {
            val currentTimer = getTimer()
            updateTime()
            if (currentTimer.isRunning()) {
                timer.postDelayed(this, 1)
            }
        }
    }

    private inner class TimerWatcher : TimerListener {
        override fun timerUpdated(before: Timer, after: Timer) {
            when {
                after.isRunning() -> startUpdatingTime()
                after.isStopped() -> stopUpdatingTime()
            }
        }
    }

    private inner class TimerClickListener : View.OnClickListener {
        override fun onClick(v: View) {
            if (getTimer().isRunning()) {
                TimerModel.stopTimer()
            } else {
                TimerModel.startTimer()
            }
        }
    }
}

