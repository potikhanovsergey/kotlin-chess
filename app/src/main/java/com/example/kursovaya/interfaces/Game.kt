package com.example.kursovaya.interfaces

import com.example.kursovaya.implementations.engine.Coordinate
import com.example.kursovaya.implementations.engine.Move
import com.example.kursovaya.implementations.engine.Turn

interface Game {
    fun getCurrentBoard(): Board

    fun getTurnHistory(): Collection<Turn>

    fun applyMoves(moves: Collection<Move>): Turn

    fun canUndo(): Boolean

    fun undo(): Turn

    fun playerTurn(): Int
    fun checkmatedPlayerId(): Int?
    fun isStalemate(): Boolean

    fun getPromotablePawnCoordinate(): Coordinate?

    fun promotePawn(promotedPiece: Piece): Turn
}
