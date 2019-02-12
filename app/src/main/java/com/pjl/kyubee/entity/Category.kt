package com.pjl.kyubee.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(val name: String, val scrambler: Scrambler) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}