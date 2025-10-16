package com.mcshr.wordloom.presentation.wordCardScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mcshr.wordloom.databinding.ItemTranslationBinding

class TranslationListAdapter :
    ListAdapter<String, TranslationViewHolder>(TranslationsDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TranslationViewHolder {
        val binding = ItemTranslationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TranslationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TranslationViewHolder,
        position: Int
    ) {
        holder.binding.tvTranslation.text = getItem(position)
    }
}

class TranslationViewHolder(val binding: ItemTranslationBinding) :
    RecyclerView.ViewHolder(binding.root)

class TranslationsDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return true
    }
}