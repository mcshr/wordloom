package com.mcshr.wordloom.presentation.learningScreen

import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.mcshr.wordloom.databinding.ItemCardBinding

class CardViewHolder(val binding: ItemCardBinding): RecyclerView.ViewHolder(binding.root){
    val controller = CardFlipController(binding)
    init{
        binding.root.setOnClickListener {
            controller.flip()
        }
    }

    fun hideFrontSideContent(){
        binding.frontSide.children.forEach {
            it.alpha = 0f
        }
    }
    fun fadeInContent() {
        binding.frontSide.children.forEach {
            it.alpha = 0f
        }
        binding.frontSide.children.forEach {
            it.animate()
                .alpha(1f)
                .setDuration(150)
                .start()
        }
    }

}