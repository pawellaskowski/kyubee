package com.pjl.kyubee.utilities

import java.util.concurrent.Executors

fun ioThread(f : () -> Unit) {
    Executors.newSingleThreadExecutor().execute(f)
}