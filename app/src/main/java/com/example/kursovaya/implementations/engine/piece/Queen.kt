package com.example.kursovaya.implementations.engine.piece

import android.graphics.Bitmap
import com.example.kursovaya.interfaces.Board
import com.example.kursovaya.implementations.engine.Coordinate
import com.example.kursovaya.interfaces.Piece
import com.example.kursovaya.implementations.engine.movement.DiagonalBehavior
import com.example.kursovaya.implementations.engine.movement.PlusBehavior

class Queen(
    override val location: Coordinate,
    override val playerId: Int,
    override val image: Bitmap?,
    moved: Boolean
) : BasePiece(moved) {
    override val score: Int = 9
    private val diagonalBehavior = DiagonalBehavior(location)
    private val plusBehavior = PlusBehavior(location)

    override fun updateLocation(coordinate: Coordinate): Piece {
        return Queen(coordinate, playerId, image, true)
    }

    override fun getPossibleCoordinates(board: Board): Collection<Coordinate> {
        val moves = arrayListOf<Coordinate>()
        moves.addAll(diagonalBehavior.possibleMoves(board))
        moves.addAll(plusBehavior.possibleMoves(board))
        return moves
    }
}