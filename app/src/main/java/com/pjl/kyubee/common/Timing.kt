package com.pjl.kyubee.common

import android.os.SystemClock

const val inspectionDuration = 15000L
const val holdDuration = 500L

fun now() = SystemClock.elapsedRealtime()