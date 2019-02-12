package com.pjl.kyubee.history

import com.pjl.kyubee.usecase.CategoryUseCase
import com.pjl.kyubee.usecase.SolveUseCase
import com.pjl.kyubee.entity.Category
import com.pjl.kyubee.viewmodel.BaseViewModel
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
        solveUseCase: SolveUseCase,
        categoryUseCase: CategoryUseCase
) : BaseViewModel(categoryUseCase) {

    private val allSolves = solveUseCase.loadAll()

    fun getAllSolves() = allSolves

    override fun onCategoryChanged(category: Category) {

    }
}