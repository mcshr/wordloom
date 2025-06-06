package com.mcshr.wordloom.presentation.editWordScreen.selectDictionary

import androidx.recyclerview.widget.DiffUtil
import com.mcshr.wordloom.domain.entities.Dictionary

class DictionarySelectableDiffCallback: DiffUtil.ItemCallback<Dictionary>() {
    override fun areContentsTheSame(oldItem: Dictionary, newItem: Dictionary): Boolean {
        return true
    }

    override fun areItemsTheSame(oldItem: Dictionary, newItem: Dictionary): Boolean {
        return oldItem.id == newItem.id
    }
}