package com.pjl.kyubee.repository

import com.pjl.kyubee.model.Solve
import com.pjl.kyubee.database.SolveDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SolveRepository @Inject constructor(private val solveDao: SolveDao) {

    fun getAllSolves() = solveDao.getAllSolves()

    fun insert(solve: Solve) = solveDao.insert(solve)
}