package com.inav.mychild

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView

class ChildrenRecyclerViewAdapter(private val children : List<MyChild>) : RecyclerView.Adapter<ChildrenViewHolder>() {
    private var tracker: SelectionTracker<Long>? = null
    private var lastSelectedView : View? = null
    init {
        setHasStableIds(true)
    }

    /*
    * Sets a specified tracker.
    * */
    fun setTracker(tracker: SelectionTracker<Long>?) {
        this.tracker = tracker
    }

    /*
    * Unselects last selected view item.
    * */
    fun unselectLastSelectedView(){
        lastSelectedView?.isActivated = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildrenViewHolder {
        val childrenView = LayoutInflater.from(parent.context).inflate(R.layout.mychild_card, parent, false)
        return ChildrenViewHolder(childrenView)
    }

    override fun onBindViewHolder(holder: ChildrenViewHolder, position: Int) {
        tracker?.let {
            holder.bind(children[position], it.isSelected(position.toLong()))}

        // Remember last selected view
        if (holder.itemView.isActivated)
            lastSelectedView = holder.itemView
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}