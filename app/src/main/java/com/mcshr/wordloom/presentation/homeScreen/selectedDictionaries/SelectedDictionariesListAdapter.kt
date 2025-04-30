package com.mcshr.wordloom.presentation.homeScreen.selectedDictionaries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mcshr.wordloom.databinding.ItemDictionaryActiveBinding
import com.mcshr.wordloom.domain.entities.DictionaryWithStats

class SelectedDictionariesListAdapter :
    ListAdapter<DictionaryWithStats, SelectedDictionaryViewHolder>(
        SelectedDictionaryDiffCallback()
    ) {

    var onDictionaryClick: ((Long) -> Unit)? = null

    override fun onBindViewHolder(holder: SelectedDictionaryViewHolder, position: Int) {
        val dictionaryWithStats = getItem(position)
        val dictionary = dictionaryWithStats.dictionary

        holder.binding.root.setOnClickListener {
            onDictionaryClick?.invoke(dictionary.id)
        }
        holder.binding.tvDictionaryName.text = dictionary.name
        holder.binding.tvDictLanguage.text = String.format(
            "%s/%s",
            dictionary.languageOriginal.name,
            dictionary.languageTranslation.name
        )
        holder.binding.tvDictTotalCount.text = dictionaryWithStats.totalCountCards.toString()
        holder.binding.tvDictReadyToLearnCount.text =
            dictionaryWithStats.readyToLearnCountCards.toString()
        holder.binding.tvDictLearningCount.text = dictionaryWithStats.learningCountCards.toString()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectedDictionaryViewHolder {
        val binding = ItemDictionaryActiveBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SelectedDictionaryViewHolder(binding)
    }
}