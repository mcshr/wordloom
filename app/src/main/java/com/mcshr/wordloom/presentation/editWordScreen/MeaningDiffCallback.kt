package com.mcshr.wordloom.presentation.editWordScreen

import androidx.recyclerview.widget.DiffUtil

class MeaningDiffCallback:DiffUtil.ItemCallback<String>() {
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}