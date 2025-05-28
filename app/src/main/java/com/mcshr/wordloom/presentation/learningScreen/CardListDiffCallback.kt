package com.mcshr.wordloom.presentation.learningScreen

import androidx.recyclerview.widget.DiffUtil
import com.mcshr.wordloom.domain.entities.WordCard

class CardListDiffCallback(
    private val oldList: List<WordCard>,
    private val newList: List<WordCard>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}