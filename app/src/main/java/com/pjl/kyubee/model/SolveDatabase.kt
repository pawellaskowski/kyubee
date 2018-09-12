package com.pjl.kyubee.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.pjl.kyubee.utilities.DATABASE_NAME

@Database(entities = [(Solve::class)], version = 1)
abstract class SolveDatabase : RoomDatabase() {

    abstract fun solveDao(): SolveDao

    companion object {
        private var INSTANCE: SolveDatabase? = null

        fun getDatabase(context: Context) =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: Room.databaseBuilder(context.applicationContext,
                            SolveDatabase::class.java, DATABASE_NAME)
                            .build()
                            .also { INSTANCE = it }
                }
    }

}