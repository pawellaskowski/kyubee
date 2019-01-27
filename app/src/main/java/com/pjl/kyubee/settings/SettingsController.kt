package com.pjl.kyubee.settings

import android.app.Application
import android.util.Log
import androidx.preference.PreferenceManager
import com.pjl.kyubee.R
import com.pjl.kyubee.model.Category
import com.pjl.kyubee.repository.CategoryRepository
import com.pjl.kyubee.utilities.CUBE3_TAG
import com.pjl.kyubee.utilities.ioThread
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

    init {
        categoryRepo.getCategory(preferredCategoryName())
                .toObservable()
                .subscribeOn(Schedulers.io())
                .subscribe(_categorySubject)
    }

    private fun preferredCategoryName(): String {
        return preferences.getString(
                appContext.resources.getString(R.string.selected_category_key),
                CUBE3_TAG
        ) ?: CUBE3_TAG
    }

    fun setCategory(selectedCategory: Category) {
        if (selectedCategory != _categorySubject.value) {
            with(preferences.edit()) {
                putString(appContext.resources.getString(R.string.selected_category_key),
                        selectedCategory.name)
                apply()
            }
            ioThread {
                _categorySubject.onNext(selectedCategory)
            }
        }
    }

    fun getCurrentCategory(): Category {
        return _categorySubject.value ?: throw IllegalStateException("Category not set")
    }
}