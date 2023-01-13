package com.example.kursovaya.interfaces

import com.example.kursovaya.implementations.engine.Coordinate
import com.example.kursovaya.implementations.engine.piece.*

interface PieceFactory {

    fun createBishop(location: Coordinate): Bishop

    fun createKing(location: Coordinate): King

    fun createKnight(location: Coordinate): Knight

    fun createPawn(location: Coordinate): Pawn

    fun createQueen(location: Coordinate): Queen

    fun createRook(location: Coordinate): Rook
    fun createPromotedPiece(location: Coordinate, id: Int): Piece
}
