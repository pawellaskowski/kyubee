package com.pjl.kyubee.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pjl.kyubee.model.Category
import com.pjl.kyubee.model.Session
import com.pjl.kyubee.settings.SettingsController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel(protected val settings: SettingsController) : ViewModel() {

    val currentCategory: LiveData<Category>
        get() = _currentCategory
    protected val _currentCategory = MutableLiveData<Category>()
    protected val categorySubject = settings.categorySubject()

    val currentSession: LiveData<Session>
        get() = _currentSession
    protected val _currentSession = MutableLiveData<Session>()
    protected val sessionSubject = settings.sessionSubject()

    protected val disposables = CompositeDisposable()

    init {
        sessionSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _currentSession.value = it
                }
                .addTo(disposables)
    }

    override fun onCleared() {
        disposables.dispose()
        settings.clearDisposables()
        super.onCleared()
    }
}