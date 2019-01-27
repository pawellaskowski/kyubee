package com.pjl.kyubee

import android.util.Log
import com.pjl.kyubee.model.Category
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class ScrambleInteractor @Inject constructor(categoryUseCase: CategoryUseCase) : ScrambleUseCase {

    private val scrambleSubject = BehaviorSubject.create<String>()
    private lateinit var category: Category

    init {
        categoryUseCase.getCategoryObservable()
                .subscribeOn(Schedulers.io())
                .map {
                    category = it
                    generateScramble()
                }
                .subscribe(scrambleSubject)
    }

    override fun getScrambleObservable(): Observable<String> {
        return scrambleSubject
    }

    override fun scramble() {
        scrambleSubject.onNext(generateScramble())
    }

    private fun generateScramble(): String {
        return category.scrambler.generateScramble()
    }
}