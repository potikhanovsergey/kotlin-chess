package com.example.kursovaya.implementations.engine.factory

import android.graphics.Bitmap
import com.example.kursovaya.implementations.engine.Coordinate
import com.example.kursovaya.implementations.engine.PieceImageProvider
import com.example.kursovaya.implementations.engine.piece.*
import com.example.kursovaya.interfaces.Piece
import com.example.kursovaya.interfaces.PieceFactory
import com.example.kursovaya.utils.ChessUtil

class DefaultPieceFactory(
    private val playerId: Int,
    imageProvider: PieceImageProvider
) : PieceFactory {
    private val bishopBitmap: Bitmap? = imageProvider.bishopBitmap
    private val kingBitmap: Bitmap? = imageProvider.kingBitmap
    private val knightBitmap: Bitmap? = imageProvider.knightBitmap
    private val pawnBitmap: Bitmap? = imageProvider.pawnBitmap
    private val queenBitmap: Bitmap? = imageProvider.queenBitmap
    private val rookBitmap: Bitmap? = imageProvider.rookBitmap

    override fun createBishop(location: Coordinate): Bishop {
        return Bishop(location, playerId, bishopBitmap, false)
    }

    override fun createKing(location: Coordinate): King {
        return King(location, playerId, kingBitmap, false)
    }

    override fun createKnight(location: Coordinate): Knight {
        return Knight(location, playerId, knightBitmap, false)
    }

    override fun createPawn(location: Coordinate): Pawn {
        return Pawn(location, playerId, pawnBitmap, false)
    }

    override fun createQueen(location: Coordinate): Queen {
        return Queen(location, playerId, queenBitmap, false)
    }

    override fun createRook(location: Coordinate): Rook {
        return Rook(location, playerId, rookBitmap, false)
    }

    override fun createPromotedPiece(location: Coordinate, id: Int): Piece {
        return when (id) {
            ChessUtil.bishopId -> this.createBishop(location)
            ChessUtil.knightId -> this.createKnight(location)
            ChessUtil.queenId -> this.createQueen(location)
            ChessUtil.rookId -> this.createRook(location)
            ChessUtil.pawnId -> this.createPawn(location)
            else -> {
                return this.createPawn(location)
            }
        }
    }
}
