package com.inav.mychild

import android.content.Context
import android.graphics.*
import android.view.View

/*
* The class to draw graphs.
*/
internal class GraphView(
    context: Context,
    private val graphs: List<Graph>,
    private val legendPosition: PointF,
    private val title: String = ""
) : View(context) {

    private val titlePaint = Paint()
    init{
        titlePaint.textSize = 60f
        titlePaint.typeface = Typeface.DEFAULT_BOLD
        titlePaint.color = context.getColor(R.color.purple_200)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null)
            return

        val textWidth = titlePaint.measureText(title)
        val x = (width - textWidth) / 2
        canvas.drawText(title, x, 50f, titlePaint)

        var y = legendPosition.y
        for (graph in graphs) {
            graph.draw(canvas)
            graph.drawLegend(canvas, legendPosition.x, y.also { y+= GRAPH_LEGEND_SIZE})
        }
    }
}
