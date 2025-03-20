package com.mcshr.wordloom.presentation.libraryScreen

import androidx.recyclerview.widget.DiffUtil
import com.mcshr.wordloom.domain.entities.DictionaryWithStats

class DictionaryLibDiffCallback: DiffUtil.ItemCallback<DictionaryWithStats>() {
    override fun areContentsTheSame(
        oldItem: DictionaryWithStats,
        newItem: DictionaryWithStats
    ): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(
        oldItem: DictionaryWithStats,
        newItem: DictionaryWithStats
    ): Boolean {
        return oldItem.dictionary.id == newItem.dictionary.id
    }

}