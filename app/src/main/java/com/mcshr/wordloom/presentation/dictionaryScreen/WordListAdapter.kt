package com.mcshr.wordloom.presentation.dictionaryScreen

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mcshr.wordloom.databinding.ItemWordListitemBinding
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.presentation.utils.getColor
import com.mcshr.wordloom.presentation.utils.getText
import com.mcshr.wordloom.presentation.utils.setDebounceOnClickListener

class WordListAdapter(
    private val onMenuClick: (WordCard) -> Unit,
    private val onItemClick: (Long) -> Unit
) : ListAdapter<WordCard, WordViewHolder>(WordDiffCallback()) {
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val wordCard = getItem(position)
        val statusColor = wordCard.status.getColor(holder.binding.root.context)
        with(holder.binding){
            tvWordText.text = wordCard.wordText
            tvMeaningList.text = wordCard.wordTranslations.joinToString(", ") {
                it
            }
            tvWordStatus.text = wordCard.status.getText(holder.binding.root.context)
            tvWordStatus.setTextColor(statusColor)
            statusIndicatorLong.backgroundTintList = ColorStateList.valueOf(statusColor)

            btnMore.setDebounceOnClickListener {
                onMenuClick(wordCard)
            }
            root.setOnClickListener {
                onItemClick(wordCard.id)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemWordListitemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return WordViewHolder(binding)
    }
}