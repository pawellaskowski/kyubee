package com.pjl.kyubee.history

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.pjl.kyubee.KyubeeApp
import com.pjl.kyubee.model.Solve

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as KyubeeApp).getRepository()
    private val allSolves: LiveData<List<Solve>>

    init {
        allSolves = repository.getAllSolves()
    }

    fun getAllSolves() = allSolves

}