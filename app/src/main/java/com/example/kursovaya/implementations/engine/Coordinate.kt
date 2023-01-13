package com.example.kursovaya.implementations.engine

import com.example.kursovaya.utils.ChessUtil

data class Coordinate(
    val x: Int,
    val y: Int
) {
    companion object {
        fun inBounds(coordinate: Coordinate): Boolean {
            return (coordinate.x in 0 until ChessUtil.tilesPerSide) &&
                (coordinate.y in 0 until ChessUtil.tilesPerSide)
        }
    }
}
