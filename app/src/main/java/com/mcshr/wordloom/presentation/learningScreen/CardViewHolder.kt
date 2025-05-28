package com.mcshr.wordloom.presentation.learningScreen

import androidx.recyclerview.widget.RecyclerView
import com.mcshr.wordloom.databinding.ItemCardBinding

class CardViewHolder(val binding: ItemCardBinding): RecyclerView.ViewHolder(binding.root){
    init{
        val controller = CardFlipController(binding)
        binding.root.setOnClickListener {
            controller.flip()
        }
    }
}