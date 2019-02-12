package com.pjl.kyubee.usecase

import androidx.lifecycle.LiveData
import com.pjl.kyubee.entity.Solve

interface SolveUseCase {

    fun save(time: Long, categoryId: Long)

    fun loadAll(): LiveData<List<Solve>>
}