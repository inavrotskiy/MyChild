package com.inav.mychild

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView

class ChildrenRecyclerViewAdapter(private val children : List<MyChild>) : RecyclerView.Adapter<ChildrenViewHolder>() {
    private var tracker: SelectionTracker<Long>? = null
    private var lastSelected : View? = null
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

        // Unselect last selected item forcibly when new item is selected.
        if (tracker?.isSelected(position.toLong()) == true) {
            if (lastSelected != holder.itemView) {
                lastSelected?.isActivated = false
                lastSelected = holder.itemView
            }
        }

        tracker?.let {
            holder.bind(children[position], it.isSelected(position.toLong()))}
    }

    private fun deselectPrevItem(tracker: SelectionTracker<Long>?){

    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}