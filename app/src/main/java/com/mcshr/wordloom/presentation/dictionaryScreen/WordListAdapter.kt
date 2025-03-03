package com.mcshr.wordloom.presentation.dictionaryScreen
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mcshr.wordloom.databinding.ItemWordListitemBinding
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.presentation.utils.getColor
import com.mcshr.wordloom.presentation.utils.getText

class WordListAdapter:ListAdapter<WordCard,WordViewHolder>(WordDiffCallback()) {
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = getItem(position)
        val statusColor = word.status.getColor(holder.binding.root.context)
        holder.binding.tvWordText.text = word.wordText
        holder.binding.tvMeaningList.text = word.wordTranslations.joinToString(", ") {
            it
        }
        holder.binding.tvWordStatus.text = word.status.getText()
        holder.binding.tvWordStatus.setTextColor(statusColor)
        holder.binding.statusIndicatorLong.backgroundTintList = ColorStateList.valueOf(statusColor)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemWordListitemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WordViewHolder(binding)
    }
}