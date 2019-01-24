package com.pjl.kyubee.timer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pjl.kyubee.*
import com.pjl.kyubee.model.Category
import com.pjl.kyubee.viewmodel.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TimerViewModel @Inject constructor(
        private val timerUseCase: TimerUseCase,
        private val scrambleUseCase: ScrambleUseCase,
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
                .doOnSubscribe {
                    _scramble.postValue(DataHolder(Status.LOADING, null))
                }
                .subscribe({
                    _scramble.postValue(DataHolder(Status.SUCCESS, it))
                }, {
                    _scramble.postValue(DataHolder(Status.ERROR, null))
                })
                .addTo(compositeDisposable)
    }

    override fun onCurrentCategoryChanged(newCategory: Category) {
        super.onCurrentCategoryChanged(newCategory)
        _scramble.postValue(DataHolder(Status.LOADING, null))
    }

    fun remainingInspection() = timerUseCase.remainingInspection()

    fun onDownEvent() {
        _timer.value = timerUseCase.onDownEvent()
    }

    fun onUpEvent() {
        _timer.value = timerUseCase.onUpEvent()
    }

    fun scramble() {
        _scramble.postValue(DataHolder(Status.LOADING, null))
        scrambleUseCase.scramble()
    }
}