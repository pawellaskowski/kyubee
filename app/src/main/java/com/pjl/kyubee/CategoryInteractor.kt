package com.pjl.kyubee

import com.pjl.kyubee.model.Category
import com.pjl.kyubee.repository.CategoryRepository
import com.pjl.kyubee.settings.SettingsController
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class CategoryInteractor @Inject constructor(
        private val categoryRepository: CategoryRepository,
        private val settingsController: SettingsController
) : CategoryUseCase {

    override fun getCategoryList(): Observable<List<Category>> {
        return categoryRepository.getAllCategories().toObservable()
    }

    override fun getCategoryObservable(): Observable<Category> {
        return settingsController.categorySubject
    }

    override fun getCurrentCategory(): Category {
        return settingsController.getCurrentCategory()
    }

    override fun setCurrentCategory(category: Category) {
        settingsController.setCategory(category)
    }
}