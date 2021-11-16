package com.inav.mychild

import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener

lateinit var graphFrame : FrameLayout

class GraphFragment : Fragment() {

    private var curChild : MyChild? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(CUR_CHILD) { key, bundle ->
            curChild = bundle.getSerializable(CUR_CHILD) as MyChild
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_graph, container, false)
        graphFrame = view.findViewById(R.id.graph_Frame)

        // Inflate the layout for this fragment
        return view
    }

    override fun onResume() {
        if (curChild != null)
        {
            val heightGraph = Graph(curChild!!.heightsAsPoints, "--рост", Color.GREEN, 8f, Color.LTGRAY, 5f)
            val weightGraph = Graph(curChild!!.weightsAsPoints, "--вес", Color.BLUE, 8f, Color.LTGRAY, 5f)
            val graphView = GraphView(graphFrame.context, listOf(heightGraph, weightGraph), PointF(50f,50f))
            graphFrame.addView(graphView)
        }

        super.onResume()
    }
}