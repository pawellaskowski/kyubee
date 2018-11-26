package com.pjl.kyubee.di

import android.app.Application
import androidx.room.Room
import com.pjl.kyubee.database.SolveDao
import com.pjl.kyubee.repository.SolveRepository
import com.pjl.kyubee.database.KyubeeDatabase
import com.pjl.kyubee.utilities.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideSolveDatabase(app: Application): KyubeeDatabase = Room
                .databaseBuilder(app, KyubeeDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .build()

    @Singleton
    @Provides
    fun provideSolveDao(db: KyubeeDatabase): SolveDao = db.solveDao()

    @Singleton
    @Provides
    fun provideSolveRepository(solveDao: SolveDao): SolveRepository = SolveRepository(solveDao)
}