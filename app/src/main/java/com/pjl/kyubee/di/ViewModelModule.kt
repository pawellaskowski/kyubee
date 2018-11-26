package com.pjl.kyubee.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pjl.kyubee.history.HistoryViewModel
import com.pjl.kyubee.timer.TimerViewModel
import com.pjl.kyubee.viewmodel.KyubeeViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [TimerModule::class])
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TimerViewModel::class)
    abstract fun bindTimerViewModel(timerViewModel: TimerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    abstract fun bindHistoryViewModel(historyViewModel: HistoryViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: KyubeeViewModelFactory): ViewModelProvider.Factory
}