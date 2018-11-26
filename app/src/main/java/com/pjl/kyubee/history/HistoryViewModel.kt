package com.pjl.kyubee.history

import android.arch.lifecycle.ViewModel
import com.pjl.kyubee.repository.SolveRepository
import javax.inject.Inject

class HistoryViewModel @Inject constructor(repository: SolveRepository) : ViewModel() {

    private val allSolves = repository.getAllSolves()

    fun getAllSolves() = allSolves
}