package com.pjl.kyubee.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pjl.kyubee.CategoryUseCase
import com.pjl.kyubee.model.Category
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel(protected val categoryUseCase: CategoryUseCase) : ViewModel() {

    val currentCategory: LiveData<Category>
        get() = _currentCategory
    protected val _currentCategory = MutableLiveData<Category>()

    protected val compositeDisposable = CompositeDisposable()

    init {
        categoryUseCase.getCategoryObservable()
                .subscribeOn(Schedulers.io())
                .subscribe {
                    _currentCategory.postValue(it)
                    onCategoryChanged(it)
                }
                .addTo(compositeDisposable)
    }

    protected abstract fun onCategoryChanged(category: Category)

    override fun onCleared() {
        compositeDisposable.dispose()
    }
}