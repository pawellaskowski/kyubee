package com.pjl.kyubee.history

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.pjl.kyubee.model.Solve
import com.pjl.kyubee.model.SolveRepository

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SolveRepository(application)
    private val allSolves: LiveData<List<Solve>>?

    init {
        allSolves = repository.getAllSolves()
    }

    fun getAllSolves() = allSolves

    fun insert(solve: Solve) = repository.insert(solve)
}