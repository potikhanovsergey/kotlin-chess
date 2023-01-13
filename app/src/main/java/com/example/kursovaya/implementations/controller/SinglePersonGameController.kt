package com.example.kursovaya.implementations.controller

import com.example.kursovaya.interfaces.Bot
import com.example.kursovaya.interfaces.Game

class SinglePersonGameController(
    private val game: Game,
    private val bot: Bot
) : BaseGameController(game) {
    override fun postPlayerMoves() {
        super.postPlayerMoves()
        if (game.playerTurn() == bot.playerId) {
            executePlayerMoves(
                bot.getNextTurn(game.getCurrentBoard())
            )
            promotePieceIfNeeded()
        }
    }

    private fun promotePieceIfNeeded() {
        val promotableCoordinate = game.getPromotablePawnCoordinate()
        if (promotableCoordinate != null) {
            val piece = bot.getPromotedPiece(promotableCoordinate, game.getCurrentBoard())
            this.promotePawn(piece)
        }
    }
}
