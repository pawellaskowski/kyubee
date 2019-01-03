package com.pjl.kyubee.timer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pjl.kyubee.model.Solve
import com.pjl.kyubee.repository.SolveRepository
import com.pjl.kyubee.settings.SettingsController
import com.pjl.kyubee.timer.strategy.TimingStrategyFactory
import com.pjl.kyubee.utilities.inspectionDuration
import com.pjl.kyubee.utilities.ioThread
import com.pjl.kyubee.utilities.now
import com.pjl.kyubee.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TimerViewModel @Inject constructor(
        private val repository: SolveRepository,
        private val strategyFactory: TimingStrategyFactory,
        settings: SettingsController
) : BaseViewModel(settings) {

    private val _timer: MutableLiveData<Timer> = MutableLiveData()
    val timer: LiveData<Timer>
        get() = _timer
    private val _scramble: MutableLiveData<String> = MutableLiveData()
    val scramble: LiveData<String>
        get() = _scramble
    private val controller = strategyFactory.createWith(_timer)

    init {
        _timer.value = Timer.RESET_TIMER

        categorySubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _currentCategory.value = it
                    _scramble.value = it.scrambler.generateScramble()
                }
                .addTo(disposables)
    }

    fun remainingInspection(): Long {
        val startTime = _timer.value?.startTime ?: now()
        return (startTime + inspectionDuration - now()) / 1000
    }

    fun onDownEvent() {
        controller.onDownEvent()
        _timer.value?.let {
            if (it.isStopped()) {
                ioThread {
                    repository.insert(Solve(it.accumulatedTime, _currentCategory.value?.id))
                }
            }
        }
    }

    fun onUpEvent() {
        controller.onUpEvent()
    }
}