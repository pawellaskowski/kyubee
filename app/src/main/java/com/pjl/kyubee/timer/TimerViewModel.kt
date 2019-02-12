package com.pjl.kyubee.timer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pjl.kyubee.common.DataHolder
import com.pjl.kyubee.common.Status
import com.pjl.kyubee.entity.Category
import com.pjl.kyubee.common.ioThread
import com.pjl.kyubee.usecase.CategoryUseCase
import com.pjl.kyubee.usecase.ScrambleUseCase
import com.pjl.kyubee.usecase.SolveUseCase
import com.pjl.kyubee.usecase.TimerUseCase
import com.pjl.kyubee.viewmodel.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TimerViewModel @Inject constructor(
        private val timerUseCase: TimerUseCase,
        private val scrambleUseCase: ScrambleUseCase,
        private val solveUseCase: SolveUseCase,
        categoryUseCase: CategoryUseCase
) : BaseViewModel(categoryUseCase) {

    private val _timer: MutableLiveData<Timer> = MutableLiveData()
    val timer: LiveData<Timer>
        get() = _timer

    private val _scramble: MutableLiveData<DataHolder<String>> = MutableLiveData()
    val scramble: LiveData<DataHolder<String>>
        get() = _scramble

    init {
        scrambleUseCase.getScrambleObservable()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _scramble.postValue(DataHolder(Status.SUCCESS, it))
                }, {
                    _scramble.postValue(DataHolder(Status.ERROR, null))
                })
                .addTo(compositeDisposable)
    }

    fun remainingInspection() = timerUseCase.remainingInspection()

    fun onDownEvent() {
        val current = timerUseCase.onDownEvent()
        _timer.value = current
        if (current.isStopped()) {
            ioThread {
                scramble()
                solveUseCase.save(current.accumulatedTime, categoryUseCase.getCurrentCategory().id)
            }
        }
    }

    fun onUpEvent() {
        _timer.value = timerUseCase.onUpEvent()
    }

    fun scramble() {
        _scramble.postValue(DataHolder(Status.LOADING, null))
        scrambleUseCase.scramble()
    }

    override fun onCategoryChanged(category: Category) {
        scramble()
    }
}