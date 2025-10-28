package com.mcshr.wordloom.presentation.libraryScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mcshr.wordloom.databinding.ItemDictionaryLibBinding
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.entities.DictionaryWithStats
import com.mcshr.wordloom.presentation.utils.setDebounceOnClickListener

class DictionaryLibListAdapter(
    private val onItemClick: (dictionaryId: Long) -> Unit,
    private val onMenuClick: (dictionary: Dictionary) -> Unit
) :
    ListAdapter<DictionaryWithStats, DictionaryLibViewHolder>(DictionaryLibDiffCallback()) {
    override fun onBindViewHolder(holder: DictionaryLibViewHolder, position: Int) {
        val dictionaryWithStats = getItem(position)
        val dictionary = dictionaryWithStats.dictionary
        holder.binding.textViewDictionaryName.text = dictionary.name
        holder.binding.tvLanguage.text = String.format(
            "%s/%s", dictionary.languageOriginal.name, dictionary.languageTranslation.name
        )
        holder.binding.tvTotalCount.text = dictionaryWithStats.totalCountCards.toString()
        holder.binding.tvLearnedCount.text = dictionaryWithStats.learnedCountCards.toString()
        holder.binding.tvLearningCount.text = dictionaryWithStats.learningCountCards.toString()
        val progress = dictionaryWithStats.learningProgress
        holder.binding.progressIndicator.progress = progress
        holder.binding.progressText.text = "$progress%"

        holder.binding.root.setOnClickListener {
            onItemClick(dictionary.id)
        }
        holder.binding.btnMenu.setDebounceOnClickListener {
            onMenuClick(dictionary)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryLibViewHolder {
        val binding = ItemDictionaryLibBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DictionaryLibViewHolder(binding)
    }
}