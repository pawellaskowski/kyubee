package com.pjl.kyubee.model

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface SolveDao {

    @Insert
    fun insert(solve: Solve)

    @Query("DELETE FROM Solve")
    fun deleteAll()

    @Query("SELECT * FROM Solve")
    fun getAllSolves(): LiveData<List<Solve>>

}