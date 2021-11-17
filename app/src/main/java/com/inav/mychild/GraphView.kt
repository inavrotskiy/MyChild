package com.inav.mychild

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.view.View

/*
* The class to draw graphs.
*/
internal class GraphView(context: Context, private val graphs: List<Graph>, private val legendPosition : PointF) : View(context) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var legendY = legendPosition.y
        for (graph in graphs) {
            graph.draw(canvas)
            graph.drawLegend(canvas, PointF(legendPosition.x, legendY))
            legendY += GRAPH_LEGEND_SIZE
        }
    }
}
