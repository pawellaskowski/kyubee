package com.pjl.kyubee.di

import com.pjl.kyubee.history.HistoryFragment
import com.pjl.kyubee.timer.TimerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeTimerFragment(): TimerFragment

    @ContributesAndroidInjector
    abstract fun contributeHistoryFragment(): HistoryFragment
}