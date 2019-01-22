package com.pjl.kyubee.database

import android.app.Application
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE
import android.util.Log
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pjl.kyubee.model.Category
import com.pjl.kyubee.model.Scrambler
import com.pjl.kyubee.model.Solve
import com.pjl.kyubee.utilities.Converters
import com.pjl.kyubee.utilities.DATABASE_NAME
import com.pjl.kyubee.utilities.ioThread

@Database(
        entities = [Solve::class, Category::class],
        version = 1,
        exportSchema = false
)
@TypeConverters(Converters::class)
abstract class KyubeeDatabase : RoomDatabase() {

    abstract fun solveDao(): SolveDao

    abstract fun categoryDao(): CategoryDao
}