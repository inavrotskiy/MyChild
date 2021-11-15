package com.inav.mychild

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.view.View

/*
* The class to draw graphs.
*/
internal class GraphView(context: Context, private val graphs: List<Graph>, private var legendPosition : PointF) : View(context) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        for (graph in graphs) {
            graph.draw(canvas)
            graph.drawLegend(canvas, legendPosition.also { legendPosition.y += GRAPH_LEGEND_SIZE })
        }
    }
}
