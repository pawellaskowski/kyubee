package com.pjl.kyubee

import com.pjl.kyubee.model.Category
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface CategoryUseCase {

    fun getCategoryList(): Observable<List<Category>>

    fun getCategoryObservable(): Observable<Category>

    fun getCurrentCategory(): Category

    fun setCurrentCategory(category: Category)
}