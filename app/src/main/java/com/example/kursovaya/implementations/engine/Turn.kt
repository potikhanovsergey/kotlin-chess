package com.example.kursovaya.implementations.engine

import com.example.kursovaya.interfaces.Piece

data class Turn(
    val moves: Collection<Move>,
    val removedPieces: Collection<Piece>
)