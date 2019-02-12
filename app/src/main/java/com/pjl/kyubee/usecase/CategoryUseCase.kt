package com.pjl.kyubee.usecase

import com.pjl.kyubee.entity.Category
import io.reactivex.Observable

interface CategoryUseCase {

    fun getCategoryList(): Observable<List<Category>>

    fun getCategoryObservable(): Observable<Category>

    fun getCurrentCategory(): Category

    fun setCurrentCategory(category: Category)
}