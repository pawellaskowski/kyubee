package com.pjl.kyubee.timer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pjl.kyubee.CategoryUseCase
import com.pjl.kyubee.ScrambleUseCase
import com.pjl.kyubee.TimerUseCase
import com.pjl.kyubee.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TimerViewModel @Inject constructor(
        private val timerUseCase: TimerUseCase,
        scrambleUseCase: ScrambleUseCase,
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
        _timer.value = timerUseCase.onDownEvent()
    }

    fun onUpEvent() {
        _timer.value = timerUseCase.onUpEvent()
    }
}