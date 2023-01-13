package com.example.kursovaya.implementations.bot

import com.example.kursovaya.implementations.engine.Coordinate
import com.example.kursovaya.implementations.engine.Move
import com.example.kursovaya.interfaces.Board
import com.example.kursovaya.interfaces.Bot
import com.example.kursovaya.interfaces.Piece
import com.example.kursovaya.interfaces.PieceFactory
import com.example.kursovaya.utils.ChessUtil

/**
 * Простой бот, который ищет первый возможный ход
 */
class FirstBot(
    override val playerId: Int,
    private val pieceFactory: PieceFactory
) : Bot {

    override fun getNextTurn(board: Board): Collection<Move> {
        val pieceIterator = board.getPiecesForPlayer(playerId).iterator()
        while (pieceIterator.hasNext()) {
            val piece = pieceIterator.next()
            val validMoves = piece.getValidMoves(board)
            if (validMoves.isNotEmpty()) {
                return validMoves.first()
            }
        }
        return emptyList()
    }

    override fun getPromotedPiece(coordinate: Coordinate, board: Board): Piece {
        return pieceFactory.createPromotedPiece(coordinate, ChessUtil.queenId)
    }
}