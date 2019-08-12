package com.rafaelfelipeac.mountains.others.helper

import androidx.recyclerview.widget.RecyclerView

interface ActionCompletionContract {
    fun onViewMoved(oldPosition: Int, newPosition: Int)

    fun onViewSwiped(position: Int, direction: Int, holder: RecyclerView.ViewHolder)
}