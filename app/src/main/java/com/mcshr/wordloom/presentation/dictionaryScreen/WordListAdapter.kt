package com.mcshr.wordloom.presentation.dictionaryScreen
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mcshr.wordloom.databinding.ItemWordListitemBinding
import com.mcshr.wordloom.domain.entities.WordCard

class WordListAdapter:ListAdapter<WordCard,WordViewHolder>(WordDiffCallback()) {
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = getItem(position)
        //val statusColor = word.status.getColorId()
        holder.binding.tvWordText.text = word.wordText
        holder.binding.tvMeaningList.text = word.wordTranslations.toString()
        holder.binding.tvWordStatus.text = word.status.toString()
        //holder.binding.tvWordStatus.setTextColor(statusColor)
        //holder.binding.statusIndicatorLong.setBackgroundColor(statusColor)
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