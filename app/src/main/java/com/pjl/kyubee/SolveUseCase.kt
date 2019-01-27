package com.pjl.kyubee

import androidx.lifecycle.LiveData
import com.pjl.kyubee.model.Solve
import com.pjl.kyubee.timer.Timer

interface SolveUseCase {

    fun save(timer: Timer)

    fun loadAll(): LiveData<List<Solve>>
}