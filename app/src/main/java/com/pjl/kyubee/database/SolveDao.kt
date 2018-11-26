package com.pjl.kyubee.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pjl.kyubee.model.Solve

@Dao
interface SolveDao {

    @Insert
    fun insert(solve: Solve)

    @Query("DELETE FROM Solve")
    fun deleteAll()

    @Query("SELECT * FROM Solve")
    fun getAllSolves(): LiveData<List<Solve>>
}