package com.example.kursovaya.utils

object ChessUtil {
    const val tilesPerSide: Int = 8

    const val bishopId: Int = 0
    const val knightId: Int = 1
    const val queenId: Int = 2
    const val rookId: Int = 3
    const val pawnId: Int = 4
    val figureIds: Collection<Int> = (0 until 5).toList()

    fun getOtherPlayerId(playerId: Int): Int {
        return if (playerId == 0) {
            1
        } else {
            0
        }
    }
}