package com.mcshr.wordloom.presentation.homeScreen.manageCardsForSession

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.mcshr.wordloom.R
import com.mcshr.wordloom.databinding.ItemWordListitemBinding
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.entities.WordStatus
import com.mcshr.wordloom.presentation.utils.getColor
import com.mcshr.wordloom.presentation.utils.getText

class FilteredWordListAdapter : ListAdapter<WordCard, FilteredWordViewHolder>(
    FilteredWordDiffCallback()
) {
    var onWordClick: ((WordCard) -> Unit)? = null

    override fun onBindViewHolder(holder: FilteredWordViewHolder, position: Int) {
        val word = getItem(position)
        holder.binding.tvWordText.text = word.wordText
        holder.binding.tvMeaningList.text = word.wordTranslations.joinToString(", ") {
            it
        }
        val context = holder.binding.root.context
        val statusColor = word.status.getColor(holder.binding.root.context)
        holder.binding.tvWordStatus.text = word.status.getText(context)
        holder.binding.tvWordStatus.setTextColor(statusColor)
        holder.binding.statusIndicatorLong.backgroundTintList = ColorStateList.valueOf(statusColor)
        val background = if (word.status == WordStatus.READY_TO_LEARN) {
            R.drawable.card_with_border_selected_purple
        } else {
            R.drawable.card_with_border
        }
        holder.binding.container.background = ContextCompat.getDrawable(context, background)
        holder.binding.root.setOnClickListener {
            onWordClick?.invoke(word)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilteredWordViewHolder {
        val binding = ItemWordListitemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FilteredWordViewHolder(binding)
    }

}
