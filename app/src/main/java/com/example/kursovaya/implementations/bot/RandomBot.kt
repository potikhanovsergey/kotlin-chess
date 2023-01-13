package com.example.kursovaya.implementations.bot

import com.example.kursovaya.implementations.engine.Coordinate
import com.example.kursovaya.implementations.engine.Move
import com.example.kursovaya.interfaces.Board
import com.example.kursovaya.interfaces.Bot
import com.example.kursovaya.interfaces.Piece
import com.example.kursovaya.interfaces.PieceFactory
import com.example.kursovaya.utils.ChessUtil

class RandomBot(
    override val playerId: Int,
    private val pieceFactory: PieceFactory
) : Bot {
    override fun getNextTurn(board: Board): Collection<Move> {
        val pieces = board.getPiecesForPlayer(playerId)
        for (piece in pieces.shuffled()) {
            val validMoves = piece.getValidMoves(board)
            if (validMoves.isNotEmpty()) {
                return validMoves.random()
            }
        }
        return emptyList()
    }

    override fun getPromotedPiece(coordinate: Coordinate, board: Board): Piece {
        return pieceFactory.createPromotedPiece(coordinate, ChessUtil.figureIds.random())
    }
}
