package com.inav.mychild

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class HomeFragment : Fragment() {
    lateinit var fragmentView: View
    var tracker: SelectionTracker<Long>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentView = inflater.inflate(R.layout.fragment_home, container, false)

        val childrenRecyclerView = fragmentView.findViewById<RecyclerView>(R.id.children_recyclerView)
        childrenRecyclerView.layoutManager = LinearLayoutManager(fragmentView.context, LinearLayoutManager.HORIZONTAL, false)
        childrenRecyclerView.adapter = ChildrenRecyclerViewAdapter(children)

        initSelectionTracker(childrenRecyclerView)

        // Inflate the layout for this fragment
        return fragmentView
    }

    private fun initSelectionTracker(childrenRecyclerView: RecyclerView) {
        tracker =
            ChildrenRecyclerTrackerFactory.create("childrenRecyclerSelection", childrenRecyclerView)
        tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()

                    curChildId = getSelectedChildId(tracker!!)
                    val curChild = children[curChildId]
                    createAnthropometriesRecyclerView(curChild.anthropometries, fragmentView)

                    // Store current Child for graphs fragment
                    setFragmentResult(CUR_CHILD, bundleOf(Pair(CUR_CHILD, curChild)))
                }
            }
        )

        (childrenRecyclerView.layoutManager as LinearLayoutManager).scrollToPosition(curChildId)
        tracker?.select(curChildId.toLong())
        (childrenRecyclerView.adapter as ChildrenRecyclerViewAdapter).setTracker(tracker)
    }

    /*
    * Returns id of the selected in tracker Child.
    * */
    private fun getSelectedChildId(tracker: SelectionTracker<Long>): Int {
        if (tracker.selection.size() != 1){
            tracker.select(curChildId.toLong())
        }

        return tracker.selection.elementAt(0).toInt()
    }

    /*
    * Creates recycler view with specified anthropometries.
    * */
    private fun createAnthropometriesRecyclerView(anthropometries : List<Anthropometry>, fragmentView: View){
        val anthropometryRecyclerView: RecyclerView = fragmentView.findViewById(R.id.anthropometries_recyclerView)
        anthropometryRecyclerView.layoutManager = LinearLayoutManager(fragmentView.context, LinearLayoutManager.VERTICAL, false)
        anthropometryRecyclerView.adapter = AnthropometryRecyclerViewAdapter(anthropometries)
    }
}