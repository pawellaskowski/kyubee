package com.pjl.kyubee.di

import com.pjl.kyubee.*
import com.pjl.kyubee.repository.CategoryRepository
import com.pjl.kyubee.settings.SettingsController
import com.pjl.kyubee.timer.strategy.TimingStrategyFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

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
}