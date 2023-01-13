package com.example.kursovaya.implementations.engine.piece

import android.graphics.Bitmap
import com.example.kursovaya.implementations.engine.Coordinate
import com.example.kursovaya.implementations.engine.Move
import com.example.kursovaya.interfaces.Board
import com.example.kursovaya.interfaces.Piece

class King(
    override val location: Coordinate,
    override val playerId: Int,
    override val image: Bitmap?,
    private val moved: Boolean
) : BasePiece(moved) {
    override val score: Int = 1000
    override fun updateLocation(coordinate: Coordinate): King {
        return King(coordinate, playerId, image, true)
    }

    override fun getPossible(board: Board): Collection<Collection<Move>> {
        val moves = super.getPossible(board)
        val castleLeftMoves = castleLeft(board)
        val castleMoves = arrayListOf<Collection<Move>>()
        if (castleLeftMoves.isNotEmpty()) {
            castleMoves.add(castleLeftMoves)
        }
        val castleRightMoves = castleRight(board)
        if (castleRightMoves.isNotEmpty()) {
            castleMoves.add(castleRightMoves)
        }
        return if (castleMoves.isEmpty()) {
            moves
        } else {
            castleMoves.addAll(moves)
            castleMoves
        }
    }

    override fun getPossibleCoordinates(board: Board): Collection<Coordinate> {
        val possibilities = hashSetOf<Coordinate>()
        for (xDelta in (-1..1)) {
            for (yDelta in (-1..1)) {
                if (xDelta == 0 && yDelta == 0) continue
                possibilities.add(
                    Coordinate(
                        location.x + xDelta,
                        location.y + yDelta
                    )
                )
            }
        }
        return possibilities
    }

    private fun castleLeft(board: Board): Collection<Move> {
        if (canCastle(board)) {
            val rookDestination = Coordinate(location.x - 1, location.y)
            val left1: Piece? = board.getPiece(rookDestination)
            val kingDestination = Coordinate(location.x - 2, location.y)
            val left2: Piece? = board.getPiece(kingDestination)
            val left3: Piece? = board.getPiece(
                Coordinate(location.x - 3, location.y)
            )
            val rookLocation = Coordinate(location.x - 4, location.y)
            val rook: Piece? = board.getPiece(rookLocation)
            val gapEmpty: Boolean = left1 == null && left2 == null && left3 == null
            if (gapEmpty && rook != null && !rook.hasMoved()) {
                return listOf(
                    Move(kingDestination, this),
                    Move(rookDestination, rook)
                )
            }
        }
        return emptyList()
    }

    private fun castleRight(board: Board): Collection<Move> {
        if (canCastle(board)) {
            val rookDestination = Coordinate(location.x + 1, location.y)
            val right1: Piece? = board.getPiece(rookDestination)
            val kingDestination = Coordinate(location.x + 2, location.y)
            val right2: Piece? = board.getPiece(kingDestination)
            val rookLocation = Coordinate(location.x + 3, location.y)
            val rook: Piece? = board.getPiece(rookLocation)
            val gapEmpty: Boolean = right1 == null && right2 == null
            if (gapEmpty && rook != null && !rook.hasMoved()) {
                return listOf(
                    Move(kingDestination, this),
                    Move(rookDestination, rook)
                )
            }
        }
        return emptyList()
    }

    private fun canCastle(board: Board): Boolean {
        return !moved && !board.isKingInCheck(playerId)
    }
}
