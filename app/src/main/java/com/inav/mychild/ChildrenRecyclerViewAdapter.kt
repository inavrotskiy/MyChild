package com.inav.mychild

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ChildrenRecyclerViewAdapter(private val children : List<MyChild>) : RecyclerView.Adapter<ChildrenViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildrenViewHolder {

        val childrenView = LayoutInflater.from(parent.context).inflate(R.layout.mychild_card, parent, false)
        return ChildrenViewHolder(childrenView)
    }

    override fun onBindViewHolder(holder: ChildrenViewHolder, position: Int) {
        val myChild = children[position]
        holder.bind(myChild)
    }

    override fun getItemCount(): Int {
        return children.size
    }
}