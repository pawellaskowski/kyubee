package com.pjl.kyubee.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pjl.kyubee.model.Category
import com.pjl.kyubee.repository.CategoryRepository
import com.pjl.kyubee.settings.SettingsController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ActivityViewModel @Inject constructor(
        private val repository: CategoryRepository,
        private val settings: SettingsController
) : ViewModel() {


    val categories = MutableLiveData<List<Category>>()
    val currentCategory = MutableLiveData<Category>()
    private val disposables = CompositeDisposable()
    private val categorySubject = settings.categorySubject()

    init {
        categorySubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    currentCategory.value = it
                }
                .addTo(disposables)

        repository.getAllCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    list -> categories.value = list
                    selectCategory(currentCategory.value)
                }
                .addTo(disposables)
    }

    fun selectCategory(category: Category?) {
        settings.setCategory(category)
    }

    override fun onCleared() {
        disposables.dispose()
        settings.clearDisposables()
        super.onCleared()
    }
}