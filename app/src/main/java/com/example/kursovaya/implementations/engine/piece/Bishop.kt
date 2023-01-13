package com.example.kursovaya.implementations.engine.piece

import android.graphics.Bitmap
import com.example.kursovaya.implementations.engine.Coordinate
import com.example.kursovaya.implementations.engine.movement.DiagonalBehavior
import com.example.kursovaya.interfaces.Board
import com.example.kursovaya.interfaces.Piece

class Bishop(
    override val location: Coordinate,
    override val playerId: Int,
    override val image: Bitmap?,
    moved: Boolean
) : BasePiece(moved) {
    override val score: Int = 3
    private val diagonalBehavior: DiagonalBehavior = DiagonalBehavior(location)

    override fun updateLocation(coordinate: Coordinate): Piece {
        return Bishop(coordinate, playerId, image, true)
    }

    override fun getPossibleCoordinates(board: Board): Collection<Coordinate> {
        return diagonalBehavior.possibleMoves(board)
    }
}
