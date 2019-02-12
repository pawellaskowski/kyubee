package com.pjl.kyubee.di

import com.pjl.kyubee.interactor.CategoryInteractor
import com.pjl.kyubee.interactor.ScrambleInteractor
import com.pjl.kyubee.interactor.SolveInteractor
import com.pjl.kyubee.interactor.TimerInteractor
import com.pjl.kyubee.repository.CategoryRepository
import com.pjl.kyubee.repository.SolveRepository
import com.pjl.kyubee.settings.SettingsController
import com.pjl.kyubee.timer.strategy.TimingStrategyFactory
import com.pjl.kyubee.usecase.CategoryUseCase
import com.pjl.kyubee.usecase.ScrambleUseCase
import com.pjl.kyubee.usecase.SolveUseCase
import com.pjl.kyubee.usecase.TimerUseCase
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
    fun provideSolveUseCase(solveRepo: SolveRepository): SolveUseCase {
        return SolveInteractor(solveRepo)
    }
}