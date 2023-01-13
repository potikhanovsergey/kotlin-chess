package com.example.kursovaya

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kursovaya.implementations.engine.*
import com.example.kursovaya.implementations.engine.factory.DefaultGameFactory
import com.example.kursovaya.interfaces.*

abstract class ChessGameActivity : AppCompatActivity() {
    private var gameFactory: GameFactory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeGameFactory()

        val chessView = ChessBoardView(this)

        val controller = createController()
        controller.addViewModelListener(chessView)
        chessView.addViewListener(object : ChessBoardViewListener {
            override fun onCoordinateSelected(coordinate: Coordinate) {
                val playerTurn = controller.playerTurn()
                controller.selectCoordinate(coordinate)
                val promotablePawnCoordinate = controller.getPromotablePawnCoordinate()
                val playerTurnUnchanged = controller.playerTurn() == playerTurn
                if (promotablePawnCoordinate != null && playerTurnUnchanged) {
                    launchPawnPromotionDialog(controller, promotablePawnCoordinate)
                }
            }
        })
        setContentView(chessView)
    }

    private fun initializeGameFactory() {
        val blackImageProvider = PieceImageProvider(
            BitmapFactory.decodeResource(resources, R.drawable.black_bishop),
            BitmapFactory.decodeResource(resources, R.drawable.black_king),
            BitmapFactory.decodeResource(resources, R.drawable.black_knight),
            BitmapFactory.decodeResource(resources, R.drawable.black_pawn),
            BitmapFactory.decodeResource(resources, R.drawable.black_queen),
            BitmapFactory.decodeResource(resources, R.drawable.black_rook)
        )
        val whiteImageProvider = PieceImageProvider(
            BitmapFactory.decodeResource(resources, R.drawable.white_bishop),
            BitmapFactory.decodeResource(resources, R.drawable.white_king),
            BitmapFactory.decodeResource(resources, R.drawable.white_knight),
            BitmapFactory.decodeResource(resources, R.drawable.white_pawn),
            BitmapFactory.decodeResource(resources, R.drawable.white_queen),
            BitmapFactory.decodeResource(resources, R.drawable.white_rook)
        )
        gameFactory = DefaultGameFactory(
            whiteImageProvider,
            blackImageProvider
        )
    }

    protected fun getGameFactory(): GameFactory {
        return gameFactory!!
    }

    private fun createController(): GameController {
        return buildController(getGameFactory().createNewGame())
    }

    protected abstract fun buildController(game: Game): GameController

    private fun launchPawnPromotionDialog(
        controller: GameController,
        coordinate: Coordinate
    ) {
        AlertDialog.Builder(this)
            .setTitle(R.string.promote_pawn)
            .setItems(
                R.array.promotion_options
            ) { _, which ->
                val pieceFactory = getPieceFactory(controller.playerTurn())
                val piece = pieceFactory.createPromotedPiece(coordinate, which)
                controller.promotePawn(piece)
            }
            .setCancelable(false)
            .create()
            .show()
    }

    private fun getPieceFactory(playerId: Int): PieceFactory {
        return if (playerId == 0) {
            getGameFactory().whitePieceFactory
        } else {
            getGameFactory().blackPieceFactory
        }
    }
}
