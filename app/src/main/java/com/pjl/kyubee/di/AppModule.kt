package com.pjl.kyubee.di

import android.app.Application
import com.pjl.kyubee.*
import com.pjl.kyubee.database.CategoryDao
import com.pjl.kyubee.database.KyubeeDatabase
import com.pjl.kyubee.database.SolveDao
import com.pjl.kyubee.repository.CategoryRepository
import com.pjl.kyubee.repository.SolveRepository
import com.pjl.kyubee.settings.SettingsController
import com.pjl.kyubee.timer.strategy.TimingStrategyFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application): KyubeeDatabase = KyubeeDatabase.getDatabase(app)

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
    fun provideSettingsController(app: Application,
                                  categoryRepo: CategoryRepository) =
            SettingsController(app, categoryRepo)

    @Singleton
    @Provides
    fun provideTimerUseCase(strategyFactory: TimingStrategyFactory) : TimerUseCase =
            TimerInteractor(strategyFactory)

    @Singleton
    @Provides
    fun provideCategoryUseCase(categoryRepo: CategoryRepository,
                               settingsController: SettingsController) : CategoryUseCase =
            CategoryInteractor(categoryRepo, settingsController)

    @Singleton
    @Provides
    fun provideScrambleUseCase(categoryUseCase: CategoryUseCase) : ScrambleUseCase =
            ScrambleInteractor(categoryUseCase)

    // TODO: use case module
}