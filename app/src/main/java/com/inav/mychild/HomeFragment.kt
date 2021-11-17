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

val children = listOf<MyChild>(
    MyChild("Лиза", LocalDate.of(2016, 9, 17), Sex.GIRL),
    MyChild("Степан", LocalDate.of(2013, 6, 23), Sex.BOY),
    MyChild("Егор", LocalDate.of(2011, 8, 20), Sex.BOY),
    MyChild("Вова", LocalDate.of(2002, 4, 22), Sex.BOY),
    MyChild("Женя", LocalDate.of(1998, 10, 16), Sex.BOY),
    MyChild("Мила", LocalDate.of(2017, 2, 10), Sex.GIRL)
)

lateinit var fragmentView: View
var curChildId = 0

class HomeFragment : Fragment() {

    var tracker: SelectionTracker<Long>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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

        tracker?.select(curChildId.toLong())
        (childrenRecyclerView.adapter as ChildrenRecyclerViewAdapter).setTracker(tracker)

        (childrenRecyclerView.layoutManager as LinearLayoutManager).scrollToPosition(curChildId)
    }

    /*
    * Returns id of the selected in tracker Child.
    * */
    private fun getSelectedChildId(tracker: SelectionTracker<Long>): Int {
        if (tracker?.selection!!.size() == 0)
            return 0

        return tracker?.selection!!.elementAt(0).toInt()
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