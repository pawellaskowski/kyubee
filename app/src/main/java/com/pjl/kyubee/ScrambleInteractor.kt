package com.pjl.kyubee

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class ScrambleInteractor @Inject constructor(private val categoryUseCase: CategoryUseCase) : ScrambleUseCase {

    private val scrambleSubject = BehaviorSubject.create<String>()

    override fun getScrambleObservable(): Observable<String> {
        return scrambleSubject
    }

    override fun scramble() {
        scrambleSubject.onNext(categoryUseCase
                .getCurrentCategory()
                .scrambler
                .generateScramble())
    }
}