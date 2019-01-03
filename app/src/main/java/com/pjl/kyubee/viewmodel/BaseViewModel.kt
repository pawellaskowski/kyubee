package com.pjl.kyubee.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pjl.kyubee.model.Category
import com.pjl.kyubee.settings.SettingsController
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(protected val settings: SettingsController) : ViewModel() {

    val currentCategory: LiveData<Category>
        get() = _currentCategory

    protected val disposables = CompositeDisposable()
    protected val categorySubject = settings.categorySubject()
    protected val _currentCategory = MutableLiveData<Category>()


    override fun onCleared() {
        disposables.dispose()
        settings.clearDisposables()
        super.onCleared()
    }
}