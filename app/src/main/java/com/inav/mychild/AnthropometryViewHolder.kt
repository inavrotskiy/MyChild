package com.inav.mychild

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AnthropometryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(anthropometry : Anthropometry){
        val dateTextView = itemView.findViewById<TextView>(R.id.date_anthropometry_card_TextView)
        val heightTextView = itemView.findViewById<TextView>(R.id.height_anthropometry_card_TextView)
        val weightTextView = itemView.findViewById<TextView>(R.id.weight_anthropometry_card_TextView)

        dateTextView.text = "Дата: ${anthropometry.date}"
        heightTextView.text = "Рост: ${anthropometry.height}"
        weightTextView.text = "Вес: ${anthropometry.weight}"
    }
}