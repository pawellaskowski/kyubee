package com.pjl.kyubee.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pjl.kyubee.entity.Category
import com.pjl.kyubee.entity.Solve
import com.pjl.kyubee.common.Converters

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