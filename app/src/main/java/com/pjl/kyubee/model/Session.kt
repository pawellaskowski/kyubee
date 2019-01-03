package com.pjl.kyubee.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Session(val name: String) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}