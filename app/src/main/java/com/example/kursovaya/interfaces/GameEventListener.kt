package com.example.kursovaya.interfaces

import com.example.kursovaya.GameViewModel

interface GameEventListener {
    /**
     * вызывается при смене ViewModel
     */
    fun onViewModelChange(gameViewModel: GameViewModel)

    /**
     * вызывается при пате
     */
    fun onStalemate()

    /**
     * вызывается при мате
     */
    fun onCheckmate(checkedPlayerId: Int)
}