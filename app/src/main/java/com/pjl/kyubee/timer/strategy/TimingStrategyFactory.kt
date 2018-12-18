package com.pjl.kyubee.timer.strategy

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.pjl.kyubee.R
import com.pjl.kyubee.timer.Timer

class TimingStrategyFactory(private val app: Application) {

    fun createWith(timer: MutableLiveData<Timer>): TimingStrategy {
        val context = app.applicationContext
        val isSimpleStrategy = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(context.getString(R.string.is_simple_strategy), true)
        return if (isSimpleStrategy) {
            SimpleStrategy(timer)
        } else {
            InspectionStrategy(timer)
        }
    }
}