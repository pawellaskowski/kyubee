package com.pjl.kyubee

import io.reactivex.Observable

interface ScrambleUseCase {

    fun generateScramble(): String

    fun getScrambleObservable(): Observable<String>
}