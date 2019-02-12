package com.pjl.kyubee.usecase

import io.reactivex.Observable

interface ScrambleUseCase {

    fun scramble()

    fun getScrambleObservable(): Observable<String>
}