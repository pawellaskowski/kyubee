package com.pjl.kyubee.scrambling

import java.util.Random

class Cube3 : Puzzle {

    enum class Face {
        F, B, L, R, U, D
    }

    override fun generateScramble(length: Int): String {
        val random = Random()
        val stringBuilder = StringBuilder()
        for (i in 1..length) {
            val face = Face.values()[random.nextInt(Face.values().size)]
            with (stringBuilder) {
                append(face.name)
                if (random.nextBoolean()) {
                    append("\'")
                }
                if (i != length) {
                    append(" ")
                }
            }
        }
        return stringBuilder.toString()
    }
}