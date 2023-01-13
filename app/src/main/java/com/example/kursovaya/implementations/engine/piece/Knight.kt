package com.example.kursovaya.implementations.engine.piece

import android.graphics.Bitmap
import com.example.kursovaya.implementations.engine.Coordinate
import com.example.kursovaya.implementations.engine.movement.KnightBehavior
import com.example.kursovaya.interfaces.Board
import com.example.kursovaya.interfaces.Piece

class Knight(
    override val location: Coordinate,
    override val playerId: Int,
    override val image: Bitmap?,
    moved: Boolean
) : BasePiece(moved) {
    private val knightBehavior = KnightBehavior(location)

    override val score: Int = 3

    override fun updateLocation(coordinate: Coordinate): Piece {
        return Knight(coordinate, playerId, image, true)
    }

    override fun getPossibleCoordinates(board: Board): Collection<Coordinate> {
        return knightBehavior.possibleMoves(board)
    }
}
