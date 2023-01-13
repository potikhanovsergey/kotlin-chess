package com.example.kursovaya

import com.example.kursovaya.implementations.controller.TwoPersonGameController
import com.example.kursovaya.interfaces.Game
import com.example.kursovaya.interfaces.GameController

class TwoPersonGameActivity : ChessGameActivity() {
    override fun buildController(game: Game): GameController {
        return TwoPersonGameController(
            game
        )
    }
}