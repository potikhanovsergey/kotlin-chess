package com.example.kursovaya.implementations.bot

import com.example.kursovaya.implementations.engine.Coordinate
import com.example.kursovaya.implementations.engine.Move
import com.example.kursovaya.interfaces.Board
import com.example.kursovaya.interfaces.Bot
import com.example.kursovaya.interfaces.Piece
import com.example.kursovaya.interfaces.PieceFactory
import com.example.kursovaya.utils.ChessUtil

private class Choice(
    val moves: Collection<Move>,
    val board: Board,
    val score: Int,
    val parent: Choice?
)

class MinimaxBot(
    override val playerId: Int,
    private val pieceFactory: PieceFactory
) : Bot {
    private val turnsAhead: Int = 3

    override fun getNextTurn(board: Board): Collection<Move> {
        val levels: ArrayList<MutableList<Choice>> = ArrayList()
        levels.add(getChoices(board, playerId, null).toMutableList())
        var bestChoice: Choice? = null
        val otherPlayerId = ChessUtil.getOtherPlayerId(playerId)
        while (levels.isNotEmpty()) {
            val currentLevel = levels.last()
            when {
                currentLevel.isEmpty() -> {
                    levels.removeAt(levels.size - 1)
                }
                levels.size == turnsAhead -> {
                    for (choice in currentLevel) {
                        if (bestChoice == null || bestChoice.score < choice.score) {
                            bestChoice = choice
                        }
                    }
                    levels.removeAt(levels.size - 1)
                }
                else -> {
                    val choice = currentLevel.removeAt(currentLevel.size - 1)
                    val currentPlayer = if (levels.size % 2 == 0) {
                        otherPlayerId
                    } else {
                        playerId
                    }
                    levels.add(
                        getChoices(
                            choice.board,
                            currentPlayer,
                            choice
                        ).toMutableList()
                    )
                }
            }
        }
        return if (bestChoice != null) {
            var rootChoice = bestChoice
            while (rootChoice?.parent != null) {
                rootChoice = rootChoice.parent
            }
            return rootChoice!!.moves
        } else {
            RandomBot(playerId, pieceFactory).getNextTurn(board)
        }
    }

    override fun getPromotedPiece(coordinate: Coordinate, board: Board): Piece {
        return pieceFactory.createPromotedPiece(coordinate, ChessUtil.queenId)
    }

    private fun getChoices(board: Board, playerId: Int, parent: Choice?): Collection<Choice> {
        val pieces: Collection<Piece> = board.getPiecesForPlayer(playerId)
        val choices = arrayListOf<Choice>()
        for (piece in pieces) {
            val validMoves = piece.getValidMoves(board)
            for (moves in validMoves) {
                val score = (parent?.score ?: 0) + moveScoreDelta(board, moves)
                choices.add(
                    Choice(
                        moves,
                        board.applyMoves(moves),
                        score,
                        parent
                    )
                )
            }
        }
        return choices
    }

    private fun moveScoreDelta(board: Board, moves: Collection<Move>): Int {
        return moves.fold(0) { totalDelta, move ->
            totalDelta + move.scoreDelta(board, playerId)
        }
    }
}