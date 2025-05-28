package com.mcshr.wordloom.presentation.learningScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mcshr.wordloom.databinding.ItemCardBinding
import com.mcshr.wordloom.domain.entities.WordCard

class CardStackAdapter(
) : RecyclerView.Adapter<CardViewHolder>() {

    var cardList = listOf<WordCard>()
        set(value){
            val callback = CardListDiffCallback(cardList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

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
        holder.binding.frontSide.rotationY = 0f
        holder.binding.backSide.rotationY = 180f
        holder.binding.frontSide.visibility = View.VISIBLE
        holder.binding.backSide.visibility = View.INVISIBLE
        val wordCard = cardList[position]
        holder.binding.tvWordText.text = wordCard.wordText
        holder.binding.tvWordTranslation.text = wordCard.wordTranslations.joinToString(", ") {
            it
        }
    }

    override fun getItemCount(): Int {
        return cardList.size
    }
}