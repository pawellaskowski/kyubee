package com.pjl.kyubee.timer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pjl.kyubee.CategoryUseCase
import com.pjl.kyubee.ScrambleUseCase
import com.pjl.kyubee.TimerUseCase
import com.pjl.kyubee.model.Category
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
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class TimerViewModel @Inject constructor(
        private val timerUseCase: TimerUseCase,
        private val scrambleUseCase: ScrambleUseCase,
        categoryUseCase: CategoryUseCase
) : BaseViewModel(
    categoryUseCase
) {

    private val _timer: MutableLiveData<Timer> = MutableLiveData()
    val timer: LiveData<Timer>
        get() = _timer

    private val _scramble: MutableLiveData<String> = MutableLiveData()
    val scramble: LiveData<String>
        get() = _scramble

    init {
        scrambleUseCase.getScrambleObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    _scramble.value = it;
                }
                .addTo(compositeDisposable)
    }

    fun remainingInspection() = timerUseCase.remainingInspection()

    fun onDownEvent() {
        timerUseCase.onDownEvent()
    }

    fun onUpEvent() {
        timerUseCase.onUpEvent()
    }
}