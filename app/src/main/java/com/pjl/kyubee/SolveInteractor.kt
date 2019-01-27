package com.pjl.kyubee

import com.pjl.kyubee.model.Solve
import com.pjl.kyubee.repository.SolveRepository
import com.pjl.kyubee.timer.Timer
import javax.inject.Inject

class SolveInteractor @Inject constructor(
        private val categoryUseCase: CategoryUseCase,
        private val scrambleUseCase: ScrambleUseCase,
        private val solveRepository: SolveRepository
) : SolveUseCase {

    override fun loadAll() = solveRepository.getAllSolves()

    override fun save(timer: Timer) {
        val solve = Solve(timer.accumulatedTime, categoryUseCase.getCurrentCategory().id)
        solveRepository.insert(solve)
    }
}