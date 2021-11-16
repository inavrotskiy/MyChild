package com.inav.mychild

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AnthropometryRecyclerViewAdapter(private val anthropometries : List<Anthropometry>) : RecyclerView.Adapter<AnthropometryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnthropometryViewHolder {

        val anthropometryView = LayoutInflater.from(parent.context).inflate(R.layout.anthropometry_card, parent, false)
        return AnthropometryViewHolder(anthropometryView)
    }

    override fun onBindViewHolder(holder: AnthropometryViewHolder, position: Int) {
        val anthropometry = anthropometries[position]
        holder.bind(anthropometry)
    }

    override fun getItemCount(): Int {
        return anthropometries.size
    }
}