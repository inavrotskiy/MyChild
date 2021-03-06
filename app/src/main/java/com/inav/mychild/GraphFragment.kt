package com.inav.mychild

import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.setFragmentResultListener

private var graphView: GraphView? = null

class GraphFragment : Fragment() {

    private lateinit var graphFrame : FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(CUR_CHILD) { key, bundle ->
            graphView = createGraphView(bundle.getSerializable(CUR_CHILD) as MyChild)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_graph, container, false)
        graphFrame = view.findViewById(R.id.graph_Frame)
        return view
    }

    override fun onResume() {
        super.onResume()
        if ((graphFrame.childCount == 0) && (graphView != null)){
            graphFrame.addView(graphView)
        }
    }

    override fun onStop() {
        super.onStop()
        if (graphView != null)
            graphFrame.removeView(graphView)
    }

    /*
    * Creates GraphView with graphs for a specified  MyChild
    * */
    private fun createGraphView(child : MyChild) : GraphView{
        val lineWidth = 8f
        val nodeRadius = 5f

        val heightGraph = Graph(child.heightsAsPoints, "--рост", Color.GREEN, lineWidth, Color.LTGRAY, nodeRadius)
        val weightGraph = Graph(child.weightsAsPoints, "--вес", Color.BLUE, lineWidth, Color.LTGRAY, nodeRadius)

        return GraphView(graphFrame.context, listOf(heightGraph, weightGraph), PointF(50f,50f), child.name)
    }
}