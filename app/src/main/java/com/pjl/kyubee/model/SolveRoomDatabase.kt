package com.pjl.kyubee.model

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.os.AsyncTask

@Database(entities = [(Solve::class)], version = 1)
abstract class SolveRoomDatabase : RoomDatabase() {

    abstract fun solveDao(): SolveDao

    companion object {
        private var INSTANCE: SolveRoomDatabase? = null

        fun getDatabase(context: Context): SolveRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(SolveRoomDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                SolveRoomDatabase::class.java,
                                "Solve_Database")
                                .addCallback(roomDatabaseCallback)
                                .build()
                    }
                }
            }
            return INSTANCE
        }

        private val roomDatabaseCallback = object : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE).execute()
            }
        }
    }

    private class PopulateDbAsync(val db: SolveRoomDatabase?) : AsyncTask<Void, Void, Void>() {

        val dao = db?.solveDao()

        override fun doInBackground(vararg params: Void?): Void? {
            dao?.deleteAll()
            dao?.insert(Solve(12345))
            dao?.insert(Solve(4444))
            return null
        }

    }
}