package com.mcshr.wordloom.presentation.homeScreen.manageCardsForSession

import androidx.recyclerview.widget.DiffUtil
import com.mcshr.wordloom.domain.entities.WordCard

class FilteredWordDiffCallback: DiffUtil.ItemCallback<WordCard>() {
    override fun areContentsTheSame(oldItem: WordCard, newItem: WordCard): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: WordCard, newItem: WordCard): Boolean {
        return oldItem.id == newItem.id
    }
}