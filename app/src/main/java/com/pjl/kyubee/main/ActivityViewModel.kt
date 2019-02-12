package com.pjl.kyubee.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pjl.kyubee.usecase.CategoryUseCase
import com.pjl.kyubee.common.DataHolder
import com.pjl.kyubee.common.Status
import com.pjl.kyubee.entity.Category
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _categories.postValue(DataHolder(Status.LOADING, null))
                }
                .subscribe ({
                    _categories.postValue(DataHolder(Status.SUCCESS, it))
                    _currentCategory.postValue(categoryUseCase.getCurrentCategory())
                }, {
                    _categories.postValue(DataHolder(Status.ERROR, null))
                })
                .addTo(compositeDisposable)
    }

    fun setCategory(category: Category) {
        categoryUseCase.setCurrentCategory(category)
    }

    override fun onCategoryChanged(category: Category) {

    }
}