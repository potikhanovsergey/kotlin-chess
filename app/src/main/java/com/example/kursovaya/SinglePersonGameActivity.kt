package com.example.kursovaya

import com.example.kursovaya.implementations.bot.MinimaxBot
import com.example.kursovaya.implementations.bot.RandomBot
import com.example.kursovaya.implementations.controller.SinglePersonGameController
import com.example.kursovaya.interfaces.Game
import com.example.kursovaya.interfaces.GameController

class SinglePersonGameActivity : ChessGameActivity() {
    override fun buildController(game: Game): GameController {
        return SinglePersonGameController(
            game,
            MinimaxBot(
                1,
                getGameFactory().blackPieceFactory
            )
        )
    }
}