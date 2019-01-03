package com.pjl.kyubee.model

import com.pjl.kyubee.utilities.*
import net.gnehzr.tnoodle.scrambles.Puzzle
import puzzle.*

enum class Scrambler(val tag: String, val puzzle: Puzzle) {

    CUBE2(CUBE2_TAG, CubePuzzle(2)),
    CUBE3(CUBE3_TAG, CubePuzzle(3)),
    CUBE4(CUBE4_TAG, CubePuzzle(4)),
    CUBE5(CUBE5_TAG, CubePuzzle(5)),
    CUBE6(CUBE6_TAG, CubePuzzle(6)),
    CUBE7(CUBE7_TAG, CubePuzzle(7)),
    CLOCK(CLOCK_TAG, ClockPuzzle()),
    MEGAMINX(MEGAMINX_TAG, MegaminxPuzzle()),
    PYRAMINX(PYRAMINX_TAG, PyraminxPuzzle()),
    SKEWB(SKEWB_TAG, SkewbPuzzle()),
    SQUARE1(SQUARE1_TAG, SquareOneUnfilteredPuzzle());

    fun generateScramble(): String = puzzle.generateScramble()
}