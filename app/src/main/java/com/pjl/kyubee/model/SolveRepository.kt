package com.pjl.kyubee.model

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SolveRepository @Inject constructor(private val solveDao: SolveDao) {

    fun getAllSolves() = solveDao.getAllSolves()

    fun insert(solve: Solve) = solveDao.insert(solve)
}