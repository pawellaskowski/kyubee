package com.pjl.kyubee.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pjl.kyubee.CategoryUseCase
import com.pjl.kyubee.DataHolder
import com.pjl.kyubee.Status
import com.pjl.kyubee.model.Category
import com.pjl.kyubee.repository.CategoryRepository
import com.pjl.kyubee.settings.SettingsController
import com.pjl.kyubee.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ActivityViewModel @Inject constructor(
        categoryUseCase: CategoryUseCase
) : BaseViewModel(categoryUseCase) {

    val categories: LiveData<DataHolder<List<Category>>>
        get() = _categories
    private val _categories = MutableLiveData<DataHolder<List<Category>>>()

    init {
        categoryUseCase.getCategoryList()
                .subscribeOn(Schedulers.io()) // Schedulers.computation() ?
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _categories.value = DataHolder(Status.LOADING, null)
                }
                .subscribe ({
                    _categories.value = DataHolder(Status.SUCCESS, it)
                    _currentCategory.value = categoryUseCase.getCurrentCategory()
                }, {
                    _categories.value = DataHolder(Status.ERROR, null)
                })
                .addTo(compositeDisposable)
    }

    fun selectCategory(category: Category) {
        categoryUseCase.setCurrentCategory(category)
    }
}