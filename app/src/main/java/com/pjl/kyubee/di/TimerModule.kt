package com.pjl.kyubee.di

import android.app.Application
import com.pjl.kyubee.timer.strategy.TimingStrategyFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TimerModule {

    @Singleton
    @Provides
    fun providesTimingStrategyFactory(app: Application) = TimingStrategyFactory(app)
}