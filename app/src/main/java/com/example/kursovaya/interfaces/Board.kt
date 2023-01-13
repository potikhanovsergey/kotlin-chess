package com.example.kursovaya.interfaces

import com.example.kursovaya.implementations.engine.Coordinate
import com.example.kursovaya.implementations.engine.Move

interface Board {
    fun getPiece(location: Coordinate): Piece?

    fun getPieces(): Collection<Piece>

    fun getPiecesForPlayer(playerId: Int): Collection<Piece>

    fun applyMoves(moves: Collection<Move>): Board

    fun movePiece(coordinate: Coordinate, piece: Piece): Board

    val lastMovedPiece: Pair<Piece, Piece>?

    fun isKingInCheck(playerId: Int): Boolean
}
