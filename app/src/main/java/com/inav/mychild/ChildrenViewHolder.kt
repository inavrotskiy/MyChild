package com.inav.mychild

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

class ChildrenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(myChild : MyChild, isActivated: Boolean = false){
        val sexImageView = itemView.findViewById<ImageView>(R.id.sex_ImageView)
        val nameTextView = itemView.findViewById<TextView>(R.id.name_myChild_card_TextView)
        val ageTextView = itemView.findViewById<TextView>(R.id.age_myChild_card_TextView)

        itemView.isActivated = isActivated

        val sexDrawableId = when (myChild.sex){
            Sex.BOY -> R.drawable.ic_boy_black_24dp
            Sex.GIRL -> R.drawable.ic_girl_black_24dp
        }
        sexImageView.setImageDrawable(itemView.context.getDrawable(sexDrawableId))

        nameTextView.text = myChild.name
        ageTextView.text = myChild.ageToString()
    }

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
        object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition(): Int = adapterPosition
            override fun getSelectionKey(): Long? = itemId
        }
}