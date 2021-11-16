package com.inav.mychild

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView

class ChildrenRecyclerViewAdapter(private val children : List<MyChild>) : RecyclerView.Adapter<ChildrenViewHolder>() {

    private var tracker: SelectionTracker<Long>? = null
    init {
        setHasStableIds(true)
    }

    fun setTracker(tracker: SelectionTracker<Long>?) {
        this.tracker = tracker
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildrenViewHolder {
        val childrenView = LayoutInflater.from(parent.context).inflate(R.layout.mychild_card, parent, false)
        return ChildrenViewHolder(childrenView)
    }

    override fun onBindViewHolder(holder: ChildrenViewHolder, position: Int) {
        val myChild = children[position]
        tracker?.let {
            holder.bind(myChild, it.isSelected(position.toLong()))
        }
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}