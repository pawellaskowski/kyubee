package com.pjl.kyubee.model

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.os.AsyncTask

class SolveRepository private constructor(database: SolveDatabase) {

    private val solveDao = database.solveDao()
    private val allSolves: LiveData<PagedList<Solve>>

    init {
        val factory = solveDao.getAllSolves()
        allSolves = LivePagedListBuilder(factory, 30).build()
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