package com.pjl.kyubee.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [(Solve::class)], version = 1)
abstract class SolveRoomDatabase : RoomDatabase() {

    abstract fun solveDao(): SolveDao

    companion object {
        private var INSTANCE: SolveRoomDatabase? = null

        fun getDatabase(context: Context): SolveRoomDatabase {
            if (INSTANCE == null) {
                synchronized(SolveRoomDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                SolveRoomDatabase::class.java,
                                "Solve_Database")
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

}