package com.thinklearing.mem.TinderCard

import androidx.recyclerview.widget.DiffUtil

class SpotDiffCallback(
        private val old: List<Vocabulary>,
        private val new: List<Vocabulary>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition].word == new[newPosition].word
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition] == new[newPosition]
    }

}
