package com.pjl.kyubee.model

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

class SolveRepository(application: Application) {

    private val solveDao: SolveDao?
    private val allSolves: LiveData<List<Solve>>?

    init {
        val db = SolveRoomDatabase.getDatabase(application)
        solveDao = db?.solveDao()
        allSolves = solveDao?.getAllSolves()
    }

    fun getAllSolves() = allSolves

    fun insert(solve: Solve) = InsertAsyncTask(solveDao).execute(solve)

    private class InsertAsyncTask(val dao: SolveDao?) : AsyncTask<Solve, Void, Void>() {

        override fun doInBackground(vararg params: Solve): Void? {
            dao?.insert(params[0])
            return null
        }

    }
}