package com.example.kursovaya

import android.app.AlertDialog
import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.kursovaya.implementations.engine.Coordinate
import com.example.kursovaya.interfaces.ChessBoardViewListener
import com.example.kursovaya.interfaces.GameEventListener
import com.example.kursovaya.utils.ChessUtil
import kotlin.math.min

private data class Tile(val rect: Rect, val paint: Paint)

class ChessBoardView(
    context: Context
) : SurfaceView(context), GameEventListener {

    private var tileLength: Int = 0
    private var boardLength: Int = 0
    private var boardTopX: Int = 0
    private var boardTopY: Int = 0
    private var tiles = arrayListOf<Tile>()
    private val coordinateToRect = mutableMapOf<Coordinate, Rect>()

    private val viewListeners = arrayListOf<ChessBoardViewListener>()
    private var viewModel: GameViewModel? = null

    init {
        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(p0: SurfaceHolder) {
                computeSizes()
                preComputeTiles()
                draw()
            }

            override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
            }

            override fun surfaceDestroyed(p0: SurfaceHolder) {
            }
        })
    }

    fun addViewListener(viewListener: ChessBoardViewListener) {
        viewListeners.add(viewListener)
    }

    override fun onViewModelChange(gameViewModel: GameViewModel) {
        viewModel = gameViewModel
        draw()
    }

    override fun onStalemate() {
        AlertDialog.Builder(context)
            .setMessage(R.string.game_stalemate)
            .setCancelable(false)
            .create()
            .show()
    }

    override fun onCheckmate(checkedPlayerId: Int) {
        val playerName = if (checkedPlayerId == 1) {
            context.getString(R.string.player_name_0)
        } else {
            context.getString(R.string.player_name_1)
        }

        AlertDialog.Builder(context)
            .setMessage(
                context.getString(R.string.game_checkmate, playerName)
            )
            .setCancelable(false)
            .create()
            .show()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_POINTER_DOWN,
                MotionEvent.ACTION_DOWN -> {
                    val coordinate = touchCoordinatesToCoordinate(event.x.toInt(), event.y.toInt())
                    if (coordinate != null) {
                        viewListeners.forEach { listener ->
                            listener.onCoordinateSelected(coordinate)
                        }
                    }
                    performClick()
                }
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    /**
     * Возвращает коодинату доски по x/y нажатия,
     */
    private fun touchCoordinatesToCoordinate(x: Int, y: Int): Coordinate? {
        val relativeX = x - boardTopX
        val relativeY = y - boardTopY
        if (relativeX < 0 || relativeY < 0) return null

        val coordinateX = relativeX / tileLength
        val coordinateY = relativeY / tileLength
        val xInBounds = coordinateX >= 0 && coordinateX < ChessUtil.tilesPerSide
        val yInBounds = coordinateY >= 0 && coordinateY < ChessUtil.tilesPerSide
        return if (xInBounds && yInBounds) {
            Coordinate(coordinateX, coordinateY)
        } else {
            null
        }
    }

    private fun draw() {
        if (holder.surface.isValid) {
            val canvas = holder.lockCanvas()

            drawBackground(canvas)
            drawBoard(canvas)
            drawOverlay(canvas)
            drawPieces(canvas)

            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun drawBackground(canvas: Canvas) {
        canvas.drawRGB(0, 0, 0)
    }

    private fun drawBoard(canvas: Canvas) {
        for (tile in tiles) {
            canvas.drawRect(tile.rect, tile.paint)
        }
        drawLabels(canvas)
    }

    private fun drawLabels(canvas: Canvas) {
        var c = 'a'
        val paint = Paint()
        paint.textSize = tileLength * 0.5f
        paint.setARGB(255, 255, 255, 255)
        paint.isAntiAlias = true
        paint.textAlign = Paint.Align.CENTER
        for (z in 0 until ChessUtil.tilesPerSide) {
            canvas.drawText(
                (ChessUtil.tilesPerSide - z).toString(),
                tileLength / 4f,
                (boardTopY + (z + 0.75f) * tileLength),
                paint
            )
            canvas.drawText(
                c.toString(),
                (boardTopX + (z + 0.5f) * tileLength),
                (boardTopY + boardLength + tileLength / 2f),
                paint
            )
            c++
        }
    }

    /**
     * рисует возможные ходы для выбранной фигуры
     */
    private fun drawOverlay(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.argb(100, 10, 10, 120)

        viewModel?.validDestinations?.forEach { destination ->
            canvas.drawRect(
                coordinateToRect[destination]!!,
                paint
            )
        }
    }

    private fun drawPieces(canvas: Canvas) {
        viewModel?.pieces?.forEach { piece ->
            if (piece.image != null) {
                canvas.drawBitmap(
                    piece.image!!,
                    null,
                    coordinateToRect[piece.location]!!,
                    null
                )
            }
        }
    }

    private fun computeSizes() {
        val point = Point()
        display?.getRealSize(point)
        val width = point.x
        val height = point.y
        tileLength = min(width, height) / (ChessUtil.tilesPerSide + 1)
        boardLength = ChessUtil.tilesPerSide * tileLength
        boardTopX = tileLength / 2
        boardTopY = tileLength / 2
        tiles = arrayListOf()
    }

    private fun preComputeTiles() {
        val tileColor1 = Color.rgb(245, 222, 179)
        val paint1 = Paint()
        paint1.color = tileColor1
        val tileColor2 = Color.rgb(222, 184, 135)
        val paint2 = Paint()
        paint2.color = tileColor2
        val tileRange = 0 until ChessUtil.tilesPerSide
        for (row in tileRange) {
            for (col in tileRange) {
                val left = boardTopX + (row * tileLength)
                val top = boardTopY + (col * tileLength)
                val right = left + tileLength
                val bottom = top + tileLength
                val rect = Rect(left, top, right, bottom)
                val paint = if ((row + col) % 2 == 0) {
                    paint1
                } else {
                    paint2
                }
                tiles.add(Tile(rect, paint))
                val coordinate = Coordinate(row, col)
                coordinateToRect[coordinate] = rect
            }
        }
    }
}
