package com.example.kursovaya.interfaces

import com.example.kursovaya.implementations.engine.Coordinate

interface ChessBoardViewListener {
    fun onCoordinateSelected(coordinate: Coordinate)
}