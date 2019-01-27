package com.pjl.kyubee.di

import com.pjl.kyubee.*
import com.pjl.kyubee.repository.CategoryRepository
import com.pjl.kyubee.repository.SolveRepository
import com.pjl.kyubee.settings.SettingsController
import com.pjl.kyubee.timer.strategy.TimingStrategyFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideTimerUseCase(solveUseCase: SolveUseCase,
                            strategyFactory: TimingStrategyFactory): TimerUseCase {
        return TimerInteractor(solveUseCase, strategyFactory)
    }


    @Singleton
    @Provides
    fun provideCategoryUseCase(categoryRepo: CategoryRepository,
                               settingsController: SettingsController): CategoryUseCase {
        return CategoryInteractor(categoryRepo, settingsController)
    }


    @Singleton
    @Provides
    fun provideScrambleUseCase(categoryUseCase: CategoryUseCase): ScrambleUseCase {
        return ScrambleInteractor(categoryUseCase)
    }


    @Singleton
    @Provides
    fun provideSolveUseCase(categoryUseCase: CategoryUseCase,
                            scrambleUseCase: ScrambleUseCase,
                            solveRepo: SolveRepository): SolveUseCase {
        return SolveInteractor(categoryUseCase, scrambleUseCase, solveRepo)
    }
}