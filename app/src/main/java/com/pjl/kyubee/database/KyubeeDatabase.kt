package com.pjl.kyubee.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pjl.kyubee.model.Solve

@Database(
        entities = [Solve::class],
        version = 1,
        exportSchema = false
)
abstract class KyubeeDatabase : RoomDatabase() {

    abstract fun solveDao(): SolveDao
}