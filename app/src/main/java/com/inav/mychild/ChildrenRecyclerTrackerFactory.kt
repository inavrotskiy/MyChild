package com.inav.mychild

import android.view.MotionEvent
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.RecyclerView

internal class ChildrenRecyclerTrackerFactory {
    companion object{
        fun create (id: String, recyclerView: RecyclerView) : SelectionTracker<Long>{
            return SelectionTracker.Builder(
                id,
                recyclerView,
                ItemIdKeyProvider(recyclerView),
                ItemLookup(recyclerView),
                StorageStrategy.createLongStorage()
            ).withSelectionPredicate(
                SelectionPredicates.createSelectSingleAnything()
            ).build()
        }
    }
}

/*
* Lookup class
* */
private class ItemLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
    override fun getItemDetails(event: MotionEvent) : ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y) ?: return null
        val viewHolder = recyclerView.getChildViewHolder(view) as ChildrenViewHolder
        return viewHolder.getItemDetails()
    }
}

/*
* KeyProvider class
* */
private class ItemIdKeyProvider(private val recyclerView: RecyclerView)
    : ItemKeyProvider<Long>(SCOPE_MAPPED) {
    override fun getKey(position: Int): Long {
        return recyclerView.adapter?.getItemId(position)
            ?: throw IllegalStateException("RecyclerView adapter is not set!")
    }

    override fun getPosition(key: Long): Int {
        val viewHolder = recyclerView.findViewHolderForItemId(key)
        return viewHolder?.layoutPosition ?: RecyclerView.NO_POSITION
    }
}

