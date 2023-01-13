package com.example.kursovaya

import com.example.kursovaya.implementations.engine.Coordinate
import com.example.kursovaya.interfaces.Piece

/**
 * @param pieces фигуры на доске
 * @param selectedCoordinate координата выбранной фигуры
 * @param validDestinations возможные ходы для выбранной фигуры
 */
data class GameViewModel(
    val pieces: Collection<Piece>,
    val selectedCoordinate: Coordinate?,
    val validDestinations: Collection<Coordinate>
)
