package com.pjl.kyubee.common

import java.util.concurrent.Executors

fun ioThread(f : () -> Unit) {
    Executors.newSingleThreadExecutor().execute(f)
}