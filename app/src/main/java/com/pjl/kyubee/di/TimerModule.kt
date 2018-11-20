package com.pjl.kyubee.di

import android.app.Application
import android.content.Context
import com.pjl.kyubee.R
import com.pjl.kyubee.model.preparation.InspectionStrategy
import com.pjl.kyubee.model.preparation.PreparationStrategy
import com.pjl.kyubee.model.preparation.SimpleStrategy
import dagger.Module
import dagger.Provides

@Module
class TimerModule {

    @Provides
    fun providesPreparationStrategy(app: Application): PreparationStrategy {
        val context = app.applicationContext
        val isSimpleStrategy = context
                .getSharedPreferences(context.getString(R.string.preference_file_key),
                        Context.MODE_PRIVATE)
                .getBoolean(context.getString(R.string.is_simple_strategy), true)
        return if (isSimpleStrategy) {
            SimpleStrategy()
        } else {
            InspectionStrategy()
        }
    }
}