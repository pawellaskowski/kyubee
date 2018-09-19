package com.pjl.kyubee.history

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.pjl.kyubee.KyubeeApp
import com.pjl.kyubee.model.Solve

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as KyubeeApp).getRepository()
    private val allSolves: LiveData<PagedList<Solve>>

    init {
        val factory = repository.getAllSolves()
        allSolves = LivePagedListBuilder(factory, 30).build()
    }

    fun getAllSolves() = allSolves
}