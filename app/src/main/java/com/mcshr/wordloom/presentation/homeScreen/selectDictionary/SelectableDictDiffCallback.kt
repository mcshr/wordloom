package com.mcshr.wordloom.presentation.homeScreen.selectDictionary

import androidx.recyclerview.widget.DiffUtil
import com.mcshr.wordloom.domain.entities.Dictionary

class SelectableDictDiffCallback:DiffUtil.ItemCallback<Dictionary>() {
    override fun areContentsTheSame(oldItem: Dictionary, newItem: Dictionary): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Dictionary, newItem: Dictionary): Boolean {
        return oldItem.id == newItem.id
    }
}