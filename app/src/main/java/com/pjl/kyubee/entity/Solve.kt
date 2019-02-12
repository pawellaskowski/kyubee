package com.pjl.kyubee.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"],
        onDelete = CASCADE)])
data class Solve(val time: Long, val categoryId: Long?) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}