package com.pjl.kyubee.settings

import android.app.Application
import androidx.preference.PreferenceManager
import com.pjl.kyubee.R
import com.pjl.kyubee.model.Category
import com.pjl.kyubee.model.Session
import com.pjl.kyubee.repository.CategoryRepository
import com.pjl.kyubee.repository.SessionRepository
import com.pjl.kyubee.utilities.CUBE3_TAG
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class SettingsController(
        app: Application,
        categoryRepo: CategoryRepository,
        sessionRepo: SessionRepository
) {

    private val appContext = app.applicationContext
    private val preferences = PreferenceManager.getDefaultSharedPreferences(appContext)
    private val categorySubject = BehaviorSubject.create<Category>()
    private val sessionSubject = BehaviorSubject.create<Session>()
    private val disposables = CompositeDisposable()

    init {
        val categoryName = preferences.getString(
                appContext.resources.getString(R.string.selected_category_key),
                CUBE3_TAG
        ) ?: CUBE3_TAG

        categoryRepo.getCategory(categoryName)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    c -> categorySubject.onNext(c)
                }.addTo(disposables)

        val sessionId = preferences.getLong(
                appContext.resources.getString(R.string.selected_session_key),
                1
        )

        sessionRepo.getSession(sessionId)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    s -> sessionSubject.onNext(s)
                }.addTo(disposables)
    }

    fun categorySubject(): Observable<Category> = categorySubject

    fun setCategory(selectedCategory: Category?) {
        if (selectedCategory != null) {
            with (preferences.edit()) {
                putString(appContext.resources.getString(R.string.selected_category_key),
                        selectedCategory.name)
                apply()
            }
            categorySubject.onNext(selectedCategory)
        }
    }

    fun sessionSubject(): Observable<Session> = sessionSubject

    fun setSession(selectedSession: Session?) {
        if (selectedSession != null) {
            with(preferences.edit()) {
                putLong(appContext.resources.getString(R.string.selected_session_key),
                        selectedSession.id)
                apply()
            }
            sessionSubject.onNext(selectedSession)
        }
    }

    fun clearDisposables() {
        disposables.clear()
    }
}