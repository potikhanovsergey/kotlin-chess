package com.example.kursovaya.interfaces

import android.graphics.Bitmap
import com.example.kursovaya.implementations.engine.Coordinate
import com.example.kursovaya.implementations.engine.Move

interface Piece {
    val score: Int

    val location: Coordinate

    val playerId: Int

    val image: Bitmap?

    fun hasMoved(): Boolean

    fun updateLocation(coordinate: Coordinate): Piece

    fun captureLocation(coordinate: Coordinate, board: Board): Coordinate

    fun getValidMoves(board: Board): Collection<Collection<Move>>

    fun getPossibleMoves(board: Board): Collection<Collection<Move>>

    fun getPossibleDestinations(board: Board): Collection<Coordinate>
}
