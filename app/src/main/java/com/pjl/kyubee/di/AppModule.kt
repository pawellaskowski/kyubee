package com.pjl.kyubee.di

import android.app.Application
import android.arch.persistence.room.Room
import com.pjl.kyubee.model.SolveDao
import com.pjl.kyubee.model.SolveRepository
import com.pjl.kyubee.model.SolveDatabase
import com.pjl.kyubee.utilities.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideSolveDatabase(app: Application): SolveDatabase = Room
                .databaseBuilder(app, SolveDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .build()

    @Singleton
    @Provides
    fun provideSolveDao(db: SolveDatabase): SolveDao = db.solveDao()

    @Singleton
    @Provides
    fun provideSolveRepository(solveDao: SolveDao): SolveRepository = SolveRepository(solveDao)
}