package com.pjl.kyubee.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
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

    companion object {
        private var INSTANCE: KyubeeDatabase? = null

        fun getDatabase(app: Application) =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: Room.databaseBuilder(app,
                            KyubeeDatabase::class.java, DATABASE_NAME)
                            .addCallback(object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    ioThread {
                                        enumValues<Scrambler>().forEach {
                                            INSTANCE?.categoryDao()?.insert(Category(it.tag, it))
                                        }
                                    }
                                }
                            })
                            .build()
                            .also { INSTANCE = it }
                }
    }
}