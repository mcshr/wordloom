package com.mcshr.wordloom.presentation.learningScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mcshr.wordloom.databinding.ItemCardBinding
import com.mcshr.wordloom.domain.entities.WordCard

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
        with(holder.binding) {
            tvWordText.text = wordCard.wordText
            tvWordTranslation.text = wordCard.wordTranslations.joinToString(", ") {
                it
            }

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

    fun cancelFlip() {
        currentHolder?.controller?.cancelFlip()
    }

    override fun getItemCount(): Int {
        return cardList.size
    }
}