package com.pjl.kyubee.settings

import android.app.Application
import android.util.Log
import androidx.preference.PreferenceManager
import com.pjl.kyubee.R
import com.pjl.kyubee.model.Category
import com.pjl.kyubee.repository.CategoryRepository
import com.pjl.kyubee.utilities.CUBE3_TAG
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.lang.IllegalStateException

class SettingsController(
        app: Application,
        categoryRepo: CategoryRepository
) {

    private val appContext = app.applicationContext
    private val preferences = PreferenceManager.getDefaultSharedPreferences(appContext)
    private val _categorySubject = BehaviorSubject.create<Category>()
    val categorySubject: Observable<Category>
        get() = _categorySubject
    private val disposables = CompositeDisposable()

    init {
        categoryRepo.getCategory(preferredCategoryName())
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    category -> _categorySubject.onNext(category)
                }, {
                    Log.d("__category", "Message: ${it.message}")
                })
                .addTo(disposables)
    }

    private fun preferredCategoryName(): String {
        return preferences.getString(
                appContext.resources.getString(R.string.selected_category_key),
                CUBE3_TAG
        ) ?: CUBE3_TAG
    }

    fun setCategory(selectedCategory: Category) {
        with (preferences.edit()) {
            putString(appContext.resources.getString(R.string.selected_category_key),
                    selectedCategory.name)
            apply()
        }
        _categorySubject.onNext(selectedCategory)
    }

    fun clearDisposables() {
        disposables.clear()
    }

    fun getCurrentCategory(): Category {
        return _categorySubject.value ?: throw IllegalStateException("Category not set")
    }
}