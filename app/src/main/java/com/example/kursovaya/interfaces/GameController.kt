package com.example.kursovaya.interfaces

import com.example.kursovaya.GameViewModel
import com.example.kursovaya.implementations.engine.Coordinate

interface GameController {
    fun playerTurn(): Int

    fun getPromotablePawnCoordinate(): Coordinate?

    fun addViewModelListener(listener: GameEventListener)

    fun selectCoordinate(coordinate: Coordinate)

    fun getViewModel(): GameViewModel

    fun promotePawn(promotedPiece: Piece)
}
