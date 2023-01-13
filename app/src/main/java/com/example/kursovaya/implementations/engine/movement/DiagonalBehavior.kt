package com.example.kursovaya.implementations.engine.movement

import com.example.kursovaya.implementations.engine.Coordinate
import com.example.kursovaya.interfaces.Board

class DiagonalBehavior(
    private val origin: Coordinate
) : LineBehavior() {
    fun possibleMoves(board: Board): Collection<Coordinate> {
        val moveCollector: ArrayList<Coordinate> = arrayListOf()
        collectDirection(origin, board, moveCollector, -1, -1)
        collectDirection(origin, board, moveCollector, -1, 1)
        collectDirection(origin, board, moveCollector, 1, -1)
        collectDirection(origin, board, moveCollector, 1, 1)
        return moveCollector
    }
}
