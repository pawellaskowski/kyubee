package com.pjl.kyubee.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.pjl.kyubee.utilities.DATABASE_NAME

@Database(
        entities = [Solve::class],
        version = 1,
        exportSchema = false
)
abstract class SolveDatabase : RoomDatabase() {

    abstract fun solveDao(): SolveDao
}