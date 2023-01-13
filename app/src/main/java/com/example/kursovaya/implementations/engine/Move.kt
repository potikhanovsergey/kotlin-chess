package com.example.kursovaya.implementations.engine

import com.example.kursovaya.interfaces.Board
import com.example.kursovaya.interfaces.Piece

data class Move(
    val destination: Coordinate,
    val piece: Piece
) {
    /**
     * изначальная координата перемещаемой фигуры
     */
    val origin: Coordinate = piece.location

    fun captureLocation(board: Board): Coordinate {
        return piece.captureLocation(destination, board)
    }

    fun scoreDelta(board: Board, playerId: Int): Int {
        val removedPiece = board.getPiece(
            captureLocation(board)
        )
        return if (removedPiece != null) {
            val delta = if (removedPiece.playerId == playerId) {
                -removedPiece.score
            } else {
                removedPiece.score
            }
            delta
        } else {
            0
        }
    }
}
