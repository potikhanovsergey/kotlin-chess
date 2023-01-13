package com.example.kursovaya.implementations.engine.piece

import android.graphics.Bitmap
import com.example.kursovaya.implementations.engine.Coordinate
import com.example.kursovaya.implementations.engine.movement.PlusBehavior
import com.example.kursovaya.interfaces.Board
import com.example.kursovaya.interfaces.Piece

class Rook(
    override val location: Coordinate,
    override val playerId: Int,
    override val image: Bitmap?,
    moved: Boolean
) : BasePiece(moved) {
    override val score: Int = 5
    private val plusBehavior: PlusBehavior = PlusBehavior(location)

    override fun updateLocation(coordinate: Coordinate): Piece {
        return Rook(coordinate, playerId, image, true)
    }

    override fun getPossibleCoordinates(board: Board): Collection<Coordinate> {
        return plusBehavior.possibleMoves(board)
    }
}
