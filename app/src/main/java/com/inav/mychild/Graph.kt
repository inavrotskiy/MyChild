package com.inav.mychild

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

const val GRAPH_LEGEND_SIZE = 48f
const val GRAPH_PADDING = 48f

/*
* The class representing graph.
* */
internal class Graph(
    points: List<PointF>,
    private val legend: String,
    lineColor: Int, lineWidth: Float,
    nodeColor: Int, private val nodeRadius: Float
) {

    private lateinit var sortedPoints: List<PointF>
    private var maxX = 0f
    private var maxY = 0f
    private val linePaint = Paint()
    private val nodePaint = Paint()

    init {
        if (points.isNotEmpty()) {
            sortedPoints = points.sortedBy { it.x }

            // Points are sorted by X, so just can take last point for max X
            maxX = sortedPoints[sortedPoints.size - 1].x
            maxY = sortedPoints.maxOf { it.y }

            linePaint.color = lineColor
            linePaint.strokeWidth = lineWidth
            linePaint.textSize = GRAPH_LEGEND_SIZE

            nodePaint.color = nodeColor
        }
    }

    /*
    * Draws graph at a specified canvas.
    * */
    fun draw(canvas: Canvas?) {
        if ((canvas == null) || sortedPoints.isEmpty())
            return

        val graphWidth = maxX
        val graphHeight = maxY
        val canvasWidth = canvas.width - (GRAPH_PADDING * 2)
        val canvasHeight = canvas.height - (GRAPH_PADDING * 2)

        // scale our graph to fit in canvas
        val ratioX = canvasWidth / graphWidth
        val ratioY = canvasHeight / graphHeight

        var startX = (sortedPoints[0].x) * ratioX + GRAPH_PADDING
        var startY = (sortedPoints[0].y) * ratioY - GRAPH_PADDING
        for (i in 1 until sortedPoints.size) {
            val endX = ((sortedPoints[i].x) * ratioX) + GRAPH_PADDING
            val endY = ((sortedPoints[i].y) * ratioY) - GRAPH_PADDING

            // Canvas origin (0,0) is at the top left corner,
            // but I want my graph start at bottom left corner, so flip Y-coordinates.
            // Draw graph edge
            canvas.drawLine(startX, canvasHeight - startY, endX, canvasHeight - endY, linePaint)
            // Draw graph node
            canvas.drawCircle(startX, canvasHeight - startY, nodeRadius, nodePaint)

            startX = endX
            startY = endY
        }
        // Draw last node
        canvas.drawCircle(startX, canvasHeight - startY, nodeRadius, nodePaint)
    }

    /*
    * Draws legend text of the graph at a specified position.
    * */
    fun drawLegend(canvas: Canvas?, x: Float, y: Float) {
        if (canvas == null)
            return
        canvas.drawText(legend, x, y, linePaint)
    }
}