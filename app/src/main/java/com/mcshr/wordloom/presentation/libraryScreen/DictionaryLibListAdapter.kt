package com.mcshr.wordloom.presentation.libraryScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mcshr.wordloom.databinding.ItemDictionaryLibBinding
import com.mcshr.wordloom.domain.entities.DictionaryWithStats

class DictionaryLibListAdapter :
    ListAdapter<DictionaryWithStats, DictionaryLibViewHolder>(DictionaryLibDiffCallback()) {

    var openDictionary: ((dictionaryId: Long) -> Unit)? = null

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
        val progress = if(dictionaryWithStats.totalCountCards != 0) {
            (dictionaryWithStats.learnedCountCards + dictionaryWithStats.knownCountCards) /
                    dictionaryWithStats.totalCountCards  * 100
        } else {0}
        holder.binding.progressIndicator.progress = progress
        holder.binding.progressText.text = "$progress%"

        holder.binding.root.setOnClickListener {
            openDictionary?.invoke(dictionary.id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryLibViewHolder {
        val binding = ItemDictionaryLibBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DictionaryLibViewHolder(binding)
    }
}