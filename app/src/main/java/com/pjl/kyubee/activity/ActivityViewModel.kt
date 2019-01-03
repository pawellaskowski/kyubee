package com.pjl.kyubee.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pjl.kyubee.model.Category
import com.pjl.kyubee.repository.CategoryRepository
import com.pjl.kyubee.settings.SettingsController
import com.pjl.kyubee.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ActivityViewModel @Inject constructor(
        private val repository: CategoryRepository,
        settings: SettingsController
) : BaseViewModel(settings) {

    val categories: LiveData<List<Category>>
        get() = _categories
    private val _categories = MutableLiveData<List<Category>>()

    init {
        repository.getAllCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    list -> _categories.value = list
                    selectCategory(_currentCategory.value)
                }
                .addTo(disposables)
    }

    fun selectCategory(category: Category?) {
        settings.setCategory(category)
    }
}