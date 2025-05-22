package com.mcshr.wordloom.presentation.learningScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mcshr.wordloom.databinding.ItemCardBinding
import com.mcshr.wordloom.domain.entities.WordCard

class CardStackAdapter(
    private val cardList: List<WordCard>
) : RecyclerView.Adapter<CardViewHolder>() {
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
        holder.binding.tvWordText.text = wordCard.wordText
        holder.binding.tvWordTranslation.text = wordCard.wordTranslations.toString()
        val controller = CardFlipController(holder.binding)
        holder.binding.root.setOnClickListener {
            controller.flip()
        }
    }

    override fun getItemCount(): Int {
        return cardList.size
    }
}