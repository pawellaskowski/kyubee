package com.pjl.kyubee.model

import android.arch.lifecycle.LiveData
import android.os.AsyncTask

class SolveRepository private constructor(database: SolveRoomDatabase) {

    private val solveDao = database.solveDao()
    private val allSolves: LiveData<List<Solve>>

    init {
        allSolves = solveDao.getAllSolves()
    }

    companion object {
        private var INSTANCE: SolveRepository? = null

        fun getInstance(database: SolveRoomDatabase): SolveRepository {
            if (INSTANCE == null) {
                synchronized(SolveRepository::class) {
                    if (INSTANCE == null) {
                        INSTANCE = SolveRepository(database)
                    }
                }
            }
            return INSTANCE!!
        }
    }

    fun getAllSolves() = allSolves

    fun insert(solve: Solve) = InsertAsyncTask(solveDao).execute(solve)

    private class InsertAsyncTask(val dao: SolveDao) : AsyncTask<Solve, Void, Void>() {

        override fun doInBackground(vararg params: Solve): Void? {
            dao.insert(params[0])
            return null
        }

    }
}