package com.pjl.kyubee.di

import android.app.Application
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pjl.kyubee.database.CategoryDao
import com.pjl.kyubee.database.KyubeeDatabase
import com.pjl.kyubee.database.SolveDao
import com.pjl.kyubee.entity.Category
import com.pjl.kyubee.entity.Scrambler
import com.pjl.kyubee.repository.CategoryRepository
import com.pjl.kyubee.repository.SolveRepository
import com.pjl.kyubee.settings.SettingsController
import com.pjl.kyubee.common.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, UseCaseModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application): KyubeeDatabase =
            Room.databaseBuilder(app,
                    KyubeeDatabase::class.java, DATABASE_NAME)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            enumValues<Scrambler>().forEach {
                                val contentValues = ContentValues().apply {
                                    put("name", it.tag)
                                    put("scrambler", it.name)
                                }
                                db.insert(Category::class.java.simpleName,
                                        SQLiteDatabase.CONFLICT_REPLACE, contentValues)
                            }
                        }
                    })
                    .build()

    @Singleton
    @Provides
    fun provideSolveDao(db: KyubeeDatabase): SolveDao = db.solveDao()

    @Singleton
    @Provides
    fun provideSolveRepository(solveDao: SolveDao): SolveRepository = SolveRepository(solveDao)

    @Singleton
    @Provides
    fun provideCategoryDao(db: KyubeeDatabase): CategoryDao = db.categoryDao()

    @Singleton
    @Provides
    fun provideCategoryRepository(categoryDao: CategoryDao) = CategoryRepository(categoryDao)

    @Singleton
    @Provides
    fun provideSettingsController(app: Application, categoryRepo: CategoryRepository) =
            SettingsController(app, categoryRepo)

}