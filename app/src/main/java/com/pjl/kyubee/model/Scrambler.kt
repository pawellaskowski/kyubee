package com.pjl.kyubee.model

import net.gnehzr.tnoodle.scrambles.Puzzle
import puzzle.*

enum class Scrambler(val tag: String, val puzzle: Puzzle) {

    CUBE2("2x2x2", CubePuzzle(2)),
    CUBE3("3x3x3", CubePuzzle(3)),
    CUBE4("4x4x4", CubePuzzle(4)),
    CUBE5("5x5x5", CubePuzzle(5)),
    CUBE6("6x6x6", CubePuzzle(6)),
    CUBE7("7x7x7", CubePuzzle(7)),
    CLOCK("Clock", ClockPuzzle()),
    MEGAMINX("Megaminx", MegaminxPuzzle()),
    PYRAMINX("Pyraminx", PyraminxPuzzle()),
    SKEWB("Skewb", SkewbPuzzle()),
    SQUARE1("Square-1", SquareOneUnfilteredPuzzle())
}