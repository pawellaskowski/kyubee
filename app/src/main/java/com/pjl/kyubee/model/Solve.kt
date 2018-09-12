package com.pjl.kyubee.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Solve(val time: Long) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}