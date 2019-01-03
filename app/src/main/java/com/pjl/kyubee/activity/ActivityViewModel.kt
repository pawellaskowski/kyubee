package com.pjl.kyubee.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pjl.kyubee.model.Category
import com.pjl.kyubee.model.Session
import com.pjl.kyubee.repository.CategoryRepository
import com.pjl.kyubee.repository.SessionRepository
import com.pjl.kyubee.settings.SettingsController
import com.pjl.kyubee.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ActivityViewModel @Inject constructor(
        categoryRepo: CategoryRepository,
        sessionRepo: SessionRepository,
        settings: SettingsController
) : BaseViewModel(settings) {

    val categories: LiveData<List<Category>>
        get() = _categories
    private val _categories = MutableLiveData<List<Category>>()

    val sessions: LiveData<List<Session>>
        get() = _sessions
    private val _sessions = MutableLiveData<List<Session>>()

    init {
        categoryRepo.getAllCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    categoryList -> _categories.value = categoryList
                    selectCategory(_currentCategory.value)
                }
                .addTo(disposables)

        sessionRepo.getAllSessions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    sessionList -> _sessions.value = sessionList
                    selectSession(_currentSession?.value)
                }
                .addTo(disposables)
    }

    fun selectCategory(category: Category?) {
        settings.setCategory(category)
    }

    fun selectSession(session: Session?) {
        settings.setSession(session)
    }
}