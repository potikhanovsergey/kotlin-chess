package com.example.kursovaya.interfaces

interface GameFactory {

    fun createNewGame(): Game

    val whitePieceFactory: PieceFactory
    val blackPieceFactory: PieceFactory
}
