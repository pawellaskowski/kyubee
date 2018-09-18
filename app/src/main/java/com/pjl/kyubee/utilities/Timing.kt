package com.pjl.kyubee.utilities

import android.os.SystemClock

const val inspectionDuration = 15000L
const val holdDuration = 500L

fun now() = SystemClock.elapsedRealtime()