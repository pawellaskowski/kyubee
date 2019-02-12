package com.pjl.kyubee.interactor

import com.pjl.kyubee.entity.Solve
import com.pjl.kyubee.repository.SolveRepository
import com.pjl.kyubee.usecase.SolveUseCase
import javax.inject.Inject

class SolveInteractor @Inject constructor(
        private val solveRepository: SolveRepository
) : SolveUseCase {

    override fun loadAll() = solveRepository.getAllSolves()

    override fun save(time: Long, categoryId: Long) {
        val solve = Solve(time, categoryId)
        solveRepository.insert(solve)
    }
}