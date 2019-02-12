package com.pjl.kyubee.timer

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.pjl.kyubee.R
import com.pjl.kyubee.common.Status
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_timer.*
import kotlinx.android.synthetic.main.fragment_timer.view.*
import javax.inject.Inject


class TimerFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: TimerViewModel
    private val timeUpdate = TimeUpdate()
    private val inspectionUpdate = InspectionUpdate()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_timer, container, false)
        view.container.setOnTouchListener { _, event ->
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
        viewModel = ViewModelProviders
                .of(requireActivity(), viewModelFactory)
                .get(TimerViewModel::class.java)
        subscribeUi()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    private fun subscribeUi() {
        viewModel.timer.observe(this, Observer {
            indicateNotReady()
            when {
                it.isRunning() -> {
                    stopUpdatingInspection()
                    startUpdatingTime()
                }
                it.isStopped() -> stopUpdates()
                it.isReady() -> indicateReady()
                it.isInspecting() -> startUpdatingInspection()
                it.isReset() -> stopUpdates()
            }
        })

        viewModel.scramble.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progress.visibility = View.GONE
                    scramble.visibility = View.VISIBLE
                    scramble.text = it.data
                }
                Status.LOADING -> {
                    scramble.visibility = View.GONE
                    progress.visibility =  View.VISIBLE

                }
                Status.ERROR -> {
                    progress.visibility = View.GONE
                    scramble.visibility = View.VISIBLE
                    scramble.text = getString(R.string.scramble_not_generated)
                }
            }
        })
    }

    private fun stopUpdates() {
        stopUpdatingInspection()
        stopUpdatingTime()
    }

    private fun stopUpdatingTime() {
        timer.removeCallbacks(timeUpdate)
        timer.text = viewModel.timer.value?.accumulatedTime.toString()
    }

    private fun stopUpdatingInspection() {
        timer.removeCallbacks(inspectionUpdate)
    }

    private fun startUpdatingTime() {
        timer.post(timeUpdate)
    }

    private fun startUpdatingInspection() {
        timer.post(inspectionUpdate)
    }

    private fun indicateReady() {
        Log.d("__read", "ready")
        timer.setTextColor(Color.GREEN)
    }

    private fun indicateNotReady() {
        timer.setTextColor(Color.BLACK);
    }

    private inner class TimeUpdate : Runnable {
        override fun run() {
            if (isResumed) {
                with (timer) {
                    timer.text = viewModel.timer.value?.getTime().toString()
                    timer.postDelayed(this@TimeUpdate, 10)
                }
            }
        }
    }

    private inner class InspectionUpdate : Runnable {
        override fun run() {
            if (isResumed) {
                with (timer) {
                    text = viewModel.remainingInspection().toString()
                    postDelayed(this@InspectionUpdate, 10)
                }

            }
        }
    }
}