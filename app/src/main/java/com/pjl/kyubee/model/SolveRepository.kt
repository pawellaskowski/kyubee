package com.pjl.kyubee.model

import android.arch.lifecycle.LiveData
import android.os.AsyncTask

class SolveRepository private constructor(database: SolveDatabase) {

    private val solveDao = database.solveDao()
    private val allSolves: LiveData<List<Solve>>

    init {
        allSolves = solveDao.getAllSolves()
    }

    fun getAllSolves() = allSolves

    fun insert(solve: Solve) = InsertAsyncTask(solveDao).execute(solve)

    companion object {
        private var INSTANCE: SolveRepository? = null

        fun getInstance(database: SolveDatabase) =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: SolveRepository(database).also { INSTANCE = it}
                }

    }

    private class InsertAsyncTask(val dao: SolveDao) : AsyncTask<Solve, Void, Void>() {

        override fun doInBackground(vararg params: Solve): Void? {
            dao.insert(params[0])
            return null
        }

    }
}