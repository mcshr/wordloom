package com.mcshr.wordloom.presentation.dictionaryScreen

import androidx.recyclerview.widget.DiffUtil
import com.mcshr.wordloom.domain.entities.WordCard

class WordDiffCallback:DiffUtil.ItemCallback<WordCard>() {
    override fun areContentsTheSame(oldItem: WordCard, newItem: WordCard): Boolean {
       return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: WordCard, newItem: WordCard): Boolean {
        return oldItem.id == newItem.id
    }
}