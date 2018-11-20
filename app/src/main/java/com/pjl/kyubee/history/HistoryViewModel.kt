package com.pjl.kyubee.history

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.pjl.kyubee.model.Solve
import com.pjl.kyubee.model.SolveRepository
import javax.inject.Inject

class HistoryViewModel @Inject constructor(repository: SolveRepository) : ViewModel() {

    private val allSolves = repository.getAllSolves()

    fun getAllSolves() = allSolves
}