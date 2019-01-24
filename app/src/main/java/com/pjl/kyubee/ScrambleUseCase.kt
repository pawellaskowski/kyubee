package com.pjl.kyubee

import io.reactivex.Observable

interface ScrambleUseCase {

    fun scramble()

    fun getScrambleObservable(): Observable<String>
}