package com.mcshr.wordloom.presentation.learningScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mcshr.wordloom.databinding.ItemCardBinding
import com.mcshr.wordloom.domain.entities.WordCard

class CardStackAdapter() : RecyclerView.Adapter<CardViewHolder>() {

    var cardList = listOf<WordCard>()
        set(value) {
            val callback = CardListDiffCallback(cardList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    private val activeHolders = mutableListOf<CardViewHolder>()
    private var currentHolder : CardViewHolder? = null

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
        with(holder.binding) {
            tvWordText.text = wordCard.wordText
            tvWordTranslation.text = wordCard.wordTranslations.joinToString(", ") {
                it
            }

        }
        if (!activeHolders.contains(holder)) {
            activeHolders.add(holder)
        }
        currentHolder = holder

        holder.controller.onFlipStart = {
            activeHolders.forEach {
                if (it != holder)
                    it.hideFrontSideContent()
            }
        }
        holder.controller.onFlipEnd = {
            activeHolders.forEach {
                it.showFrontSideContent()
            }
        }

    }

    fun cancelFlip(){
        currentHolder?.controller?.cancelFlip()
    }

    override fun getItemCount(): Int {
        return cardList.size
    }
}