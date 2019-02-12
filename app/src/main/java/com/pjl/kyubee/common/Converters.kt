package com.pjl.kyubee.common

import androidx.room.TypeConverter
import com.pjl.kyubee.entity.Scrambler

class Converters {

    @TypeConverter
    fun fromScrambler(scrambler: Scrambler): String {
        return scrambler.name
    }

    @TypeConverter
    fun toScrambler(enumName: String): Scrambler {
        return Scrambler.valueOf(enumName)
    }
}