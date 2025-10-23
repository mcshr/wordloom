package com.mcshr.wordloom.presentation.learningScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mcshr.wordloom.databinding.ItemCardBinding
import com.mcshr.wordloom.domain.entities.PartOfSpeech
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.presentation.utils.getColor
import com.mcshr.wordloom.presentation.utils.getText
import com.mcshr.wordloom.presentation.utils.toResId

class CardStackAdapter() : RecyclerView.Adapter<CardViewHolder>() {
    var topPosition = 0

    var cardList = listOf<WordCard>()
        set(value) {
            val callback = CardListDiffCallback(cardList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    var toggleCardStackSwipe: ((canSwipe: Boolean) -> Unit)? = null
    private var currentHolder: CardViewHolder? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        val binding = ItemCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CardViewHolder,
        position: Int
    ) {
        val wordCard = cardList[position]
        val adapter = UsageExamplesAdapter()
        val context = holder.binding.root.context
        with(holder.binding) {
            tvWordText.text = wordCard.wordText
            if (wordCard.partOfSpeech != PartOfSpeech.EMPTY) {
                tvPartOfSpeech.setText(wordCard.partOfSpeech.toResId())
                tvPartOfSpeech.visibility = View.VISIBLE
            } else {
                tvPartOfSpeech.visibility = View.GONE
            }
            tvWordTranslation.text = wordCard.wordTranslations.joinToString("\n") {
                it
            }
            adapter.submitList(wordCard.usageExamples)
            rvExamples.adapter = adapter
            tvWordStatus.text = wordCard.status.getText(context)
            tvWordStatus.setTextColor(
                wordCard.status.getColor(context)
            )
            tvLanguageTranslation.text = wordCard.languageTranslation.name
            tvLanguageWord.text = wordCard.languageOriginal.name
        }
        currentHolder = holder
        if (position != topPosition) {
            holder.hideFrontSideContent()
        }

        holder.controller.onFlipStart = {
            //toggleCardStackSwipe?.invoke(false)
        }
        holder.controller.onFlipEnd = {
            //toggleCardStackSwipe?.invoke(true)
        }

    }

    override fun getItemCount(): Int {
        return cardList.size
    }
}