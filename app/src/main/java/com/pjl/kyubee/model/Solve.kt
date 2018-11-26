package com.pjl.kyubee.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Solve(val time: Long) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}