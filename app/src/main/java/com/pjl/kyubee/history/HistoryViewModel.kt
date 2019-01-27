package com.pjl.kyubee.history

import com.pjl.kyubee.CategoryUseCase
import com.pjl.kyubee.SolveUseCase
import com.pjl.kyubee.viewmodel.BaseViewModel
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
        solveUseCase: SolveUseCase,
        categoryUseCase: CategoryUseCase
) : BaseViewModel(categoryUseCase) {

    private val allSolves = solveUseCase.loadAll()

    fun getAllSolves() = allSolves
}