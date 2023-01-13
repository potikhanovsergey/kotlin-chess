package com.example.kursovaya.implementations.engine.game

import com.example.kursovaya.utils.ChessUtil
import com.example.kursovaya.implementations.engine.*
import com.example.kursovaya.implementations.engine.piece.Pawn
import com.example.kursovaya.interfaces.Board
import com.example.kursovaya.interfaces.Game
import com.example.kursovaya.interfaces.Piece
import java.util.*

class DefaultGame(
    initialBoard: Board,
    initialPlayerTurn: Int
) : Game {
    private var currentBoard: Board = initialBoard
    private var currentPlayer: Int = initialPlayerTurn
    private var turnHistory: ArrayDeque<Turn> = ArrayDeque()
    private var promotablePawnCoordinate: Coordinate? = null

    override fun getCurrentBoard(): Board {
        return currentBoard
    }

    override fun getTurnHistory(): Collection<Turn> {
        return turnHistory
    }

    override fun applyMoves(moves: Collection<Move>): Turn {
        val removedPieces = getRemovedPieces(moves)
        currentBoard = currentBoard.applyMoves(moves)
        val turn = Turn(
            moves,
            removedPieces
        )
        turnHistory.push(turn)
        nextTurn()
        return turn
    }

    override fun canUndo(): Boolean {
        return turnHistory.isNotEmpty()
    }

    override fun undo(): Turn {
        if (turnHistory.isNotEmpty()) {
            val turn = turnHistory.pop()
            currentBoard = turn.moves.fold(currentBoard) { board, move ->
                board.movePiece(
                    move.origin,
                    move.piece
                )
            }
            currentBoard = turn.removedPieces.fold(currentBoard) { board, piece ->
                board.movePiece(
                    piece.location,
                    piece
                )
            }
            changePlayer()
            return turn
        } else {
            throw Exception()
        }
    }

    override fun playerTurn(): Int {
        return currentPlayer
    }

    override fun checkmatedPlayerId(): Int? {
        val hasNoMoves = !currentBoard.getPiecesForPlayer(currentPlayer)
            .any { piece -> piece.getValidMoves(currentBoard).isNotEmpty() }
        return if (hasNoMoves && currentBoard.isKingInCheck(currentPlayer)) {
            currentPlayer
        } else {
            null
        }
    }

    override fun isStalemate(): Boolean {
        val hasNoMoves = !currentBoard.getPiecesForPlayer(currentPlayer)
            .any { piece -> piece.getValidMoves(currentBoard).isNotEmpty() }
        return hasNoMoves && !currentBoard.isKingInCheck(currentPlayer)
    }

    override fun getPromotablePawnCoordinate(): Coordinate? {
        return promotablePawnCoordinate
    }

    override fun promotePawn(promotedPiece: Piece): Turn {
        val turn = applyMoves(
            listOf(
                Move(
                    promotedPiece.location,
                    promotedPiece
                )
            )
        )
        promotablePawnCoordinate = null
        return turn
    }

    private fun getRemovedPieces(moves: Collection<Move>): Collection<Piece> {
        return moves.mapNotNull { move ->
            val captureLocation = move.captureLocation(currentBoard)
            currentBoard.getPiece(captureLocation)
        }
    }

    /**
     * проверяет события:
     * * повышение пешки
     * * мат для следующего игрока
     * * иначе - передача хода
     */
    private fun nextTurn() {
        val promotablePiece = findPromotablePawn()
        when {
            promotablePiece != null ->
                promotablePawnCoordinate = promotablePiece.location

            else -> changePlayer()
        }
    }

    /**
     * возвращает пешку для повышения для текущего игрока, если находит ее,
     */
    private fun findPromotablePawn(): Piece? {
        return currentBoard.getPiecesForPlayer(currentPlayer)
            .find { piece ->
                val y = piece.location.y
                val isPawn = piece is Pawn
                val atFinalRow = (currentPlayer == 0 && y == 0) || (currentPlayer == 1 && y == 7)
                isPawn && atFinalRow
            }
    }

    private fun changePlayer() {
        currentPlayer = otherPlayerId()
    }

    private fun otherPlayerId(): Int {
        return ChessUtil.getOtherPlayerId(currentPlayer)
    }
}
