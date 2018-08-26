package com.pjl.kyubee.timer

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.pjl.kyubee.R
import kotlinx.android.synthetic.main.fragment_timer.*
import kotlinx.android.synthetic.main.fragment_timer.view.*


class TimerFragment : Fragment() {
    private lateinit var viewModel: TimerViewModel
    private val timeRunnable = TimeUpdateRunnable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_timer, container, false)
        view.timer.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> viewModel.onDownEvent()
                MotionEvent.ACTION_UP -> viewModel.onUpEvent()
            }
            true
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TimerViewModel::class.java)
        viewModel.getTimer().observe(this, Observer {
            if (it != null) {
                Log.d("viewmodel", "state = ${it.state}")
                when {
                    it.isRunning() -> startUpdatingTime()
                    it.isStopped() -> stopUpdatingTime()
                }
            }
        })
    }

    private inner class TimeUpdateRunnable : Runnable {
        override fun run() {
            if (isResumed) {
                val currentTimer = viewModel.getTimer().value
                timer.text = currentTimer?.getTime().toString()
                if (currentTimer?.isRunning() == true) {
                    timer.postDelayed(this, 1)
                }
            }
        }
    }

    private fun stopUpdatingTime() {
        timer.removeCallbacks(timeRunnable)
        timer.text = viewModel.getTimer().value?.accumulatedTime.toString()
    }

    private fun startUpdatingTime() {
        stopUpdatingTime()
        timer.post(timeRunnable)
    }
}