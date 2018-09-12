package com.pjl.kyubee

import android.app.Application
import com.pjl.kyubee.model.SolveRepository
import com.pjl.kyubee.model.SolveDatabase

class KyubeeApp : Application() {

    fun getRepository(): SolveRepository {
        return SolveRepository.getInstance(getDatabase())
    }

    fun getDatabase(): SolveDatabase {
        return SolveDatabase.getDatabase(this)
    }

}