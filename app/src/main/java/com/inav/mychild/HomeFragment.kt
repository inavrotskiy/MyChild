package com.inav.mychild

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
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

var curChild = children[0]

class HomeFragment : Fragment() {

    var tracker: SelectionTracker<Long>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val childrenRecyclerView = view.findViewById<RecyclerView>(R.id.children_recyclerView)
        childrenRecyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        childrenRecyclerView.adapter = ChildrenRecyclerViewAdapter(children)

        tracker = SelectionTracker.Builder<Long>(
            "childrenRecyclerSelection",
            childrenRecyclerView,
            ItemIdKeyProvider(childrenRecyclerView),
            ItemLookup(childrenRecyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectSingleAnything()
        ).build()

        tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    if(tracker?.selection!!.size() > 0)
                    {
                        val id = tracker?.selection!!.elementAt(0)
                        curChild = children[id.toInt()]

                        setFragmentResult(CUR_CHILD, bundleOf(Pair(CUR_CHILD, curChild)))

                        // TODO: refactor
                        val anthropometryRecyclerView : RecyclerView = view.findViewById(R.id.anthropometries_recyclerView)
                        anthropometryRecyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
                        anthropometryRecyclerView.adapter = AnthropometryRecyclerViewAdapter(
                            curChild.anthropometries
                        )
                    }
                }
            }
        )

        tracker?.select(0)

        (childrenRecyclerView.adapter as ChildrenRecyclerViewAdapter).setTracker(tracker)

        // Inflate the layout for this fragment
        return view
    }

    inner class ItemLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
        override fun getItemDetails(event: MotionEvent) : ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(event.x, event.y) ?: return null
            val viewHolder = recyclerView.getChildViewHolder(view) as ChildrenViewHolder
            return viewHolder?.getItemDetails()
        }
    }

    inner class ItemIdKeyProvider(private val recyclerView: RecyclerView)
        : ItemKeyProvider<Long>(SCOPE_MAPPED) {

        override fun getKey(position: Int): Long? {
            return recyclerView.adapter?.getItemId(position)
                ?: throw IllegalStateException("RecyclerView adapter is not set!")
        }

        override fun getPosition(key: Long): Int {
            val viewHolder = recyclerView.findViewHolderForItemId(key)
            return viewHolder?.layoutPosition ?: RecyclerView.NO_POSITION
        }
    }
}