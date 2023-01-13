package com.example.kursovaya.interfaces

import com.example.kursovaya.implementations.engine.Coordinate
import com.example.kursovaya.implementations.engine.Move

interface Bot {
    val playerId: Int

    fun getNextTurn(board: Board): Collection<Move>

    fun getPromotedPiece(coordinate: Coordinate, board: Board): Piece
}
